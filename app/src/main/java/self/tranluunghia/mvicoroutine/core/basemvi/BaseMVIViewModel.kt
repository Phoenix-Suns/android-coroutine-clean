package self.tranluunghia.mvicoroutine.core.basemvi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import self.tranluunghia.mvicoroutine.core.entity.ErrorInfo
import self.tranluunghia.mvicoroutine.core.entity.ErrorType
import self.tranluunghia.mvicoroutine.core.helper.SingleLiveEvent

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseMVIViewModel<INTENT: BaseMVIContract.BaseIntent, STATE: BaseMVIContract.BaseState> : ViewModel() {
    private val tag by lazy { this::class.java.name }

    var viewModelJob = Job()
    private val ioContext = viewModelJob + Dispatchers.IO
    private val uiContext = viewModelJob + Dispatchers.Main
    val ioScope = CoroutineScope(ioContext)
    val uiScope = CoroutineScope(uiContext)

    private val uiEvent = MutableSharedFlow<INTENT>()
    val callbackState = MutableSharedFlow<STATE>()

    // Common error
    val loadingEvent = MutableLiveData<Boolean>()
    val errorMessageEvent = SingleLiveEvent<String>()
    val noInternetConnectionEvent = SingleLiveEvent<Unit>()
    val connectTimeoutEvent = SingleLiveEvent<Unit>()
    val tokenExpiredEvent = SingleLiveEvent<Unit>()

    init {
        viewModelScope.launch {
            uiEvent.collect { viewIntent ->
                handleIntents(viewIntent)
            }
        }
    }

    abstract fun handleIntents(viewIntent: INTENT)

    fun sendIntent(intent: INTENT) {
        viewModelScope.launch {
            uiEvent.emit(intent)
        }
    }

    protected fun callbackState(state: STATE) {
        viewModelScope.launch {
            callbackState.emit(state)
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