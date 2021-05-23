package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.arrayMapOf
import androidx.databinding.ObservableMap
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentCategory0102Binding
import kr.co.mapo.project_seoulmatcheap.databinding.MapItemInfowindowBinding
import kr.co.mapo.project_seoulmatcheap.system.*
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02

private const val GANGNAM_LAT = 37.4968436
private const val GANGNAM_LNG = 127.0329199

class CATEGORY_01_02(val owner : AppCompatActivity): Fragment(), OnMapReadyCallback {

    private lateinit var binding : FragmentCategory0102Binding
    private lateinit var map01: MAP_01
    private lateinit var view: MapItemInfowindowBinding
    private lateinit var storeWindowBehavior : BottomSheetBehavior<LinearLayout>

    private lateinit var list : MutableList<StoreTest>
    private lateinit var naverMap : NaverMap

    //오버레이 이벤트 상태 저장
    private val clickedInfowindow = arrayMapOf<InfoWindow, StoreTest>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategory0102Binding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        map01 = MAP_01(owner)
        view = MapItemInfowindowBinding.inflate(layoutInflater)
        storeWindowBehavior = BottomSheetBehavior.from(binding.include.storeBottomLayout)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        list = Test().addData900()
        mapFragment.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.include.storeBottomLayout.setOnClickListener {
            startActivity(Intent(owner, INFORM_02::class.java))
        }
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        Log.e("[데이터 로딩 테스트]", "리스트 사이즈 : ${list.size}")
        naverMap.apply {
            cameraPosition = map01.setMapCamera(GANGNAM_LAT, GANGNAM_LNG, 16.0)
            minZoom = MAP_MIN_ZOOM
            with(locationOverlay) {
                isVisible = true
                position = LatLng(GANGNAM_LAT, GANGNAM_LNG)
                icon = MapHelper.map_marker
                iconHeight = NOW_ICON_SIZE
                iconWidth = NOW_ICON_SIZE
            }
            setOnMapClickListener { _, _ ->
                initiateOverlay()
            }
        }
        list.forEach {
            val marker = createMaker(it, naverMap)
            createInfoWindow (it, marker)
        }
        map01.createCircle(GANGNAM_LAT, GANGNAM_LNG, 3000.0, naverMap)
    }

    //마커생성함수
    private fun createMaker(item: StoreTest, naverMap: NaverMap) : Marker {
        return Marker().apply {
            position = LatLng(item.x, item.y)
            map = naverMap
            width = MARKER_SIZE
            height = MARKER_SIZE
            isForceShowIcon = true
            when(item.sort) {
                SORT_HANSIK -> {
                    icon = MapHelper.icon_hansik
                }
                SORT_CHINA -> {
                    icon = MapHelper.icon_china
                }
                SORT_JAPAN -> {
                    icon = MapHelper.icon_japan
                }
                SORT_FOOD -> {
                    icon = MapHelper.icon_food
                }
                SORT_BEAUTY -> {
                    icon = MapHelper.icon_beauty
                }
                SORT_WASH -> {
                    icon = MapHelper.icon_wash
                }
                SORT_HOTEL -> {
                    icon = MapHelper.icon_hotel
                }
                else -> {
                    icon = MapHelper.icon_store
                }
            }
            setOnClickListener {
                this.infoWindow?.let { it1 -> onOverlayClick(it1, item) }
                return@setOnClickListener true
            }
        }
    }

    //인포윈도우 생성함수
    private fun createInfoWindow(item: StoreTest, marker: Marker) : InfoWindow {
        return InfoWindow().apply {
            tag = false
            adapter = map01.createInfoWindowAdapter(item, tag as Boolean, view)
            setOnClickListener {
                Log.e("[$this]", "${clickedInfowindow.size}, $tag")
                onOverlayClick(this, item)
                true
            }
            open(marker)
        }
    }

    //오버레이 클릭 이벤트 처리 함수
    private fun onOverlayClick(infoWindow: InfoWindow, item: StoreTest) {
        with(infoWindow){
            if(clickedInfowindow.isNotEmpty() &&
                clickedInfowindow.keyAt(0) !== this) {
                val infoWindow = clickedInfowindow.keyAt(0)
                with(infoWindow) {
                    adapter = map01.createInfoWindowAdapter(clickedInfowindow[infoWindow]!!, false, view)
                    tag = false
                }
                clickedInfowindow.clear()
            }
            if(this?.tag == false) {  //클릭이 안된 상태에서 클릭
                this.adapter = map01.createInfoWindowAdapter(item, true, view)
                map01.setBottomSheetData(item)
                clickedInfowindow[this] = item
                tag = true  //다음 이벤트 처리를 위해 다시 tag boolean값을 클릭된 상태로 초기화한다.
            } else {    //클릭된 상태에서 클릭
                map01.setBottomSheetData(item)
                storeWindowBehavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
                this!!.adapter = map01.createInfoWindowAdapter(item, false, view)
                clickedInfowindow.clear()
                tag = false //다음 이벤트 처리를 위해 다시 tag boolean값을 클릭이 안된 상태로 초기화한다.
            }
        }
    }

    //오버레이 클릭상태 초기화
    private fun initiateOverlay() {
        if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            if(clickedInfowindow.isNotEmpty()) {
                val infoWindow = clickedInfowindow.keyAt(0)
                with(infoWindow) {
                    adapter = map01.createInfoWindowAdapter(clickedInfowindow[infoWindow]!!, false, view)
                    tag = false
                }
                clickedInfowindow.clear()
            }
        }
    }

}