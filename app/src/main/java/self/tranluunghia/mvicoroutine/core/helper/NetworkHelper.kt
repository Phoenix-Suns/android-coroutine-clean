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
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo

        if (networkInfo != null) {
            when (networkInfo.type) {
                ConnectivityManager.TYPE_WIFI -> return ConnectionType.TYPE_WIFI
                ConnectivityManager.TYPE_MOBILE -> return ConnectionType.TYPE_MOBILE_DATA
                ConnectivityManager.TYPE_ETHERNET -> return ConnectionType.TYPE_ETHERNET
            }
        }
        return ConnectionType.NOT_CONNECTED
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

    @JvmStatic
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isWiFiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
        }
    }

    @JvmStatic
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun is3GConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE
        }
    }

    @JvmStatic
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun is2G(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE
                || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
            .subtype == TelephonyManager.NETWORK_TYPE_CDMA)
    }


    /**
     * is wifi on
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isWifiEnabled(context: Context): Boolean {
        val mgrConn = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mgrTel = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return mgrConn.activeNetworkInfo != null
                && mgrConn.activeNetworkInfo?.state == NetworkInfo.State.CONNECTED
                || mgrTel.networkType == TelephonyManager.NETWORK_TYPE_UMTS
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
