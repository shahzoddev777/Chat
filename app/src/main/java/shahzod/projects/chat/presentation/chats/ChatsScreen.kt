package shahzod.projects.chat.presentation.chats

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class ChatsScreen : Screen {
    @Composable
    override fun Content() {
        Text(text = "chatscreen")
    }
}