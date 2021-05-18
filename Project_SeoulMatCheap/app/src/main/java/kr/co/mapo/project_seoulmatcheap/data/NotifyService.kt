package kr.co.mapo.project_seoulmatcheap.data

import com.google.gson.GsonBuilder
import io.reactivex.Single
import kr.co.mapo.project_seoulmatcheap.data.response.NotifyResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-05-17
 * @desc
 */

const val BASE_URL= "http://52.78.206.155:8081/"


interface NotifyService {
    @GET("api/notice")
    fun getTest(
    ) : Single<NotifyResponse>

    /*
    @GET("MY01")
    fun get(
    프로필사진, 찜가게, 마이가게, 레벨 , 닉네임 , 이메일주소

    ) : Single<NotifyResponse>

    @GET("MY0102")
    fun get(

    )  : Single<NotifyResponse>


     */


    companion object{
        private var _notifyService : NotifyService? = null
        private val notifyService get() = _notifyService!!
        operator fun invoke() : NotifyService {
            if(_notifyService != null){
                return notifyService
            }else {
                val requestInterceptor = Interceptor {
                    val url = it.request()
                        .url()
                        .newBuilder()
                        .build()
                    val request = it.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    return@Interceptor it.proceed(request)
                }
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                _notifyService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
                    .create(NotifyService::class.java)
            }
            return notifyService
        }
    }
}

