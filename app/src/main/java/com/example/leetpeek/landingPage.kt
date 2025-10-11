package com.example.leetpeek

import android.content.Context
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
                return@setOnClickListener
            }
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        val api = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)
                        val profile = api.getUserProfile(username)

                        if (profile == null || profile.username.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), "Invalid username. Please try again.", Toast.LENGTH_LONG).show()
                            return@launch
                        }
                        val prefs = requireActivity().getSharedPreferences("leetpeek_prefs", Context.MODE_PRIVATE)
                        prefs.edit().putString("username", username).apply()

                        fetchUserProfile(username)

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Invalid username, please check and try again.", Toast.LENGTH_LONG).show()

                    }
                }
            }


        return view
    }

    private fun fetchUserProfile(username: String) {
        val leetcodeApi = RetrofitHelper.getInstance().create(LeetCodeApi::class.java)



        lifecycleScope.launch {
            try {
                val result = leetcodeApi.getUserProfile(username)
                val result2 = leetcodeApi.getSubmData(username)

                val bundle = Bundle().apply {
                    putString("name", result.name)
                    putString("username", result.username)
                    putString("ranking", result.ranking.toString())
                    putString("about", result.about)
                    putString("solvedProblem" , result2.solvedProblem.toString())
                }

                findNavController().navigate(R.id.profilePage, bundle)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "error : ${e.message}" , Toast.LENGTH_LONG ).show()
            }

        }
    }
}
