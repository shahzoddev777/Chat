package shahzod.projects.chat.presentation.util.navigation

interface AppNavigator {
    fun navigateTo(screen: Screen)
    fun back()
    fun replace(screen: Screen)
    fun replaceAll(screen: Screen)
}
