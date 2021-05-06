package kr.co.mapo.project_seoulmatcheap.system

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.util.FusedLocationSource
import kr.co.mapo.project_seoulmatcheap.R
import java.util.*


/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-06
 * @desc 어플리케이션 클래스 -공통변수, 공통함수, 로케이션, rest 등 설정
 */

const val SEARCH_HISTROY = "search_history_prefs"
const val SEOULCITYHALL_X = 37.5662952
const val SEOULCITYHALL_Y = 126.9779451
const val SEOULCITYHALL_ADDRESS = "중구 세종대로 110 서울특별시청"

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
    }

    var x : Double = SEOULCITYHALL_X     //위도
    var y : Double = SEOULCITYHALL_Y      //경도
    var address : MutableLiveData<String> = MutableLiveData()

    init {
        this.address.value = SEOULCITYHALL_ADDRESS
    }

    /* onCreate()
     * Activity, Service, Receiver가 생성되기전 어플리케이션이 시작중일때
     * Application onCreate() 메서드가 만들어 진다.
     * */
    override fun onCreate() {
        super.onCreate()
        //Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))
    }

    //토스트메세지 출력
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
            showToast(context, context.getString(R.string.gps_notice))
            return
        } else {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        x = location.latitude
                        y = location.longitude
                        address.value = getAddress(x, y, context)
                    }
                    Log.e("[GPS]", "$x, $y, ${address.value}")
                }

        }
    }

    //위도, 경도로부터 주소를 계산하는 함수
    fun getAddress(lat: Double, lng: Double, context: Context): String {
        val geocoder = Geocoder(context, Locale.getDefault()) //주소 구하기 객체
        val address : String = geocoder.getFromLocation(lat, lng, 1)[0].getAddressLine(0) // 현재 주소
        Log.e("[address]", address)
        return address.slice(11 until address.length)
    }

}