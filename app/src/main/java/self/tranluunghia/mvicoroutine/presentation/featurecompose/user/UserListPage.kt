package self.tranluunghia.mvicoroutine.presentation.featurecompose.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

import androidx.compose.runtime.getValue

@Composable
fun UserListPage(navController: NavController, vm: HomeScreenViewModel) {

    Column {
        //import androidx.compose.runtime.collectAsState
        //import androidx.compose.runtime.getValue
        val users by vm.users.collectAsState()
        users.forEach { user ->
            ClickableText(text = AnnotatedString(user.name), Modifier.padding(all = 16.dp),
                onClick = {
                    navController.navigate("user_detail/${user.id}")
                })
        }
    }
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {
    private val _users = MutableStateFlow(userRepository.getUsers())
    val users: StateFlow<List<User>> = _users
}