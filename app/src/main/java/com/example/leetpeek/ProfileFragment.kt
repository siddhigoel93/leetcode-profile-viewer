package com.example.leetpeek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


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
        val rank = arguments?.getString("ranking") ?: "N/A"
        val about = arguments?.getString("about" )?: "No description"
        val avatar = arguments?.getString("avatar")

        // Find TextViews and set data
        view.findViewById<TextView>(R.id.profileName).text = name
        view.findViewById<TextView>(R.id.profileUsername)?.text = username
        view.findViewById<TextView>(R.id.rankCount)?.text = rank
        view.findViewById<TextView>(R.id.about).text = about



        return view
    }


}