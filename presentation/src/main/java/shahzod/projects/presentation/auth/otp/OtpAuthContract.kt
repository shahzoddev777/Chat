package shahzod.projects.presentation.auth.otp

sealed interface OtpAuthContract {
    data class UiState(
        val phone: String = "",
        val code: String = "",
        val timerSeconds: Int = 45,
        val isResendEnabled: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    ) : OtpAuthContract {
        val isCodeFilled: Boolean get() = code.length == 6
        val formattedTimer: String
            get() {
                val minutes = timerSeconds / 60
                val seconds = timerSeconds % 60
                return String.format("%02d:%02d", minutes, seconds)
            }
    }

    sealed interface Intent : OtpAuthContract {
        data class OnKeyClick(val digit: String) : Intent
        data object OnBackspaceClick : Intent
        data object OnVerifyClick : Intent
        data object OnResendClick : Intent
        data object OnBackClick : Intent
    }

    sealed interface SideEffect : OtpAuthContract {
        data object NavigateToHome : SideEffect
        data class ShowToast(val message: String) : SideEffect
        data object NavigateBack : SideEffect
    }
}
