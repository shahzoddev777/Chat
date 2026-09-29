package shahzod.projects.domain.repository

import kotlinx.coroutines.flow.Flow
import shahzod.projects.core.request.UserIdRequest
import shahzod.projects.core.request.UsersUpdateBody
import shahzod.projects.core.response.SearchUserResponse
import shahzod.projects.core.response.UserIdResponse
import shahzod.projects.core.response.UserUpdateResponse
import shahzod.projects.core.response.UsersResponse

interface UserRepository {
    fun getUsers(): Flow<Result<UsersResponse>>
    fun updateUsers(request: UsersUpdateBody): Flow<Result<UserUpdateResponse>>
    fun getUserById(id: UserIdRequest): Flow<Result<UserIdResponse>>
    fun getSearchUsers(query: String, limit: Int): Flow<Result<SearchUserResponse>>
}