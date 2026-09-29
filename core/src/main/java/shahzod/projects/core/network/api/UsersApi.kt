package shahzod.projects.core.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import shahzod.projects.core.request.UserIdRequest
import shahzod.projects.core.request.UsersUpdateBody
import shahzod.projects.core.response.SearchUserResponse
import shahzod.projects.core.response.UserIdResponse
import shahzod.projects.core.response.UserUpdateResponse
import shahzod.projects.core.response.UsersResponse

interface UsersApi {
    @GET("v1/users/me")
    suspend fun getUsers(): UsersResponse

    @PATCH("v1/users/me")
    suspend fun updateUsers(
        @Body request: UsersUpdateBody
    ): Response<UserUpdateResponse>

    @GET("v1/users/{id}")
    suspend fun getUserById(
        @Path("id") request: UserIdRequest
    ): Response<UserIdResponse>

    @GET("v1/users/search")
    suspend fun getSearchUsers(
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): Result<SearchUserResponse>

    //FCM TOKEN YOZISH KERAK

}