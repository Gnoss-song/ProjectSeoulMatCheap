package kr.co.mapo.project_seoulmatcheap.system

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.kakao.sdk.common.KakaoSdk
import kr.co.mapo.project_seoulmatcheap.R
import java.util.*
import kotlin.math.*


/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-06
 * @desc 어플리케이션 클래스 -공통변수, 공통함수, 로케이션, rest 등 설정
 */

private const val r = 6372.8 * 100
const val SEARCH_HISTROY = "search_history_pref"

class SeoulMatCheap : Application() {

    companion object {
        private lateinit var application: SeoulMatCheap
        fun getInstance() : SeoulMatCheap {
            if(this::application.isInitialized) return application
            else {
                application = SeoulMatCheap()
            }
            return application
        }

//        private var _application : SeoulMatCheap? = null
//        private val application get() = _application!!
//        fun getInstance() : SeoulMatCheap {
//            if(_application != null) return application
//            else {
//                _application = SeoulMatCheap()
//            }
//            return application
//        }
    }

    var x : Double = 0.0      //현재 위치 위도
    var y : Double = 0.0      //현재 위치 경도
    var adress : String = "현재위치"     //현재 위치 주소
    lateinit var sharedPreferences : SharedPreferences

    /* onCreate()
     * Activity, Service, Receiver가 생성되기전 어플리케이션이 시작중일때
     * Application onCreate() 메서드가 만들어 진다.
     * */
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(SEARCH_HISTROY, MODE_PRIVATE)
        Log.e("[프리퍼런스]", "${sharedPreferences.all.size}")
        //Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))
        //네이버 아이디 로그인 초기화
    }

    //토스트메세지 출력
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 두 좌표의 거리를 계산한다.
     * @param x 위도
     * @param y 경도
     * @return 두 좌표의 거리(km) - Double
     */
    fun getDistance(x: Double, y: Double) : Double {
        val a = 2 * asin(
            sqrt(
                sin(Math.toRadians(x - this.x) / 2).pow(2.0)
                        + sin(Math.toRadians(y - this.y) / 2).pow(2.0) * cos(Math.toRadians(this.x)) * cos(
                    Math.toRadians(
                        x
                    )
                )
            )
        )
        Log.e("[거리계산]", "${(r * a) / 1000}")
        return (r * a) / 1000
    }

    //GPS로부터 위치정보를 얻어오는 함수
    fun setLocation(context: Context) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // LocationManager.GPS_PROVIDER 또는 LocationManager.NETWORK_PROVIDER 를 얻어온다.
        val provider = locationManager.getBestProvider(Criteria(), true)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "위치정보를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (provider == null) {
            Toast.makeText(context, "위치 정보를 사용할 수 있는 상태가 아닙니다, GPS를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        // 해당 장치가 마지막으로 수신한 위치 얻기
        val location = locationManager.getLastKnownLocation(provider)
        Log.e("[TEST]", provider.toString())
        if(location != null) {
            locationManager.requestLocationUpdates(provider, 400, 1f, LocationListener {  })
            x = location.latitude
            y = location.longitude
            adress = getAddress(x, y, context)
        }
        Log.e("[GPS]", "${x}, ${y}")
    }

    //위도, 경도로부터 주소를 계산하는 함수
    fun getAddress(lat: Double, lng: Double, context: Context): String {
        val geocoder = Geocoder(context, Locale.getDefault()) //주소 구하기 객체
        val address : String = geocoder.getFromLocation(lat, lng, 1)[0].getAddressLine(0) // 현재 주소
        Log.e("[address]", address)
        return address.slice(11 until address.length)
    }

}