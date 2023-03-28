package self.tranluunghia.mvicoroutine.core.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.StrictMode
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


private const val TIMEOUT = 3000 // TIMEOUT

/**
 * Loại kết nối, không kết nối, qua wifi, qua mạng
 */
enum class ConnectionType {
    NOT_CONNECTED,
    TYPE_WIFI,
    TYPE_ETHERNET, TYPE_MOBILE_DATA
}

/**
 * Created by Nghia-PC on 9/16/2015.
 */
object NetworkHelper {
    /**
     * Lấy phương thức kết nối Mạng
     * @param context
     * @return
     */
    @JvmStatic
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun getConnectivityStatus(context: Context): ConnectionType  {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val nw = connectivityManager.activeNetwork
            val actNw = connectivityManager.getNetworkCapabilities(nw)

            return when {
                actNw?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> ConnectionType.TYPE_WIFI
                actNw?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> ConnectionType.TYPE_MOBILE_DATA
                actNw?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> ConnectionType.TYPE_ETHERNET
                else -> ConnectionType.NOT_CONNECTED
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null) {
                when (networkInfo.type) {
                    ConnectivityManager.TYPE_WIFI -> return ConnectionType.TYPE_WIFI
                    ConnectivityManager.TYPE_MOBILE -> return ConnectionType.TYPE_MOBILE_DATA
                    ConnectivityManager.TYPE_ETHERNET -> return ConnectionType.TYPE_ETHERNET
                }
            }
            return ConnectionType.NOT_CONNECTED
        }
    }

    @JvmStatic
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            val validated = networkCapabilities == null || !networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
            return !validated
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnected ?: false
        }
    }

    /**
     * ping "http://www.baidu.com"
     *
     * @return
     */
    @JvmStatic
    private fun pingNetWork(): Boolean {
        var result = false
        var httpUrl: HttpURLConnection? = null
        try {
            httpUrl = URL("http://www.baidu.com")
                .openConnection() as HttpURLConnection
            httpUrl.connectTimeout = TIMEOUT
            httpUrl.connect()
            result = true
        } catch (e: IOException) {
        } finally {
            httpUrl?.disconnect()
        }
        return result
    }

    /** check response
     *
     */
    @JvmStatic
    fun hasResponse(url: String) : Boolean = getResponseCode(url) == 200

    /**
     * check response of link 200, 403...
     */
    @JvmStatic
    fun getResponseCode(url: String) : Int {
        try {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            return connection.responseCode

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return 404
    }
}
