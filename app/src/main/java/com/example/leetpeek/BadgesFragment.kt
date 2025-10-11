package com.example.leetpeek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leetpeek.dataClasses.Badge
import com.example.leetpeek.dataClasses.BadgesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope


class  BadgesFragment : Fragment() {
    lateinit var badge : RecyclerView
    lateinit var badgeAdapter: BadgeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_badges, container, false)
        badge = view.findViewById<RecyclerView>(R.id.badgeRV)
        badge.layoutManager = LinearLayoutManager(requireContext())


        val sharedPref = requireActivity().getSharedPreferences("leetpeek_prefs", 0)
        val username = sharedPref.getString("username", "") ?: ""

        if (username.isNotEmpty()) {
            fetchBadges(username)
        } else {
            Toast.makeText(requireContext(), "Username not found!", Toast.LENGTH_SHORT).show()
        }

        return view

    }
    fun fetchBadges(username: String) {
        val api = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
            try {
                val response: BadgesData = api.getBadges(username)
                val badgeList: List<Badge> = response.badges.map { it as Badge }

                withContext(Dispatchers.Main) {
                    badgeAdapter = BadgeAdapter(badgeList)
                    badge.adapter = badgeAdapter
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