package shahzod.projects.core.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

fun Interceptor.Chain.update(block: (Request.Builder) -> Unit): Response {
    val request = this.request()
    val newRequestBuilder = request.newBuilder()

    block(newRequestBuilder)

    val newRequest = newRequestBuilder.build()
    val response = this.proceed(newRequest)
    return response
}