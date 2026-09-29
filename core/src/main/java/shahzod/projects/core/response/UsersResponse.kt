package shahzod.projects.core.response

data class UsersResponse(
    val avatarMediaId: Any,
    val avatarVersion: Int,
    val displayName: String,
    val id: String,
    val lastSeenAt: Any,
    val online: Boolean,
    val phone: String,
    val username: String
)