package com.example.leetpeek.dataClasses

import com.google.gson.annotations.SerializedName

data class UserProfileData(
    val username: String ,
    val name: String,
    val avatar: String ,
    val ranking: Int?,
    val reputation: Int?,
    val gitHub: String?,
    val skillTags: ArrayList<String>,
    val about: String,
    @SerializedName("solvedProblem") val solved: String
)