package self.tranluunghia.mvicoroutine.domain.model

import androidx.annotation.Keep

@Keep
data class GithubUser(
    var role: String?,
    var isActive: Boolean = false,
    var dialCode: String?,
    var sex: String?,
    var name: String?,
    var createdAt: String?,
    var id: String?,
    var type: String?,
    var email: String?,
    var phone: String?,
    var picture: String?,
    var dob: String?,
    var totalFollower: Int?,
    var totalFollowing: Int?,
    var isFollow: Boolean?
)