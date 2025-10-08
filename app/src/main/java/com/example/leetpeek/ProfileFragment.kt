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



class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val name = arguments?.getString("name") ?: "Unknown"
        val username = arguments?.getString("username") ?: "-"
        val rank = arguments?.getString("ranking") ?: "-"
        val about = arguments?.getString("about" )?: "No description"
        val solved = arguments?.getString("solvedProblem")?: "0"

        view.findViewById<TextView>(R.id.profileName).text = name
        view.findViewById<TextView>(R.id.profileUsername)?.text = username
        view.findViewById<TextView>(R.id.rankCount)?.text = rank
        view.findViewById<TextView>(R.id.about).text = about
        view.findViewById<TextView>(R.id.solvedCount).text = solved

        val submSection = view.findViewById<LinearLayout>(R.id.sectionSubmissions)
        submSection.setOnClickListener {
            findNavController().navigate(R.id.action_profilePage_to_SubmissionPage)
        }
        val badgeSection = view.findViewById<LinearLayout>(R.id.sectionBadges)
        badgeSection.setOnClickListener {
            findNavController().navigate(R.id.action_profilePage_to_BadgesPage)
        }
        val solvedSection = view.findViewById<LinearLayout>(R.id.sectionSolved)
        solvedSection.setOnClickListener {
            findNavController().navigate(R.id.action_profilePage_to_SolvedPage)
        }

        return view
    }


}