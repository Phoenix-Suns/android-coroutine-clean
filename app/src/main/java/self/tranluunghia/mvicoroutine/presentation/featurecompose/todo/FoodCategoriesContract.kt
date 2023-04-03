package self.tranluunghia.mvicoroutine.presentation.featurecompose.todo

import self.tranluunghia.mvicoroutine.data.model.FoodItem
import self.tranluunghia.mvicoroutine.domain.model.GithubRepo

class FoodCategoriesContract {
    sealed class Event : BaseContract.ViewEvent {
        data class CategorySelection(
            val categoryName: Int?
        ) : Event()
    }

    data class State(
        val categories: List<GithubRepo> = listOf(),
        val isLoading: Boolean = false
    ) : BaseContract.ViewState

    sealed class Effect : BaseContract.ViewSideEffect {
        object ToastDataWasLoaded : Effect()
    }
}