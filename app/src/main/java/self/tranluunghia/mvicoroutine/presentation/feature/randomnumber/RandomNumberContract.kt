package self.tranluunghia.mvicoroutine.presentation.feature.randomnumber

import self.tranluunghia.mvicoroutine.core.basemvi.BaseMVIContract

sealed class RandomNumberContract {
    sealed class Event: BaseMVIContract.BaseEvent {
        object OnRandomNumberClicked : Event()
        object OnShowToastClicked : Event()
    }

    sealed class Effect: BaseMVIContract.BaseEffect {
        object ShowToast : Effect()
    }


    //#region State
    /*sealed class State: BaseMVIContract.BaseState {
        object Idle : State()
        object Loading : State()
        data class Success(val number : Int) : State()
    }*/

    /*data class State(
        val isLoading: Boolean = false,
        val randomNumber: Int = -1,
        val error: String? = null
    ) : UiState*/

    // Ui View States
    data class State(
        val randomNumberState: RandomNumberState
    ) : BaseMVIContract.BaseState

    // View State that related to Random Number
    sealed class RandomNumberState {
        object Idle : RandomNumberState()
        object Loading : RandomNumberState()
        data class Success(val number : Int) : RandomNumberState()
    }
    //#endregion
}