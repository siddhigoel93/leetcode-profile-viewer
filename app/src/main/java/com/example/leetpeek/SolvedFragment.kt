package com.example.leetpeek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leetpeek.dataClasses.Submission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SolvedFragment : Fragment() {

    private lateinit var subItem: RecyclerView
    private lateinit var solvedAdapter: SolvedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_solved, container, false)

        subItem = view.findViewById(R.id.solved)
        subItem.layoutManager = LinearLayoutManager(requireContext())

        solvedAdapter = SolvedAdapter(emptyList())
        subItem.adapter = solvedAdapter

        val sharedPref = requireActivity().getSharedPreferences("MyPrefs", 0)
        val username = sharedPref.getString("leetcode_username", "") ?: ""

        if (username.isNotEmpty()) {
            fetchSolved(username)
        } else {
            Toast.makeText(requireContext(), "Username not found!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun fetchSolved(username: String) {
        val api = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response: List<Submission> = api.getSolved(username)

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
