package shahzod.projects.core.request

data class VerifyTokenRequest(
    val phone: String,
    val code: String,
    val deviceName: String
)
