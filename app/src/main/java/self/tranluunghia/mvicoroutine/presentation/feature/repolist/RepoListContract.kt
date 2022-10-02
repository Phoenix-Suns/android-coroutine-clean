package self.tranluunghia.mvicoroutine.presentation.feature.repolist

import self.tranluunghia.mvicoroutine.core.basemvi.BaseMVIContract
import self.tranluunghia.mvicoroutine.domain.model.GithubRepo
import self.tranluunghia.mvicoroutine.domain.model.GithubUser

sealed class RepoListContract {
    sealed class ListIntent: BaseMVIContract.BaseIntent {
        class GetList(val searchKey: String) : ListIntent()
    }

    sealed class ListState: BaseMVIContract.BaseState {
        class ShowUserInfo(val userInfo: GithubUser) : ListState()
        class ShowRepoList(val repoList: List<GithubRepo>) : ListState()

    }
}