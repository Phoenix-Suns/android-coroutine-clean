package self.tranluunghia.mvicoroutine.core.helper.extention

import android.app.Activity
import android.content.ContextWrapper
import android.os.Build
import android.os.SystemClock
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.hapticFeedback() {
    performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
}

fun View.singleClick(listener: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(listener))
}

class OnSingleClickListener : View.OnClickListener {

    companion object {
        private const val DELAY_MILLIS = 200L
        private var previousClickTimeMillis = 0L
    }

    private val onClickListener: View.OnClickListener

    constructor(listener: View.OnClickListener) {
        onClickListener = listener
    }

    constructor(listener: (View) -> Unit) {
        onClickListener = View.OnClickListener { listener.invoke(it) }
    }

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - previousClickTimeMillis > DELAY_MILLIS) {
            previousClickTimeMillis = SystemClock.elapsedRealtime()
            onClickListener.onClick(v)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun View.addKeyboardVisibleListener(keyboardCallback: (keyboardVisible: Boolean) -> Unit) {
    doOnLayout {
        var keyboardVisible = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
        keyboardCallback(keyboardVisible)

        //whenever the layout resizes/changes, callback with the state of the keyboard.
        viewTreeObserver.addOnGlobalLayoutListener {
            val keyboardUpdateCheck = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
            //since the observer is hit quite often, only callback when there is a change.
            if (keyboardUpdateCheck != keyboardVisible) {
                keyboardCallback(keyboardUpdateCheck)
                keyboardVisible = keyboardUpdateCheck
            }
        }
    }
}