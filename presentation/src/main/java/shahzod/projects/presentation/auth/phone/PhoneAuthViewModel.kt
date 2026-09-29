package shahzod.projects.presentation.auth.phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shahzod.projects.presentation.util.navigation.AppNavigator
import shahzod.projects.core.request.VerifyNumberRequest
import shahzod.projects.domain.usecase.AuthUseCase
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val requestOtpUseCase: AuthUseCase.RequestOtp,
    private val navigator: AppNavigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhoneAuthContract.UiState())
    val uiState: StateFlow<PhoneAuthContract.UiState> = _uiState.asStateFlow()

    private val _sideEffect = Channel<PhoneAuthContract.SideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onIntent(intent: PhoneAuthContract.Intent) {
        when (intent) {
            is PhoneAuthContract.Intent.OnKeyClick -> handleKeyClick(intent.digit)
            PhoneAuthContract.Intent.OnBackspaceClick -> handleBackspace()
            PhoneAuthContract.Intent.OnSendCodeClick -> handleSendCode()
            PhoneAuthContract.Intent.OnBackClick -> handleBack()
        }
    }

    private fun handleKeyClick(digit: String) {
        if (_uiState.value.phoneNumber.length < 9) {
            _uiState.update { it.copy(phoneNumber = it.phoneNumber + digit, errorMessage = null) }
        }
    }

    private fun handleBackspace() {
        if (_uiState.value.phoneNumber.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    phoneNumber = it.phoneNumber.dropLast(1),
                    errorMessage = null
                )
            }
        }
    }

    private fun handleSendCode() {
        val phone = _uiState.value.phoneNumber
        if (phone.length != 9) return

        val fullPhoneNumber = "+998$phone"

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            requestOtpUseCase(VerifyNumberRequest(fullPhoneNumber)).collect { result ->
                _uiState.update { it.copy(isLoading = false) }
                result.onSuccess {
                    _sideEffect.send(PhoneAuthContract.SideEffect.NavigateToOtp(fullPhoneNumber))
                }.onFailure { error ->
                    val message = error.message ?: "Kodni yuborishda xatolik yuz berdi"
                    _uiState.update { it.copy(errorMessage = message) }
                    _sideEffect.send(PhoneAuthContract.SideEffect.ShowToast(message))
                }
            }
        }
    }

    private fun handleBack() {
        viewModelScope.launch {
            _sideEffect.send(PhoneAuthContract.SideEffect.NavigateBack)
        }
        navigator.back()
    }
}
