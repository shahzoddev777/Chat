package shahzod.projects.core.response

data class Chat(
    val avatarMediaId: Any,
    val id: String,
    val lastActivityAt: Long,
    val lastMessage: Any,
    val muted: Boolean,
    val mutedUntil: Any,
    val peerUserId: String,
    val readUpToSeq: Int,
    val title: Any,
    val topSeq: Int,
    val type: String,
    val unreadCount: Int
)