package kr.co.mapo.project_seoulmatcheap.ui.custom

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.system.*
import kr.co.mapo.project_seoulmatcheap.ui.fragment.StoreTest
import java.util.*

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-14
 * @desc
 */
class MapViewModel {

    //지도 관리 라이브데이터
    val markersHansik = MutableLiveData<Vector<Marker>>()
    val markersJapan = MutableLiveData<Vector<Marker>>()
    val markersChina = MutableLiveData<Vector<Marker>>()
    val markersFood = MutableLiveData<Vector<Marker>>()
    val markersWash = MutableLiveData<Vector<Marker>>()
    val markersBeauty = MutableLiveData<Vector<Marker>>()
    val markersHotel = MutableLiveData<Vector<Marker>>()
    val markersStore = MutableLiveData<Vector<Marker>>()

    //오버레이 이미지 전역변수
    private val icon_hansik = OverlayImage.fromResource(R.drawable.icon_hansik)
    private val icon_china = OverlayImage.fromResource(R.drawable.icon_china)
    private val icon_japan = OverlayImage.fromResource(R.drawable.icon_japan)
    private val icon_food = OverlayImage.fromResource(R.drawable.icon_food)
    private val icon_beauty = OverlayImage.fromResource(R.drawable.icon_beauty)
    private val icon_wash = OverlayImage.fromResource(R.drawable.icon_wash)
    private val icon_hotel = OverlayImage.fromResource(R.drawable.icon_hotel)
    private val icon_store = OverlayImage.fromResource(R.drawable.icon_store)

    fun createMarker(item: StoreTest, naverMap : NaverMap) : Marker {
        return Marker().apply {
            position = LatLng(item.x, item.y)
            map = naverMap
            width = 120
            height = 120
            isForceShowIcon = true
            when(item.sort) {
                SORT_HANSIK -> {
                    icon = icon_hansik
                    markersHansik.value?.add(this)
                }
                SORT_CHINA -> {
                    icon = icon_china
                    markersChina.value?.add(this)
                }
                SORT_JAPAN -> {
                    icon = icon_japan
                    markersJapan.value?.add(this)
                }
                SORT_FOOD -> {
                    icon = icon_food
                    markersFood.value?.add(this)
                }
                SORT_BEAUTY -> {
                    icon = icon_beauty
                    markersBeauty.value?.add(this)
                }
                SORT_WASH -> {
                    icon = icon_wash
                    markersWash.value?.add(this)
                }
                SORT_HOTEL -> {
                    icon = icon_hotel
                    markersHotel.value?.add(this)
                }
                else -> {
                    icon = icon_store
                    markersStore.value?.add(this)
                }
            }
        }
    }
}