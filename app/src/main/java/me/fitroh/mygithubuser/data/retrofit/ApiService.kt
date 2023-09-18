package me.fitroh.mygithubuser.data.retrofit

import me.fitroh.mygithubuser.data.response.DetailUserResponse
import me.fitroh.mygithubuser.data.response.GithubResponse
import me.fitroh.mygithubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String?
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")
        username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>
}