package shahzod.projects.presentation.auth.phone

sealed interface PhoneAuthContract {
    data class UiState(
        val phoneNumber: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    ) : PhoneAuthContract {
        val isValid: Boolean get() = phoneNumber.length == 9
        val formattedPhone: String
            get() {
                return buildString {
                    for (i in phoneNumber.indices) {
                        if (i == 2 || i == 5 || i == 7) append(' ')
                        append(phoneNumber[i])
                    }
                }
            }
    }

    sealed interface Intent : PhoneAuthContract {
        data class OnKeyClick(val digit: String) : Intent
        data object OnBackspaceClick : Intent
        data object OnSendCodeClick : Intent
        data object OnBackClick : Intent
    }

    sealed interface SideEffect : PhoneAuthContract {
        data class NavigateToOtp(val phone: String) : SideEffect
        data class ShowToast(val message: String) : SideEffect
        data object NavigateBack : SideEffect
    }
}
