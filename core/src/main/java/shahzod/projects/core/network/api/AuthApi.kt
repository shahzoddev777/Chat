package shahzod.projects.core.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import shahzod.projects.core.request.RefreshTokenRequest
import shahzod.projects.core.request.VerifyNumberRequest
import shahzod.projects.core.request.VerifyTokenRequest
import shahzod.projects.core.response.RefreshTokenResponse
import shahzod.projects.core.response.VerifyTokenResponse

interface AuthApi {

    @POST("v1/auth/otp/request")
    suspend fun getAuthVerify(
        @Body request: VerifyNumberRequest
    )

    @POST("v1/auth/otp/verify")
    suspend fun getVerifyToken(
        @Body tokenRequest: VerifyTokenRequest
    ): Response<VerifyTokenResponse>

    @POST("v1/auth/refresh")
    fun getRefreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Response<RefreshTokenResponse>
}