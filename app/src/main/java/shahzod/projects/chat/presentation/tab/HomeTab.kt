package shahzod.projects.chat.presentation.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import shahzod.projects.chat.presentation.chats.ChatsScreen
import shahzod.projects.chat.presentation.group.GroupScreen
import shahzod.projects.chat.presentation.profile.ProfileScreen
import shahzod.projects.chat.presentation.settings.SettingsScreen

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Asosiy sahifa"
            val icon = rememberVectorPainter(Icons.Default.Home)
            return remember { TabOptions(index = 0u, title, icon) }
        }

    @Composable
    override fun Content() {
        ChatsScreen().Content()
    }
}

object GroupsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Guruhlar"
            val icon = rememberVectorPainter(Icons.Default.Group)
            return remember { TabOptions(title = title, icon = icon, index = 1u) }
        }

    @Composable
    override fun Content() {
        GroupScreen().Content()
    }
}

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Sozlamalar"
            val icon = rememberVectorPainter(Icons.Default.Settings)
            return remember { TabOptions(title = title, icon = icon, index = 2u) }
        }

    @Composable
    override fun Content() {
        SettingsScreen().Content()
    }
}

object ProfileScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profil"
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember { TabOptions(title = title, icon = icon, index = 3u) }
        }

    @Composable
    override fun Content() {
        ProfileScreen().Content()
    }
}

