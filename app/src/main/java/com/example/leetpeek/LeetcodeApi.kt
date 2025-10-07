package com.example.leetpeek

import retrofit2.http.GET
import retrofit2.http.Path

interface LeetCodeApi {

    @GET("/{username}")
    suspend fun getUserProfile(
        @Path("username") username: String
    ): UserProfileData

    @GET("/{username}/solved")
    suspend fun getSubmData(
        @Path("username") username: String
    ) : SubmissionsData
}
