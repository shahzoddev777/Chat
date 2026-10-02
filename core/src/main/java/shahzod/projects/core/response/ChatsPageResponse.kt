package shahzod.projects.core.response

data class ChatsPageResponse(
    val chats: List<Chat>,
    val nextCursor: Any
)