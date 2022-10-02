package self.tranluunghia.mvicoroutine.core.helper.extention

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.DecimalFormat

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this.viewLifecycleOwner, Observer(body))

fun Any.logD(message: String?) {
    Log.d(this::class.java.simpleName, "" + message)
}

fun Any.logE(message: String?) {
    Log.e(this::class.java.toString(), "" + message)
}

//Returns true if no exception was caught. Otherwise, it logs the exception and returns false
fun tryCatch(body: () -> Unit): Boolean {
    return try {
        body()
        true
    } catch (e: Throwable) {
        body.logE(e.message)
        false
    }
}

fun Float.roundOff(): String {
    val df = DecimalFormat("##.##")
    return df.format(this)
}

tailrec fun Context.activity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}

/**
 * Make Json more beautiful, Add /n /t to Json
 */
fun String.toPrettyJSONString(): String {

    val json = StringBuilder()
    var indentString = ""

    for (element in this) {
        when (element) {
            '{', '[' -> {
                json.append("\n" + indentString + element + "\n")
                indentString += "\t"
                json.append(indentString)
            }
            '}', ']' -> {
                indentString = indentString.replaceFirst("\t".toRegex(), "")
                json.append("\n" + indentString + element)
            }
            ',' -> json.append(element + "\n" + indentString)

            else -> json.append(element)
        }
    }

    return json.toString()
}
