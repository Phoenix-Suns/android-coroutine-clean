package self.tranluunghia.mvicoroutine.presentation.featurecompose.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import self.tranluunghia.mvicoroutine.core.entity.DataState
import self.tranluunghia.mvicoroutine.domain.model.Paging
import self.tranluunghia.mvicoroutine.domain.usecase.GetRepoListUseCase
import javax.inject.Inject

@HiltViewModel
class FoodCategoriesViewModel @Inject constructor(
    private val getRepoListUseCase: GetRepoListUseCase
) : BaseViewModel<
        FoodCategoriesContract.Event,
        FoodCategoriesContract.State,
        FoodCategoriesContract.Effect>() {

    var state by mutableStateOf(
        FoodCategoriesContract.State(
            categories = listOf(),
            isLoading = true
        )
    )
        private set

    var effects = Channel<FoodCategoriesContract.Effect>(UNLIMITED)
        private set

    private var searchKey: String = "mario"
    private var repoListPaging: Paging = Paging()

    init {
        getFoodCategories()
    }

    override fun setInitialState() =
        FoodCategoriesContract.State(categories = listOf(), isLoading = true)

    override fun handleEvents(event: FoodCategoriesContract.Event) {
        /*when (event) {
            is FoodCategoriesContract.Event.CategorySelection -> {
                setEffect {
                    FoodCategoriesContract.Effect.Navigation.ToCategoryDetails(event.categoryName)
                }
            }
        }*/
    }

    private fun getFoodCategories() {
        ioScope.launch {
            val flow = getRepoListUseCase.invoke(
                GetRepoListUseCase.Params(
                    perPage = repoListPaging.perPage,
                    page = repoListPaging.page,
                    keyWork = searchKey
                )
            )
            //delay(3000)
            flow.collect { dataState ->

                when (dataState.status) {
                    DataState.Status.SUCCESS -> {
                        //callbackState(RepoListContract.State.ShowRepo(dataState.data!!))
                        val categories = dataState.data ?: ArrayList()
                        state = state.copy(categories = categories, isLoading = false)
                        effects.send(FoodCategoriesContract.Effect.ToastDataWasLoaded)
                    }
                    DataState.Status.ERROR -> {
                        effects.send(FoodCategoriesContract.Effect.ToastDataWasLoaded)
                    }
                    DataState.Status.LOADING -> {

                    }
                }
            }
        }
    }
}