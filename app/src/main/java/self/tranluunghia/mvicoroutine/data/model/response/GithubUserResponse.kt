package self.tranluunghia.mvicoroutine.data.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GithubUserResponse(
    @SerializedName("role")
    var role: String?,
    @SerializedName("is_active")
    var isActive: Boolean = false,
    @SerializedName("dial_code")
    var dialCode: String?,
    @SerializedName("sex")
    var sex: String?,
    @SerializedName("name")
    var name: String ="",
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("_id")
    var id: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("email")
    var email: String,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("picture")
    var picture: String?,
    @SerializedName("dob")
    var dob: String?,
    @SerializedName("total_follower")
    var totalFollower: Int?,
    @SerializedName("total_following")
    var totalFollowing: Int?,
    @SerializedName("is_follow")
    var isFollow: Boolean?
)