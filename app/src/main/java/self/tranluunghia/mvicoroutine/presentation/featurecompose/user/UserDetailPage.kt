package self.tranluunghia.mvicoroutine.presentation.featurecompose.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

import androidx.compose.runtime.getValue

@Composable
fun UserDetailPage(navController: NavController, vm: UserDetailScreenViewModel, userId: Long) {
    Column {
        vm.load(userId = userId)
        //import androidx.compose.runtime.collectAsState
        //import androidx.compose.runtime.getValue
        val user by vm.user.collectAsState()
        Column(Modifier.padding(all = 16.dp)) {
            Text(text = "Hello, I'm ${user?.name}")
            Text(text = "My email is ${user?.email}")
        }
    }

}

@HiltViewModel
class UserDetailScreenViewModel @Inject constructor(val userRepository: UserRepository) :
    ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun load(userId: Long) {
        _user.value = userRepository.getUser(id = userId)
    }
}