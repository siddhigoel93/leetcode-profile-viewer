package com.example.leetpeek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import android.widget.LinearLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    lateinit var profileName: TextView
    lateinit var profileUsername: TextView
    lateinit var rankCount: TextView
    lateinit var about: TextView
    lateinit var solvedCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileName = view.findViewById(R.id.profileName)
        profileUsername = view.findViewById(R.id.profileUsername)
        rankCount = view.findViewById(R.id.rankCount)
        about = view.findViewById(R.id.about)
        solvedCount = view.findViewById(R.id.solvedCount)

        val submSection = view.findViewById<LinearLayout>(R.id.sectionSubmissions)
        val badgeSection = view.findViewById<LinearLayout>(R.id.sectionBadges)
        val solvedSection = view.findViewById<LinearLayout>(R.id.sectionSolved)


        submSection.setOnClickListener {
            findNavController().navigate(R.id.action_profilePage_to_SubmissionPage)
        }
        badgeSection.setOnClickListener {
            findNavController().navigate(R.id.action_profilePage_to_BadgesPage)
        }
        solvedSection.setOnClickListener {
            findNavController().navigate(R.id.action_profilePage_to_SolvedPage)
        }
        val sharedPref = requireActivity().getSharedPreferences("MyPrefs", 0)
        val username = sharedPref.getString("leetcode_username", "") ?: ""

        if (username.isNotEmpty()) {
            fetchProfileData(username)
        } else {
            Toast.makeText(requireContext(), "Username not found!", Toast.LENGTH_SHORT).show()
            view.post {
                findNavController().navigate(R.id.landingPage)
            }
            return view
        }

        return view

    }
    fun fetchProfileData(username: String) {
        val api = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val profile = api.getUserProfile(username)
                val subData = api.getSubmData(username)

                profileName.text = profile.name
                profileUsername.text = profile.username
                rankCount.text = profile.ranking.toString()
                about.text = profile.about
                solvedCount.text = subData.solvedProblem.toString()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Failed to load profile: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


