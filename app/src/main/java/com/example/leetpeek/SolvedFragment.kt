package com.example.leetpeek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leetpeek.dataClasses.Submission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.material.chip.ChipGroup

class SolvedFragment : Fragment() {

    private lateinit var subItem: RecyclerView
    private lateinit var solvedAdapter: SolvedAdapter
    lateinit var filters: ChipGroup
    var allSubmissions: List<Submission> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_solved, container, false)

        subItem = view.findViewById(R.id.solved)
        subItem.layoutManager = LinearLayoutManager(requireContext())

        solvedAdapter = SolvedAdapter(emptyList())
        subItem.adapter = solvedAdapter


        filters = view.findViewById<ChipGroup>(R.id.filters)

        val sharedPref = requireActivity().getSharedPreferences("leetpeek_prefs", 0)
        val username = sharedPref.getString("username", "") ?: ""

        if (username.isNotEmpty()) {
            fetchSolved(username)
        } else {
            Toast.makeText(requireContext(), "Username not found!", Toast.LENGTH_SHORT).show()
        }
        filterListener()
        return view
    }

    private fun fetchSolved(username: String) {
        val api = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getSolved(username)

                withContext(Dispatchers.Main) {
                    allSubmissions = response.submission
                    solvedAdapter.solvedList = allSubmissions
                    solvedAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "error : ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    fun filterListener(){
        filters.setOnCheckedChangeListener { _, checkedId ->
            val filteredList = when (checkedId) {
                R.id.accepted -> allSubmissions.filter { it.statusDisplay.equals("Accepted", true) }
                R.id.time -> allSubmissions.filter { it.statusDisplay.contains("Time Limit", true) }
                R.id.runtime -> allSubmissions.filter { it.statusDisplay.contains("Runtime", true) }
                R.id.compile -> allSubmissions.filter { it.statusDisplay.contains("Compile", true) }
                else -> allSubmissions
            }

            solvedAdapter.solvedList = filteredList
            solvedAdapter.notifyDataSetChanged()
        }
    }
}