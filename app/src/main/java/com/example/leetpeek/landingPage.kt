package com.example.leetpeek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch



class LandingPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landing_page, container, false)

        val usernameInput = view.findViewById<EditText>(R.id.username)
        val button = view.findViewById<Button>(R.id.signup)

        button.setOnClickListener {
            val username = usernameInput.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter the username", Toast.LENGTH_SHORT).show()
            }
           else
                fetchUserProfile(username)
        }

        return view
    }

    private fun fetchUserProfile(username: String) {
        val leetcodeApi = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)



        lifecycleScope.launch {
            val result = leetcodeApi.getUserProfile(username)

            val bundle = Bundle().apply {
                putString("name", result.name)
                putString("username" ,result.username)
                putString("ranking", result.ranking.toString())
                putString("about" , result.about)
            }

            findNavController().navigate(R.id.profilePage, bundle)


        }
    }
}
