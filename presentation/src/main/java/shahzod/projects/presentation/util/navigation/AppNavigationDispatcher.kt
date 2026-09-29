package shahzod.projects.presentation.util.navigation

import androidx.lifecycle.MutableLiveData

object AppNavigationDispatcher : AppNavigator, AppNavigationHandler {

    override val backStack = MutableLiveData<AppNavigationParam>()

    private fun navigat(param: AppNavigationParam) {
        backStack.postValue(param)
    }

    override fun navigateTo(screen: Screen) = navigat {
        push(screen)
    }

    override fun back() = navigat {
        pop()
    }

    override fun replace(screen: Screen) = navigat {
        replace(screen)
    }

    override fun replaceAll(screen: Screen) = navigat {
        replaceAll(screen)
    }
}
