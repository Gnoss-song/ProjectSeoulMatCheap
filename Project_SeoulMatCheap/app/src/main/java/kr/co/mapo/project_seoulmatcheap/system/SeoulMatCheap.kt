package kr.co.mapo.project_seoulmatcheap.system

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kr.co.mapo.project_seoulmatcheap.R
import java.util.*
import kotlin.math.*

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-06
 * @desc 어플리케이션 클래스(싱글톤)-공통변수, 공통함수, 로케이션, rest 등 설정
 */

private const val R = 6372.8 * 1000

class SeoulMatCheap : Application() {

    var latX : Double = 0.0      //현재 위치 위도
    var lngY : Double = 0.0      //현재 위치 경도
    var adress : String = "현재위치"     //현재 위치 주소

    /* onCreate()
     * Activity, Service, Receiver가 생성되기전 어플리케이션이 시작중일때
     * Application onCreate() 메서드가 만들어 진다.
     * */
    override fun onCreate() {
        super.onCreate()
    }

    //토스트메세지 출력
    fun showToast(message : String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 두 좌표의 거리를 계산한다.
     * @param x 위도
     * @param y 경도
     * @return 두 좌표의 거리(km) - String
     */
    fun getDistance(x: Double, y: Double) : String {
        val a = 2 * asin(sqrt(sin(Math.toRadians(x - this.latX) / 2).pow(2.0)
                + sin(Math.toRadians(y - this.lngY) / 2).pow(2.0) * cos(Math.toRadians(this.latX)) * cos(Math.toRadians(x))))
        Log.e("[거리계산]", "${(R * a) / 1000}")
        Log.e("[거리결과]", String.format("%.1fkm", (R * a) / 1000))
        return String.format("%.1fkm", (R * a) / 1000)
    }

    //GPS로부터 위치정보를 얻어오는 함수
    fun getLocation(context: Context): Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // LocationManager.GPS_PROVIDER 또는 LocationManager.NETWORK_PROVIDER 를 얻어온다.
        val provider = locationManager.getBestProvider(Criteria(), true)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "위치정보를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
            return null
        }
        if (provider == null) {
            Toast.makeText(context, "위치 정보를 사용할 수 있는 상태가 아닙니다, GPS를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return null
        }
        // 해당 장치가 마지막으로 수신한 위치 얻기
        val location = locationManager.getLastKnownLocation(provider)
        return location
    }

    //위도, 경도로부터 주소를 계산하는 함수
    fun getAddress(lat: Double, lng: Double, context: Context): String? {
        val geocoder = Geocoder(context, Locale.getDefault()) //주소 구하기 객체
        val address : String = geocoder.getFromLocation(lat, lng, 1)[0].getAddressLine(0) // 현재 주소
        Log.e("[address]", address)
        return address.slice(11 until address.length)
    }

}