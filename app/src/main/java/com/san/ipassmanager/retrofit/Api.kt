package com.san.ipassmanager.retrofit

import com.san.ipassmanager.model.TestResponse
import retrofit2.http.GET
import retrofit2.http.Url


interface Api {

    @GET("todos/1")
    suspend fun testCall(): TestResponse


  /*  @GET()
    suspend fun testCall2(@Url url: String): TestResponse*/

    @GET()
    suspend fun testCall2(@Url url :String): List<String>
}