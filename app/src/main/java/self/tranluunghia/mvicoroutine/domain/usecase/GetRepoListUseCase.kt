package self.tranluunghia.mvicoroutine.domain.usecase

import kotlinx.coroutines.flow.Flow
import self.tranluunghia.mvicoroutine.core.basemvi.UseCase
import self.tranluunghia.mvicoroutine.core.entity.DataState
import self.tranluunghia.mvicoroutine.domain.model.GithubRepo
import self.tranluunghia.mvicoroutine.domain.repository.GithubUserRepository
import javax.inject.Inject

class GetRepoListUseCase @Inject constructor(
    private val repository: GithubUserRepository
) : UseCase<GetRepoListUseCase.Params, Flow<DataState<List<GithubRepo>>>> {

    override fun invoke(params: Params): Flow<DataState<List<GithubRepo>>> =
        repository.getRepoList(params.keyWork, params.page, params.perPage)

    class Params(
        val perPage: Int,
        val page: Int,
        val keyWork: String
    )
}