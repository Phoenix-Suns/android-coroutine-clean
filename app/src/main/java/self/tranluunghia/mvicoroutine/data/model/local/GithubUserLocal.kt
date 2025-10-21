package self.tranluunghia.mvicoroutine.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_user")
data class GithubUserLocal(
    var role: String?,
    var isActive: Boolean = false,
    var dialCode: String?,
    var sex: String?,
    var name: String?,
    var createdAt: String?,
    @PrimaryKey var id: String,
    var type: String?,
    var email: String?,
    var phone: String?,
    var picture: String?,
    var dob: String?,
    var totalFollower: Int?,
    var totalFollowing: Int?,
    var isFollow: Boolean?
)