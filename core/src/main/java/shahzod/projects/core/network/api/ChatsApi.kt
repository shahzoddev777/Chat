package shahzod.projects.core.network.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import shahzod.projects.core.response.ChatsPageResponse

interface ChatsApi {

    @GET("v1/chats")
    fun getUserChats(
        @Query("limit") limit: Int,
        @Query("cursor") cursor: String
    ): ChatsPageResponse

//    @POST("v1/chats/direct")
//    fun
}