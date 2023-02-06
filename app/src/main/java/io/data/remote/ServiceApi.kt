package io.data.remote

import io.data.model.character.CharacteModelResponse
import io.data.model.comic.ComicModelResponse
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun list(@Query("nameStartsWith") nameStartWith: String? = null): Response<CharacteModelResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId: Int
    ): Response<ComicModelResponse>
}