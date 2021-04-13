package kr.co.mapo.project_seoulmatcheap.system

import android.app.Application
import android.util.Log
import android.widget.Toast
import kotlin.math.*

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-06
 * @desc 어플리케이션 클래스(싱글톤)-공통변수, 공통함수, 로케이션 설정
 */

private const val R = 6372.8 * 1000

class Application : Application() {

    var latX : Double = 0.0      //현재 위치 위도
    var lngY : Double = 0.0      //현재 위치 경도
    var adress : String = ""     //현재 위치 주소

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

}