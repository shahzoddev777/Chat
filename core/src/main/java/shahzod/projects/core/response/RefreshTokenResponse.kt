package shahzod.projects.core.response

data class RefreshTokenResponse(
    val accessToken: String,
    val deviceId: String,
    val isNewUser: Boolean,
    val refreshToken: String,
    val userId: String
)