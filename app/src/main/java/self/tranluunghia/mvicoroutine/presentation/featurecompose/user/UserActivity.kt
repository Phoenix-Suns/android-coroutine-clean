package self.tranluunghia.mvicoroutine.presentation.featurecompose.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

// Single Activity per app
@AndroidEntryPoint
class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                UserApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UserApp()
}

@Composable
fun UserApp() {
    val navController = rememberNavController()

    // Nav Graph
    NavHost(
        navController = navController,
        startDestination = "user_list",
    ) {
        composable(route = "user_list") {
            // inject View Model
            val vm: HomeScreenViewModel = hiltViewModel()
            UserListPage(navController = navController, vm = vm)
        }
        composable(route = "user_detail/{user_id}") { backStackEntry ->
            // inject View Model
            val vm: UserDetailScreenViewModel = hiltViewModel()
            val userId = (backStackEntry.arguments?.getString("user_id", "") ?: "").toLong()
            UserDetailPage(navController = navController, vm = vm, userId)
        }
    }
}

