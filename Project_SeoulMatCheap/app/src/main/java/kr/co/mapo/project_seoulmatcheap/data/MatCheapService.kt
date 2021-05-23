package kr.co.mapo.project_seoulmatcheap.data

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.Single
import kr.co.mapo.project_seoulmatcheap.data.response.MemberResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-07
 * @desc 서울맛칩 서버 API 요청 레트로핏
 */

private const val TARGET_ADDRESS = "http://52.78.206.155:8081/"
private const val MEMBER_CATEGORY_CODE = "member_category_code"
private const val EMAIL = "email"
private const val PROFILE_URL = "profile_url"
private const val NICKNAME = "nickname"

interface MatCheapService {

    @Headers("Content-Type: application/json")
    @POST("api/member")
    fun serviceLogin (
        @Body loginBody: LoginBody
    ) : Single<MemberResponse>

    companion object {
        private var _matcheapService : MatCheapService? = null
        private val matcheapService : MatCheapService get() = _matcheapService!!
        operator fun invoke(context: Context) : MatCheapService {
            if(_matcheapService != null) {
                return matcheapService
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
                _matcheapService = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(TARGET_ADDRESS)
                    .build()
                    .create(MatCheapService::class.java)
                return matcheapService
            }
        }
    }

}