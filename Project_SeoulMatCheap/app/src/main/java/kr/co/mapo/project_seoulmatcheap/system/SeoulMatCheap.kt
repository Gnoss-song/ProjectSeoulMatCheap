package kr.co.mapo.project_seoulmatcheap.system

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.kakao.sdk.common.KakaoSdk
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import java.lang.Math.*
import java.util.*
import kotlin.math.pow


/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-06
 * @desc 어플리케이션 클래스 -공통변수, 공통함수, 로케이션
 */

private const val r = 6372.8 * 1000

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

    var x = SEOULCITYHALL_X   //위도
    var y = SEOULCITYHALL_Y     //경도
    val address = MutableLiveData<String>()
    val location = MutableLiveData<Location>()
    var filterList = listOf<String>()
    var storeList = listOf<StoreEntity>()

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
                    if(location == null) {
                        locationManager.requestLocationUpdates(provider, 400, 1f) {
                            updateLocation(it, context)
                        }
                    } else {
                        updateLocation(location, context)
                    }
                }
        }
    }

    //위도, 경도로부터 주소를 계산하는 함수 : 인터넷문제
    private fun updateLocation(location: Location, context: Context) {
        this.location.value = location
        this.x = location.latitude
        this.y = location.longitude
        //인터넷연결 불안정할시 Geocoder X
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if(capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
            || capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true) {
            this.address.value = Geocoder(context, Locale.getDefault())
                .getFromLocation(x, y, 1)[0]
                .getAddressLine(0)
                .substring(11)
            Log.e("[GSP]", "$x, $y, ${address.value}")
        }
    }

    //현재 위경도에서부터 떨어져 있는 거리 계산
    fun calculateDistance(lat: Double, lng : Double) : String {
        val a = 2 * asin(sqrt(sin(toRadians(lat - this.x) / 2).pow(2.0)
                        + sin(toRadians(lng - this.y) / 2).pow(2.0) * cos(toRadians(this.x)) * cos(toRadians(lat))))
        return String.format("%.1fkm", (r * a) / 1000)
    }

    fun calculateDistanceDou(lat: Double, lng : Double) : Double {
        val a = 2 * asin(sqrt(sin(toRadians(lat - this.x) / 2).pow(2.0)
                + sin(toRadians(lng - this.y) / 2).pow(2.0) * cos(toRadians(this.x)) * cos(toRadians(lat))))
        return (r * a) / 1000
    }

    fun getAutoComplete(context: Context, lifecycleOwner: LifecycleOwner) {
        AppDatabase(context)!!.storeDAO().getAutoComplete().observe(lifecycleOwner, {
            filterList = it
        })
    }
}