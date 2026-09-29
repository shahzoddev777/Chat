package shahzod.projects.core.response

data class VerifyTokenResponse(
    val accessToken: String,
    val deviceId: String,
    val isNewUser: Boolean,
    val refreshToken: String,
    val userId: String
)