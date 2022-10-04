package self.tranluunghia.mvicoroutine.presentation.feature.repolist

import self.tranluunghia.mvicoroutine.core.basemvi.BaseMVIContract
import self.tranluunghia.mvicoroutine.domain.model.GithubRepo
import self.tranluunghia.mvicoroutine.domain.model.GithubUser

sealed class RepoListContract {
    sealed class Event: BaseMVIContract.BaseEvent {
        data class GetList(val searchKey: String) : Event()
    }

    sealed class State: BaseMVIContract.BaseState {
        object Idle : State()

        data class ShowUserInfo(val userInfo: GithubUser) : State()
        data class ShowRepo(val repoList: List<GithubRepo>) : State()
    }

    sealed class Effect: BaseMVIContract.BaseEffect {
        object ShowToast : Effect()
    }
}