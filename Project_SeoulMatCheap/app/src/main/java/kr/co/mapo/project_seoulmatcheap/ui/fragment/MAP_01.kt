package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.*
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMap01Binding
import kr.co.mapo.project_seoulmatcheap.databinding.MapItemInfowindowBinding
import kr.co.mapo.project_seoulmatcheap.system.*
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.MAP_01_01
import java.util.*

class MAP_01(val owner : AppCompatActivity) : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(owner: AppCompatActivity) : Fragment {
            return MAP_01(owner)
        }
    }

    private lateinit var binding : FragmentMap01Binding
    private lateinit var naverMap : NaverMap
    private lateinit var storeWindowBehavior : BottomSheetBehavior<LinearLayout>
    private lateinit var list : MutableList<StoreTest>
    private lateinit var filterDialog : MAP_01_02

    //infoWindow
    private lateinit var view: MapItemInfowindowBinding

    //오버레이 관리를 위한 저장 Vector
    private val markersHansik = Vector<Marker>()
    private val infoWindowsHansik = Vector<InfoWindow>()
    private val markersJapan = Vector<Marker>()
    private val infoWindowsJapan = Vector<InfoWindow>()
    private val markersChina = Vector<Marker>()
    private val infoWindowsChina = Vector<InfoWindow>()
    private val markersFood = Vector<Marker>()
    private val infoWindowsFood = Vector<InfoWindow>()
    private val markersWash = Vector<Marker>()
    private val infoWindowsWash = Vector<InfoWindow>()
    private val markersBeauty = Vector<Marker>()
    private val infoWindowsBeauty = Vector<InfoWindow>()
    private val markersHotel = Vector<Marker>()
    private val infoWindowsHotel = Vector<InfoWindow>()
    private val markersStore = Vector<Marker>()
    private val infoWindowsStore = Vector<InfoWindow>()

    private val circleOverlay = mutableSetOf<CircleOverlay>()

    //오버레이 이벤트 상태 저장
    private val clickedInfowindow = arrayMapOf<InfoWindow, StoreTest>()

    //필터 상태 저장
    private var filterSort = mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_01, container,false)
        init()
        return binding.root
    }

    private fun init() {
        view = MapItemInfowindowBinding.inflate(layoutInflater)
        binding.fragment = this
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
        storeWindowBehavior = BottomSheetBehavior.from(binding.include.storeBottomLayout)
        filterDialog = MAP_01_02(this)
        list = Test().addData100()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SeoulMatCheap.getInstance().address.observe(viewLifecycleOwner, Observer {
            binding.toolbar.title = it
        })
    }

    //네이버지도 초기 설정
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        Log.e("[데이터 로딩 테스트]", "리스트 사이즈 : ${list.size}")
        naverMap.apply {
            val locationObserver = Observer<Location> {
                cameraPosition = setMapCamera(it.latitude, it.longitude)
                with(locationOverlay) {
                    isVisible = true
                    position = LatLng(it.latitude, it.longitude)
                    icon = MapHelper.map_marker
                    iconHeight = NOW_ICON_SIZE
                    iconWidth = NOW_ICON_SIZE
                }
            }
            SeoulMatCheap.getInstance().location.observe(viewLifecycleOwner, locationObserver)
            addOnCameraChangeListener { reason, _ ->
                if(reason != CameraUpdate.REASON_DEVELOPER) {
                    SeoulMatCheap.getInstance().location.removeObserver(locationObserver)
                }
            }
            minZoom = MAP_MIN_ZOOM
            setOnMapClickListener { _, _ ->
                initiateOverlay()
            }
            list.forEach {
                val marker = createMaker(it)
                createInfoWindow (it, marker)
            }
        }
    }

    //지도 카메라 설정 함수
    fun setMapCamera(lat:Double, lng:Double) : CameraPosition {
        return CameraPosition(LatLng(lat, lng), MAP_ZOOM)
    }

    //마커생성함수
    fun createMaker(item: StoreTest) : Marker {
        return Marker().apply {
            position = LatLng(item.x, item.y)
            map = naverMap
            width = MARKER_SIZE
            height = MARKER_SIZE
            isForceShowIcon = true
            minZoom = MAP_MIN_ZOOM
            when(item.sort) {
                SORT_HANSIK -> {
                    icon = MapHelper.icon_hansik
                    markersHansik.add(this)
                }
                SORT_CHINA -> {
                    icon = MapHelper.icon_china
                    markersChina.add(this)
                }
                SORT_JAPAN -> {
                    icon = MapHelper.icon_japan
                    markersJapan.add(this)
                }
                SORT_FOOD -> {
                    icon = MapHelper.icon_food
                    markersFood.add(this)
                }
                SORT_BEAUTY -> {
                    icon = MapHelper.icon_beauty
                    markersBeauty.add(this)
                }
                SORT_WASH -> {
                    icon = MapHelper.icon_wash
                    markersWash.add(this)
                }
                SORT_HOTEL -> {
                    icon = MapHelper.icon_hotel
                    markersHotel.add(this)
                }
                else -> {
                    icon = MapHelper.icon_store
                    markersStore.add(this)
                }
            }
            setOnClickListener {
                this.infoWindow?.let { it1 -> onOverlayClick(it1, item) }
                return@setOnClickListener true
            }
        }
    }

    //인포윈도우 생성함수
    fun createInfoWindow(item: StoreTest, marker: Marker) : InfoWindow {
        return InfoWindow().apply {
            tag = false
            adapter = createInfoWindowAdapter(item, tag as Boolean)
            when(item.sort) {
                SORT_HANSIK -> infoWindowsHansik.add(this)
                SORT_CHINA -> infoWindowsChina.add(this)
                SORT_JAPAN -> infoWindowsJapan.add(this)
                SORT_FOOD -> infoWindowsFood.add(this)
                SORT_BEAUTY -> infoWindowsBeauty.add(this)
                SORT_WASH -> infoWindowsWash.add(this)
                SORT_HOTEL -> infoWindowsHotel.add(this)
                else -> infoWindowsStore.add(this)
            }
            setOnClickListener {
                Log.e("[$this]", "${clickedInfowindow.size}, $tag")
                onOverlayClick(this, item)
                true
            }
            open(marker)
        }
    }

    //인포윈도우 어댑터 생성 함수
    fun createInfoWindowAdapter(item: StoreTest,  clicked : Boolean) : InfoWindow.ViewAdapter {
        return object : InfoWindow.ViewAdapter() {
            override fun getView(p0: InfoWindow): View {
                val color = when(item.code) {
                    0 -> MapHelper.seoulColor  //(임시)착한업소
                    1 -> MapHelper.matColor    //(임시)인증맛칩
                    else -> MapHelper.likeColor   //(임시) 찜
                }
                with(view) {
                    textName.text = item.name
                    if(!clicked) {
                        viewContent1.backgroundTintList = color
                        viewContent2.backgroundTintList = MapHelper.unClickedColor
                        viewBottom1.imageTintList = color
                        viewBottom2.imageTintList = MapHelper.unClickedColor
                        textName.apply {
                            typeface = null
                            setTextColor(MapHelper.blackColor)
                        }
                    } else {
                        viewContent1.backgroundTintList = MapHelper.clickedColor
                        viewContent2.backgroundTintList = MapHelper.clickedColor
                        viewBottom1.imageTintList = MapHelper.clickedColor
                        viewBottom2.imageTintList = MapHelper.clickedColor
                        textName.apply {
                            typeface = Typeface.DEFAULT_BOLD
                            setTextColor(MapHelper.whiteColor)
                        }
                    }
                    return view.root
                }
            }
        }
    }

    //서클원 생성함수
    fun createCircle(lat:Double, lng:Double, m:Double) : CircleOverlay {
        return CircleOverlay().apply {
            tag = m
            center = LatLng(lat, lng)
            radius = m
            color = MapHelper.circleColor
            map = naverMap
            circleOverlay.add(this)
        }
    }

    //오버레이 클릭 이벤트 처리 함수
    fun onOverlayClick(infoWindow: InfoWindow, item: StoreTest) {
        with(infoWindow){
            if(clickedInfowindow.isNotEmpty() &&
                clickedInfowindow.keyAt(0) !== this) {
                val infoWindow = clickedInfowindow.keyAt(0)
                with(infoWindow) {
                    adapter = createInfoWindowAdapter(clickedInfowindow[infoWindow]!!, false)
                    tag = false
                }
                clickedInfowindow.clear()
            }
            if(this?.tag == false) {  //클릭이 안된 상태에서 클릭
                this.adapter = createInfoWindowAdapter(item, true)
                setBottomSheetData(item)
                clickedInfowindow[this] = item
                tag = true  //다음 이벤트 처리를 위해 다시 tag boolean값을 클릭된 상태로 초기화한다.
            } else {    //클릭된 상태에서 클릭
                setBottomSheetData(item)
                storeWindowBehavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
                this!!.adapter = createInfoWindowAdapter(item, false)
                clickedInfowindow.clear()
                tag = false //다음 이벤트 처리를 위해 다시 tag boolean값을 클릭이 안된 상태로 초기화한다.
            }
        }
    }

    //식당정보 바텀시트 데이터 설정함수
    fun setBottomSheetData(item : StoreTest) {
        binding.include.item = item
        with(binding.include) {
            Glide.with(owner).load(item.image).into(image)
            distance.text = "${item.distance} km"
            score.text = item.score.toString()
        }
        storeWindowBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun mapButtonClick(v : View) {
        when(v.id) {
            R.id.button_list -> {
                val intent = Intent(owner, MAP_01_01::class.java)
                intent.putExtra(ADDRESS, SeoulMatCheap.getInstance().address.value)
                startActivity(intent)
            }
            R.id.button_filter -> {
                filterDialog.filterSort = this.filterSort
                filterDialog.show(owner.supportFragmentManager, FILTER)
            }
            R.id.button_gps -> {
                naverMap.apply {
                    cameraPosition = setMapCamera(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y)
                }
            }
        }
    }

    //필터 기능 처리함수 - 거리필터
    fun filterDistance(distance: Double) {
        if(circleOverlay.size > 0) {
            circleOverlay.forEach {
                it.map = null
            }
        }
        createCircle(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y, distance)
    }

    //필터 기능 처리함수 - 업종필터
    fun filterSort(filterSort : MutableSet<String>) {
        this.filterSort = filterSort
        Log.e("[필터 목록-맵]", filterSort.toString())
        markersHansik.forEach { it.map = null }
        markersChina.forEach { it.map = null }
        markersJapan.forEach { it.map = null }
        markersFood.forEach { it.map = null }
        markersWash.forEach { it.map = null }
        markersBeauty.forEach { it.map = null }
        markersHotel.forEach { it.map = null }
        markersStore.forEach { it.map = null }
        if(filterSort.contains(SORT_HANSIK)) reappearMarkers(markersHansik, infoWindowsHansik)
        if(filterSort.contains(SORT_CHINA)) reappearMarkers(markersChina, infoWindowsChina)
        if(filterSort.contains(SORT_JAPAN)) reappearMarkers(markersJapan, infoWindowsJapan)
        if(filterSort.contains(SORT_FOOD)) reappearMarkers(markersFood, infoWindowsFood)
        if(filterSort.contains(SORT_WASH)) reappearMarkers(markersWash, infoWindowsWash)
        if(filterSort.contains(SORT_BEAUTY)) reappearMarkers(markersBeauty, infoWindowsBeauty)
        if(filterSort.contains(SORT_HOTEL)) reappearMarkers(markersHotel, infoWindowsHotel)
        if(filterSort.contains(SORT_STORE)) reappearMarkers(markersStore, infoWindowsStore)
    }

    //마커 다시찍을 때
    fun reappearMarkers(markers : Vector<Marker>, infowindows: Vector<InfoWindow>) {
        for(i in 0 until markers.size) {
            markers[i].map = naverMap
            infowindows[i].open(markers[i])
        }
    }

    //필터 기능 처리함수 - 맵 초기화
    fun filterInitialize() {
        filterDialog.dismiss()
        filterSort.clear()
        if(circleOverlay.isNotEmpty()) {
            circleOverlay.forEach {
                it.map = null
            }
        }
        reappearMarkers(markersHansik, infoWindowsHansik)
        reappearMarkers(markersChina, infoWindowsChina)
        reappearMarkers(markersJapan, infoWindowsJapan)
        reappearMarkers(markersFood, infoWindowsFood)
        reappearMarkers(markersWash, infoWindowsWash)
        reappearMarkers(markersBeauty, infoWindowsBeauty)
        reappearMarkers(markersHotel, infoWindowsHotel)
        reappearMarkers(markersStore, infoWindowsStore)
    }

    //오버레이 클릭상태 초기화
    fun initiateOverlay() {
        if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            if(clickedInfowindow.isNotEmpty()) {
                val infoWindow = clickedInfowindow.keyAt(0)
                with(infoWindow) {
                    adapter = createInfoWindowAdapter(clickedInfowindow[infoWindow]!!, false)
                    tag = false
                }
                clickedInfowindow.clear()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        initiateOverlay()
    }
}