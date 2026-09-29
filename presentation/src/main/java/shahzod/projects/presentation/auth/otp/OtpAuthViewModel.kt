package shahzod.projects.presentation.auth.otp

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shahzod.projects.presentation.util.navigation.AppNavigator
import shahzod.projects.core.local.LocalDataStorage
import shahzod.projects.core.request.VerifyNumberRequest
import shahzod.projects.core.request.VerifyTokenRequest
import shahzod.projects.domain.usecase.AuthUseCase
import javax.inject.Inject

@HiltViewModel
class OtpAuthViewModel @Inject constructor(
    private val verifyOtpUseCase: AuthUseCase.VerifyOtp,
    private val requestOtpUseCase: AuthUseCase.RequestOtp,
    private val localStorage: LocalDataStorage,
    private val navigator: AppNavigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(OtpAuthContract.UiState())
    val uiState: StateFlow<OtpAuthContract.UiState> = _uiState.asStateFlow()

    private val _sideEffect = Channel<OtpAuthContract.SideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private var timerJob: Job? = null

    fun setPhone(phone: String) {
        _uiState.update { it.copy(phone = phone) }
        startTimer()
    }

    fun onIntent(intent: OtpAuthContract.Intent) {
        when (intent) {
            is OtpAuthContract.Intent.OnKeyClick -> handleKeyClick(intent.digit)
            OtpAuthContract.Intent.OnBackspaceClick -> handleBackspace()
            OtpAuthContract.Intent.OnVerifyClick -> handleVerify()
            OtpAuthContract.Intent.OnResendClick -> handleResend()
            OtpAuthContract.Intent.OnBackClick -> handleBack()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(timerSeconds = 300, isResendEnabled = false) }
        timerJob = viewModelScope.launch {
            while (_uiState.value.timerSeconds > 0) {
                delay(1000)
                _uiState.update { it.copy(timerSeconds = it.timerSeconds - 1) }
            }
            _uiState.update { it.copy(isResendEnabled = true) }
        }
    }

    private fun handleKeyClick(digit: String) {
        val state = _uiState.value
        if (state.code.length < state.codeLength) {
            _uiState.update { it.copy(code = it.code + digit, errorMessage = null) }
        }
    }

    private fun handleBackspace() {
        if (_uiState.value.code.isNotEmpty()) {
            _uiState.update { it.copy(code = it.code.dropLast(1), errorMessage = null) }
        }
    }

    private fun handleVerify() {
        val state = _uiState.value
        if (!state.isCodeFilled) return

        val request = VerifyTokenRequest(
            phone = state.phone,
            code = state.code,
            deviceName = Build.MODEL ?: "Android Device"
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            verifyOtpUseCase(request).collect { result ->
                _uiState.update { it.copy(isLoading = false) }
                result.onSuccess { response ->
                    localStorage.accessToken = response.accessToken
                    localStorage.refreshToken = response.refreshToken
                    localStorage.isSigned = true
                    _sideEffect.send(OtpAuthContract.SideEffect.NavigateToHome)
                }.onFailure { error ->
                    val message = error.message ?: "Kod xato kiritildi"
                    _uiState.update { it.copy(errorMessage = message) }
                    _sideEffect.send(OtpAuthContract.SideEffect.ShowToast(message))
                }
            }
        }
    }

    private fun handleResend() {
        val phone = _uiState.value.phone
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            requestOtpUseCase(VerifyNumberRequest(phone)).collect { result ->
                _uiState.update { it.copy(isLoading = false) }
                result.onSuccess {
                    _sideEffect.send(OtpAuthContract.SideEffect.ShowToast("Kod qayta yuborildi"))
                    startTimer()
                }.onFailure { error ->
                    val message = error.message ?: "Kodni qayta yuborishda xatolik"
                    _sideEffect.send(OtpAuthContract.SideEffect.ShowToast(message))
                }
            }
        }
    }

    private fun handleBack() {
        viewModelScope.launch {
            _sideEffect.send(OtpAuthContract.SideEffect.NavigateBack)
        }
        navigator.back()
    }
}
