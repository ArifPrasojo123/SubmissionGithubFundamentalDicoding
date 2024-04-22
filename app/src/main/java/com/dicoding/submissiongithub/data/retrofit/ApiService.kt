package com.dicoding.submissiongithub.data.retrofit

import com.dicoding.submissiongithub.data.response.DetailUserResponse
import com.dicoding.submissiongithub.data.response.GithubResponse
import com.dicoding.submissiongithub.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET ("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String
    ): Call<List<ItemsItem>>
}