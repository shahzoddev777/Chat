package shahzod.projects.domain.usecase

import jakarta.inject.Inject
import shahzod.projects.core.request.RefreshTokenRequest
import shahzod.projects.core.request.VerifyNumberRequest
import shahzod.projects.core.request.VerifyTokenRequest
import shahzod.projects.domain.repository.AuthRepository

interface AuthUseCase {
    class RequestOtp @Inject constructor(
        private val repository: AuthRepository
    ) {
        operator fun invoke(phone: VerifyNumberRequest) = repository.requestOtp(phone)
    }

    class VerifyOtp @Inject constructor(
        private val repository: AuthRepository
    ) {
        operator fun invoke(request: VerifyTokenRequest) = repository.verifyOtp(request)
    }

    class RefreshToken @Inject constructor(
        private val repository: AuthRepository
    ) {
        operator fun invoke(request: RefreshTokenRequest) = repository.refreshToken(request)
    }
}