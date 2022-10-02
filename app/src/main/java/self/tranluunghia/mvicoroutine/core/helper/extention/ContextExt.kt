package self.tranluunghia.mvicoroutine.core.helper.extention

import android.content.Context
import android.widget.Toast


fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}