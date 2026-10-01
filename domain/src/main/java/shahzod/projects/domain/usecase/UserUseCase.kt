package shahzod.projects.domain.usecase

import jakarta.inject.Inject
import shahzod.projects.core.request.UserIdRequest
import shahzod.projects.core.request.UsersUpdateBody
import shahzod.projects.domain.repository.UserRepository

interface UserUseCase {

    class GetUsers @Inject constructor(
        private val repository: UserRepository
    ) {
        operator fun invoke() = repository.getUsers()
    }

    class UpdateUsers @Inject constructor(
        private val repository: UserRepository
    ) {
        operator fun invoke(request: UsersUpdateBody) = repository.updateUsers(request)
    }

    class GetUserById @Inject constructor(
        private val repository: UserRepository
    ) {
        operator fun invoke(id: UserIdRequest) = repository.getUserById(id)
    }

    class GetSearchUsers @Inject constructor(
        private val repository: UserRepository
    ) {
        operator fun invoke(query: String, limit: Int) = repository.getSearchUsers(query, limit)
    }
}