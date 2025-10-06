package com.example.leetpeek.data

data class UserProfile(
    val username: String ,
    val name: String,
    val ranking: Int?,
    val reputation: Int?,
    val gitHub: String?,
    val skillTags: ArrayList<String>,
    val about: String
)
