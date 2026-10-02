package shahzod.projects.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import shahzod.projects.domain.usecase.UserUseCase
import javax.inject.Inject
import org.orbitmvi.orbit.viewmodel.container
import shahzod.projects.core.request.UsersUpdateBody

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUsersUseCase: UserUseCase.GetUsers,
    private val updateUsersUseCase: UserUseCase.UpdateUsers
) : ViewModel(), ProfileContract.ViewModel {
    override val container = container<ProfileContract.UiState, ProfileContract.SideEffect>(
        ProfileContract.UiState()
    )

    override fun onEventDispatcher(intent: ProfileContract.ProfileIntent) {
        intent {
            when (intent) {
                is ProfileContract.ProfileIntent.OnBackClicked -> postSideEffect(ProfileContract.SideEffect.NavigateBack)
                is ProfileContract.ProfileIntent.LoadData -> {
                    getUsersUseCase().onEach {
                        it.onSuccess {
                            reduce {
                                state.copy(
                                    id = it.id,
                                    username = it.username,
                                    avatarMediaId = it.avatarMediaId?.toString(),
                                    phone = it.phone,
                                    lastSeenAt = it.lastSeenAt?.toString()
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
                }

                is ProfileContract.ProfileIntent.OnDisplayNameChanged -> intent {
                    reduce { state.copy(displayName = intent.value) }
                }

                is ProfileContract.ProfileIntent.OnUsernameChanged -> intent {
                    reduce { state.copy(username = intent.value) }
                }

                is ProfileContract.ProfileIntent.OnSaveClicked -> intent {
                    updateUserData()
                }
            }
        }
    }

    private fun updateUserData() = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }

        val requestBody = UsersUpdateBody(
            displayName = state.displayName,
            username = state.username
        )

        updateUsersUseCase(requestBody).onEach { result ->
            result.onSuccess { updatedResponse ->
                reduce {
                    state.copy(
                        isLoading = false,
                        displayName = updatedResponse.displayName,
                        username = updatedResponse.username,
                    )
                }
                postSideEffect(ProfileContract.SideEffect.ShowToast("Muvaffaqiyatli saqlandi!"))
            }.onFailure { error ->
                reduce {
                    state.copy(
                        isLoading = false,
                        errorMessage = error.localizedMessage
                    )
                }
                postSideEffect(
                    ProfileContract.SideEffect.ShowToast(
                        error.localizedMessage ?: "Saqlashda xatolik yuz berdi"
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

}