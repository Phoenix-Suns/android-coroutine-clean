package self.tranluunghia.mvicoroutine.core.helper.extention

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun Context.getAppColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getBitmapFromDrawable(@DrawableRes resourceId: Int, width: Int, height: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(this, resourceId)
    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    canvas.setBitmap(bitmap)
    drawable?.setBounds(0, 0, width, height)
    drawable?.draw(canvas)
    return bitmap
}

val Int.pxTodp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.dpTopx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()