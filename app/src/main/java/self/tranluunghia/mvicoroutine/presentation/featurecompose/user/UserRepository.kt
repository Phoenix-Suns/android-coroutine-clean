package self.tranluunghia.mvicoroutine.presentation.featurecompose.user

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    fun getUser(id: Long): User? {
        return getUsers().find { it.id == id }
    }

    fun getUsers(): List<User> {
        return listOf(
            User(id = 123, name = "James Bond", "jamesbond@007.com"),
            User(id = 345, name = "Batman", "batman@cave.com"),
            User(id = 999, name = "Arya Stark", "arya@winterfell.com")
        )
    }
}

data class User(
    val id: Long,
    val name: String,
    val email: String
)