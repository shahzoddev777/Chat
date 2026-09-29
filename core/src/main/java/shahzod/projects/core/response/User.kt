package shahzod.projects.core.response

data class User(
    val avatarMediaId: Any,
    val avatarVersion: Int,
    val displayName: String,
    val id: String,
    val lastSeenAt: Any,
    val online: Boolean,
    val username: String
)