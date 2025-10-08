package com.example.leetpeek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leetpeek.com.example.leetpeek.SubmissionAdapter
import com.example.leetpeek.dataClasses.Submission
import com.example.leetpeek.dataClasses.SubmissionsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SubmissionFragment : Fragment() {
    lateinit var ques : RecyclerView
    lateinit var subItem : RecyclerView
    lateinit var submissionAdapter: SubmissionAdapter
    lateinit var solvedAdapter: SolvedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_submission, container, false)

        subItem = view.findViewById<RecyclerView>(R.id.filters)
        subItem.layoutManager = LinearLayoutManager(requireContext())


        ques = view.findViewById<RecyclerView>(R.id.submissions)
        ques.layoutManager = LinearLayoutManager(requireContext())

        submissionAdapter = SubmissionAdapter(emptyList())
        ques.adapter = submissionAdapter

        solvedAdapter = SolvedAdapter(emptyList())
        subItem.adapter = solvedAdapter


        val sharedPref = requireActivity().getSharedPreferences("MyPrefs", 0)
        val username = sharedPref.getString("leetcode_username", "") ?: ""

        if (username.isNotEmpty()) {
            fetchSubmissions(username)
            fetchSolved(username)
        } else {
            Toast.makeText(requireContext(), "Username not found!", Toast.LENGTH_SHORT).show()
        }

        return view

    }
    fun fetchSubmissions(username: String) {
        val api = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: SubmissionsData = api.getSubmData(username)

                withContext(Dispatchers.Main) {
                    submissionAdapter.submissionList = response.totalSubmissionNum
                    submissionAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
    fun fetchSolved(username: String) {
        val api = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: List<Submission> = api.getSolved(username , 20)


                withContext(Dispatchers.Main) {
                    solvedAdapter.solvedList = response
                    solvedAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}


