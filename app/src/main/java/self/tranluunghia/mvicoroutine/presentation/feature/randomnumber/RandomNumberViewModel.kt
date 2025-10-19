package self.tranluunghia.mvicoroutine.presentation.feature.randomnumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import self.tranluunghia.mvicoroutine.core.basemvi.BaseMVIViewModel
import javax.inject.Inject

@HiltViewModel
class RandomNumberViewModel @Inject constructor(

) : BaseMVIViewModel<RandomNumberContract.Event, RandomNumberContract.State, RandomNumberContract.Effect>() {

    override fun createInitialState() = RandomNumberContract.State(RandomNumberContract.RandomNumberState.Idle)

    override fun handleEvents(viewEvent: RandomNumberContract.Event) {
        when (viewEvent) {
            is RandomNumberContract.Event.OnRandomNumberClicked -> { generateRandomNumber() }
            is RandomNumberContract.Event.OnShowToastClicked -> {
                callbackEffect(RandomNumberContract.Effect.ShowToast)
            }
        }
    }

    /**
     * Generate a random number
     */
    private fun generateRandomNumber() {
        ioScope.launch {
            // Set Loading
            setState { copy(randomNumberState = RandomNumberContract.RandomNumberState.Loading) }
            try {
                // Add delay for simulate network call
                delay(1000)
                val random = (0..10).random()
                if (random % 2 == 0) {
                    // If error happens set state to Idle
                    // If you want create a Error State and use it
                    setState { copy(randomNumberState = RandomNumberContract.RandomNumberState.Idle) }
                    throw RuntimeException("Number is even")
                }
                // Update state
                setState { copy(randomNumberState = RandomNumberContract.RandomNumberState.Success(number = random)) }
            } catch (exception : Exception) {
                // Show error
                setEffect { RandomNumberContract.Effect.ShowToast }
            }
        }
    }
}
