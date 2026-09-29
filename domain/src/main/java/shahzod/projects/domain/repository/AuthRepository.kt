package shahzod.projects.domain.repository

import kotlinx.coroutines.flow.Flow
import shahzod.projects.core.request.RefreshTokenRequest
import shahzod.projects.core.request.VerifyNumberRequest
import shahzod.projects.core.request.VerifyTokenRequest
import shahzod.projects.core.response.RefreshTokenResponse
import shahzod.projects.core.response.VerifyTokenResponse

interface AuthRepository {

    fun requestOtp(
        request: VerifyNumberRequest
    ): Flow<Result<Unit>>

    fun verifyOtp(
        request: VerifyTokenRequest
    ): Flow<Result<VerifyTokenResponse>>

    fun refreshToken(
        request: RefreshTokenRequest
    ): Flow<Result<RefreshTokenResponse>>
}