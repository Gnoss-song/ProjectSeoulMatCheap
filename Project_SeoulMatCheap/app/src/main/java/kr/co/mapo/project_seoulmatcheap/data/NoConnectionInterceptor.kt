package kr.co.mapo.project_seoulmatcheap.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-04
 * @desc 서버로부터 데이터 처리 요청시 네트워크연결상태 감지 인터셉터
 */

class NoConnectionInterceptor(
    private val context: Context) : Interceptor {

    companion object {
        private lateinit var instance : NoConnectionInterceptor
        fun getInstance(context: Context) : NoConnectionInterceptor {
            if(!this::instance.isInitialized)
                instance = NoConnectionInterceptor(context)
            return instance
        }
    }

    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn()) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }

    @Suppress("DEPRECATION")
    private fun preAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo

        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }

    private fun postAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    private fun isConnectionOn(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        return if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.M) {
            postAndroidMInternetCheck(connectivityManager)
        } else {
            preAndroidMInternetCheck(connectivityManager)
        }
    }

    class NoConnectivityException : IOException() {
        override val message: String
            get() = "인터넷 연결이 끊겼습니다. WIFI나 데이터 연결을 확인해주세요"
    }

}