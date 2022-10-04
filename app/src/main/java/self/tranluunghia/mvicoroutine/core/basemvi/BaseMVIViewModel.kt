package self.tranluunghia.mvicoroutine.core.basemvi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import self.tranluunghia.mvicoroutine.core.entity.ErrorInfo
import self.tranluunghia.mvicoroutine.core.entity.ErrorType
import self.tranluunghia.mvicoroutine.core.helper.SingleLiveEvent

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseMVIViewModel<
        EVENT : BaseMVIContract.BaseEvent,
        STATE : BaseMVIContract.BaseState,
        EFFECT : BaseMVIContract.BaseEffect> : ViewModel() {

    private val tag by lazy { this::class.java.name }

    var viewModelJob = Job()
    private val ioContext = viewModelJob + Dispatchers.IO
    private val uiContext = viewModelJob + Dispatchers.Main
    val ioScope = CoroutineScope(ioContext)
    val uiScope = CoroutineScope(uiContext)


    // Create Initial State of View
    private val initialState : STATE by lazy { createInitialState() }
    abstract fun createInitialState() : STATE

    // Get Current State
    val currentState: STATE
        get() = uiState.value

    private val _uiState : MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event : MutableSharedFlow<EVENT> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect : Channel<EFFECT> = Channel()
    val effect = _effect.receiveAsFlow()


    // Common error
    val loadingEvent = MutableLiveData<Boolean>()
    val errorMessageEvent = SingleLiveEvent<String>()
    val noInternetConnectionEvent = SingleLiveEvent<Unit>()
    val connectTimeoutEvent = SingleLiveEvent<Unit>()
    val tokenExpiredEvent = SingleLiveEvent<Unit>()

    init {
        viewModelScope.launch {
            _event.collect { viewEvent ->
                handleEvents(viewEvent)
            }
        }
    }

    abstract fun handleEvents(viewEvent: EVENT)

    fun setEvent(event: EVENT) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    /**
     * Set new Ui State
     */
    protected fun setState(reduce: STATE.() -> STATE) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun callbackState(state: STATE) {
        viewModelScope.launch {
            _uiState.emit(state)
        }
    }


    /**
     * Set new Effect
     */
    protected fun setEffect(builder: () -> EFFECT) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    protected fun callbackEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    protected fun handleError(
        errorInfo: ErrorInfo?,
        apiErrorMessage: String? = null,
        showPopup: Boolean = false
    ) {
        errorInfo?.let {
            if (errorInfo.errorType == ErrorType.NETWORK)
                noInternetConnectionEvent.call()
            else if (errorInfo.errorType == ErrorType.SERVER && (errorInfo.code == 401 || errorInfo.code == 403))
                tokenExpiredEvent.call()
            else if (errorInfo.errorType == ErrorType.API) {
                Log.e(tag, "API ERROR: $apiErrorMessage")
                if (showPopup) errorMessageEvent.postValue(apiErrorMessage ?: "API error!")
            } else {
                Log.e(tag, errorInfo.exception?.message.toString())
                if (showPopup) errorMessageEvent.postValue(errorInfo.exception?.message)
            }
        }
    }

    override fun onCleared() {
        // todo viewModel cleared nhưng không khởi tạo lại, thử Swipe Pager fragment
        //viewModelJob.cancel()
        viewModelJob.cancelChildren()
        super.onCleared()
    }
}