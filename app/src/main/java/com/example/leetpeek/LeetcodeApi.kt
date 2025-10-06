package com.example.leetpeek

import com.example.leetpeek.data.UserProfile
import retrofit2.http.GET
import retrofit2.http.Path

interface LeetCodeApi {

    @GET("/{username}")
    suspend fun getUserProfile(
        @Path("username") username: String
    ): UserProfile
}
