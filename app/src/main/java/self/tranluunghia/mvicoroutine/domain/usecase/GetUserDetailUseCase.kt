package self.tranluunghia.mvicoroutine.domain.usecase

import kotlinx.coroutines.flow.Flow
import self.tranluunghia.mvicoroutine.core.basemvi.UseCase
import self.tranluunghia.mvicoroutine.core.entity.DataState
import self.tranluunghia.mvicoroutine.domain.model.GithubUser
import self.tranluunghia.mvicoroutine.domain.repository.GithubUserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) : UseCase<GetUserDetailUseCase.Params, Flow<DataState<GithubUser>>> {

    override fun invoke(params: Params): Flow<DataState<GithubUser>> =
        githubUserRepository.getUserDetail(params.username)

    class Params(val username: String)
}