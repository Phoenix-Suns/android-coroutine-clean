package self.tranluunghia.mvicoroutine.data.model

import androidx.annotation.Keep

@Keep
data class FoodItem(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val description: String = ""
)