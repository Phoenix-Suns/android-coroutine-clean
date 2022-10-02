package self.tranluunghia.mvicoroutine.data.model
import com.google.gson.annotations.SerializedName


data class GithubList<T>(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean?,
    @SerializedName("items")
    var items: List<T>,
    @SerializedName("total_count")
    var totalCount: Int?
)