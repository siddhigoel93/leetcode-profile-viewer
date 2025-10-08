package com.example.leetpeek

import com.example.leetpeek.dataClasses.BadgesData
import com.example.leetpeek.dataClasses.Submission
import com.example.leetpeek.dataClasses.SubmissionsData
import com.example.leetpeek.dataClasses.UserProfileData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeetCodeApi {

    @GET("/{username}")
    suspend fun getUserProfile(
        @Path("username") username: String
    ): UserProfileData

    @GET("/{username}/solved")
    suspend fun getSubmData(
        @Path("username") username: String
    ) : SubmissionsData

    @GET("/{username}/badges")
    suspend fun getBadges(
        @Path("username") username: String
    ) : BadgesData

    @GET("{username}/submission?limit=20")
    suspend fun getSolved(
        @Path("username") username: String,
    ) : List<Submission>
}

