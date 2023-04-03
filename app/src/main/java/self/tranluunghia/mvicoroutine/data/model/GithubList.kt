package self.tranluunghia.mvicoroutine.data.model
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GithubList<T>(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean?,
    @SerializedName("items")
    var items: List<T>,
    @SerializedName("total_count")
    var totalCount: Int?
)