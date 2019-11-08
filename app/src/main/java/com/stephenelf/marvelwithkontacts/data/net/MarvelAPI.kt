package com.stephenelf.marvelwithkontacts.data.net

import com.stephenelf.marvelwithkontacts.data.net.response.CharacterResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
interface MarvelAPI {

    companion object{
        val LIMIT = 50
    }


    @GET("characters")
    abstract fun getCharacters(
        @Query("apikey") apiKey: String, @Query("ts") timestamp: Long, @Query("hash") hash: String,
        @Query("limit") limit: Int, @Query("offset") offset: Int
    ): Single<CharacterResponse>
}