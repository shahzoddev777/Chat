package shahzod.projects.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import shahzod.projects.core.network.api.AuthApi
import shahzod.projects.core.request.RefreshTokenRequest
import shahzod.projects.core.request.VerifyNumberRequest
import shahzod.projects.core.request.VerifyTokenRequest
import shahzod.projects.core.response.RefreshTokenResponse
import shahzod.projects.core.response.VerifyTokenResponse
import shahzod.projects.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override fun requestOtp(
        request: VerifyNumberRequest
    ): Flow<Result<Unit>> = flow {
        emit(
            runCatching {
                authApi.getAuthVerify(request)
            }
        )
    }

    override fun verifyOtp(
        request: VerifyTokenRequest
    ): Flow<Result<VerifyTokenResponse>> = flow {
        emit(
            runCatching {
                authApi.getVerifyToken(request).body()
                    ?: throw Exception("Response body is null")
            }
        )
    }

    override fun refreshToken(
        request: RefreshTokenRequest
    ): Flow<Result<RefreshTokenResponse>> = flow {
        emit(
            runCatching {
                val response = authApi.getRefreshToken(request)

                response.body()
                    ?: throw Exception("Response body is null")
            }
        )
    }
}