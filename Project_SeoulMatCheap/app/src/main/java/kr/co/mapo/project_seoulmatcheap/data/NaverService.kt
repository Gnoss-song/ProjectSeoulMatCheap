package kr.co.mapo.project_seoulmatcheap.data

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import kr.co.mapo.project_seoulmatcheap.data.response.NaverLoginResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-07
 * @desc 네이버 API 요청 레트로핏
 */

private const val TARGET_ADDRESS = "https://openapi.naver.com/v1/nid/"
private const val ACCESS_TOKEN = "Authorization"

interface NaverService {

    @GET("me")
    fun getNaverEmail (
        @Header(ACCESS_TOKEN) haeder : String
    ) : Call<NaverLoginResponse>

    companion object {
        private var _naverService : NaverService? = null
        private val naverService : NaverService get() = _naverService!!
        operator fun invoke(context: Context) : NaverService {
            if(_naverService != null) {
                return naverService
            } else {
                val requestInterceptor = Interceptor { chain ->
                    val url = chain.request()
                        .url()
                        .newBuilder()
                        .build()
                    val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    Log.e("[TEST]", "$url")
                    return@Interceptor chain.proceed(request)
                }
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(NoConnectionInterceptor.getInstance(context))
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                _naverService = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(TARGET_ADDRESS)
                    .build()
                    .create(NaverService::class.java)
                return naverService
            }
        }
    }

}