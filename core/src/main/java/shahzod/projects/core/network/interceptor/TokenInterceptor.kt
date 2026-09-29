package shahzod.projects.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import shahzod.projects.core.local.LocalDataStorage
import shahzod.projects.core.util.update
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val localStorage: LocalDataStorage
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.update { builder ->
            if (localStorage.isSigned) {
                builder.addHeader(KEY_AUTHORIZATION_HEADER, "Bearer ${localStorage.accessToken}")
            }
        }
    }

    companion object {
        const val KEY_AUTHORIZATION_HEADER = "Authorization"
    }
}