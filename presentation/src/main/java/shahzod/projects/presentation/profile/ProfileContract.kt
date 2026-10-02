package shahzod.projects.presentation.profile

import org.orbitmvi.orbit.ContainerHost


interface ProfileContract {

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: ProfileIntent)
    }

    data class UiState(
        val id: String = "",
        val displayName: String = "",
        val username: String = "",
        val phone: String = "",
        val online: Boolean = false,
        val avatarMediaId: String? = null,
        val avatarVersion: Int = 0,
        val lastSeenAt: String? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    ) {
        val formattedPhone: String
            get() = when {
                phone.isBlank() -> "Raqam yo‘q"
                phone.startsWith("+998") && phone.length > 4 -> "+998 ${phone.drop(4)}"
                else -> phone
            }

        val formattedUsername: String
            get() = if (username.isBlank()) "Qo‘shish" else "@$username"
    }

    sealed interface ProfileIntent {
        data object OnBackClicked : ProfileIntent
        data object LoadData: ProfileIntent
        data class OnDisplayNameChanged(val value: String) : ProfileIntent
        data class OnUsernameChanged(val value: String) : ProfileIntent
        data object OnSaveClicked : ProfileIntent
    }

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
        data object NavigateBack : SideEffect
        data object NavigateToLogin : SideEffect
    }
}