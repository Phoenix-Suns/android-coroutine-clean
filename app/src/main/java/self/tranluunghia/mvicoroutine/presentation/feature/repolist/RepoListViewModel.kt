package self.tranluunghia.mvicoroutine.presentation.feature.repolist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import self.tranluunghia.mvicoroutine.core.basemvi.BaseMVIViewModel
import self.tranluunghia.mvicoroutine.core.entity.DataState
import self.tranluunghia.mvicoroutine.domain.model.Paging
import self.tranluunghia.mvicoroutine.domain.usecase.GetRepoListUseCase
import self.tranluunghia.mvicoroutine.domain.usecase.GetUserDetailUseCase
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getRepoListUseCase: GetRepoListUseCase
) : BaseMVIViewModel<RepoListContract.ListIntent, RepoListContract.ListState>() {

    private var searchKey: String = ""
    private var repoListPaging: Paging = Paging()

    override fun handleIntents(viewIntent: RepoListContract.ListIntent) {
        when (viewIntent) {
            is RepoListContract.ListIntent.GetList -> {
                getListRepo(viewIntent.searchKey)
            }
        }
    }

    private fun getListRepo(searchKey: String) {
        this.searchKey = searchKey

        ioScope.launch {
            val flow = getRepoListUseCase.invoke(GetRepoListUseCase.Params(
                perPage = repoListPaging.perPage, page = repoListPaging.page, keyWork = searchKey
            ))
            //delay(3000)
            flow.collect { dataState ->

                when (dataState.status) {
                    DataState.Status.SUCCESS -> {
                        callbackState(RepoListContract.ListState.ShowRepoList(dataState.data!!))
                    }
                    DataState.Status.ERROR -> {

                    }
                    DataState.Status.LOADING -> {

                    }
                }
            }
        }
    }

    private fun getUserInfo() {
        ioScope.launch {
            getUserDetailUseCase.invoke(GetUserDetailUseCase.Params("ahmedrizwan"))
                .collect { dataState ->
                    when (dataState.status) {
                        DataState.Status.SUCCESS -> {
                            callbackState(RepoListContract.ListState.ShowUserInfo(dataState.data!!))
                        }
                        DataState.Status.ERROR -> {

                        }
                        DataState.Status.LOADING -> {

                        }
                    }
                }
        }
    }
}
