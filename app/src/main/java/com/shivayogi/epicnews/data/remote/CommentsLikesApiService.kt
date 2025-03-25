package com.shivayogi.epicnews.data.remote

import com.shivayogi.epicnews.data.models.LikesResponse
import com.shivayogi.epicnews.data.models.CommentsResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface CommentsLikesApiService {

    @GET("likes/{articleId}")
    suspend fun getLikes(@Path("articleId") articleId: String): LikesResponse

    @GET("comments/{articleId}")
    suspend fun getComments(@Path("articleId") articleId: String): CommentsResponse
}