package shahzod.projects.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import shahzod.projects.core.network.api.UsersApi
import shahzod.projects.core.request.UserIdRequest
import shahzod.projects.core.request.UsersUpdateBody
import shahzod.projects.core.response.SearchUserResponse
import shahzod.projects.core.response.UserIdResponse
import shahzod.projects.core.response.UserUpdateResponse
import shahzod.projects.core.response.UsersResponse
import shahzod.projects.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : UserRepository {
    override fun getUsers(): Flow<Result<UsersResponse>> = flow {
        emit(
            runCatching {
                usersApi.getUsers()
            }
        )
    }

    override fun updateUsers(request: UsersUpdateBody): Flow<Result<UserUpdateResponse>> = flow {
        emit(
            runCatching {
                val response = usersApi.updateUsers(request)
                if (response.isSuccessful) {
                    response.body() ?: throw Exception("Server bo'sh javob qaytardi")
                } else {
                    val errorText = response.errorBody()?.string()
                    throw Exception("Xatolik ${response.code()}: $errorText")
                }
            }
        )
    }

    override fun getUserById(id: UserIdRequest): Flow<Result<UserIdResponse>> = flow {
        emit(
            runCatching {
                val response = usersApi.getUserById(id)
                if (response.isSuccessful){
                    response.body() ?: throw Exception("Server bo'sh javob qaytardi")
                } else {
                    val errorText = response.errorBody()?.string()
                    throw Exception("Xatolik ${response.code()}: $errorText")
                }
            }
        )
    }

    override fun getSearchUsers(
        query: String,
        limit: Int
    ): Flow<Result<SearchUserResponse>> = flow {
        emit(
            runCatching {
                usersApi.getSearchUsers(query, limit).getOrThrow()
            }
        )
    }
}