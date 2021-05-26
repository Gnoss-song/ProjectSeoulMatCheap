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
import androidx.databinding.ObservableMap
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentCategory0102Binding
import kr.co.mapo.project_seoulmatcheap.databinding.MapItemInfowindowBinding
import kr.co.mapo.project_seoulmatcheap.system.*
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02
import java.util.*

class CATEGORY_01_02(val owner : AppCompatActivity,
    val key : String?): Fragment(), OnMapReadyCallback {

    private lateinit var binding : FragmentCategory0102Binding
    private lateinit var view: MapItemInfowindowBinding
    private lateinit var storeWindowBehavior : BottomSheetBehavior<LinearLayout>
    private lateinit var filterDialog : CATEGORY_01_02_01

    private lateinit var list : List<StoreEntity>
    private lateinit var naverMap : NaverMap

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

    //필터 상태 저장
    private var filterSort = mutableSetOf<String>()

    //오버레이 이벤트 상태 저장
    private val clickedInfowindow = arrayMapOf<InfoWindow, StoreEntity>()
    private var favoritList = MutableLiveData<List<Int>>()
    private var sortedList = listOf<StoreEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategory0102Binding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        AppDatabase(owner)!!.storeDAO().getFavoriteIdList().observe(viewLifecycleOwner, {
            favoritList.value = it
        })
        list = SeoulMatCheap.getInstance().storeList
        Log.e("[카테고리 지도]", "${list.size}")
        view = MapItemInfowindowBinding.inflate(layoutInflater)
        storeWindowBehavior = BottomSheetBehavior.from(binding.include.storeBottomLayout)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
        filterDialog = CATEGORY_01_02_01(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonFilter2.setOnClickListener {
                filterDialog.filterSort = filterSort
                filterDialog.show(owner.supportFragmentManager, FILTER)
            }
            buttonGps2.setOnClickListener {
                naverMap.apply {
                    cameraPosition = setMapCamera(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y, MAP_ZOOM)
                }
            }
        }
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        val x = MapHelper.getLat(key)
        val y = MapHelper.getLng(key)
        Log.e("[확인]", "$key")
        Log.e("[확인]", "$x . $y")
        naverMap.apply {
            cameraPosition = setMapCamera(x, y, 14.0)
            minZoom = MAP_MIN_ZOOM
            with(locationOverlay) {
                isVisible = true
                position = LatLng(x, y)
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
        createCircle(x, y, 5000.0, naverMap)
    }

    //지도 카메라 설정 함수
    private fun setMapCamera(lat:Double, lng:Double, zoom : Double) : CameraPosition {
        return CameraPosition(LatLng(lat, lng), zoom)
    }

    //마커생성함수
    private fun createMaker(item: StoreEntity, naverMap: NaverMap) : Marker {
        return Marker().apply {
            Log.e("[TEST]", "$item")
            position = LatLng(item.lat, item.lng)
            map = naverMap
            width = MARKER_SIZE
            height = MARKER_SIZE
            isForceShowIcon = true
            minZoom = MARKET_MIN_ZOOM
            when(item.sort) {
                0 -> {
                    icon = MapHelper.icon_hansik
                    markersHansik.add(this)
                }
                1 -> {
                    icon = MapHelper.icon_china
                    markersChina.add(this)
                }
                2 -> {
                    icon = MapHelper.icon_japan
                    markersJapan.add(this)
                }
                3 -> {
                    icon = MapHelper.icon_food
                    markersFood.add(this)
                }
                4 -> {
                    icon = MapHelper.icon_beauty
                    markersBeauty.add(this)
                }
                5 -> {
                    icon = MapHelper.icon_wash
                    markersWash.add(this)
                }
                6 -> {
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
    private fun createInfoWindow(item: StoreEntity, marker: Marker) : InfoWindow {
        return InfoWindow().apply {
            tag = false
            adapter = createInfoWindowAdapter(item, tag as Boolean, view)
            when(item.sort) {
                0 -> infoWindowsHansik.add(this)
                1 -> infoWindowsChina.add(this)
                2 -> infoWindowsJapan.add(this)
                3 -> infoWindowsFood.add(this)
                4 -> infoWindowsBeauty.add(this)
                5 -> infoWindowsWash.add(this)
                6 -> infoWindowsHotel.add(this)
                else -> infoWindowsStore.add(this)
            }
            setOnClickListener {
                onOverlayClick(this, item)
                true
            }
            favoritList.observe(viewLifecycleOwner, {
                if(it.contains(item.id)) {
                    item.liked = true
                    adapter = createInfoWindowAdapter(item, tag as Boolean, view)
                } else {
                    item.liked = false
                    adapter = createInfoWindowAdapter(item, tag as Boolean, view)
                }
            })
            open(marker)
        }
    }

    //인포윈도우 어댑터 생성 함수
    private fun createInfoWindowAdapter(item: StoreEntity,  clicked : Boolean, view:MapItemInfowindowBinding) : InfoWindow.ViewAdapter {
        return object : InfoWindow.ViewAdapter() {
            override fun getView(p0: InfoWindow): View {
                var color : ColorStateList = if(item.liked) MapHelper.likeColor else MapHelper.seoulColor
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
                        viewContent1.backgroundTintList = color
                        viewContent2.backgroundTintList = color
                        viewBottom1.imageTintList = color
                        viewBottom2.imageTintList = color
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

    //오버레이 클릭 이벤트 처리 함수
    private fun onOverlayClick(infoWindow: InfoWindow, item: StoreEntity) {
        with(infoWindow){
            if(clickedInfowindow.isNotEmpty() &&
                clickedInfowindow.keyAt(0) !== this) {
                val infoWindow = clickedInfowindow.keyAt(0)
                with(infoWindow) {
                    adapter = createInfoWindowAdapter(clickedInfowindow[infoWindow]!!, false, view)
                    tag = false
                }
                clickedInfowindow.clear()
            }
            if(this?.tag == false) {  //클릭이 안된 상태에서 클릭
                this.adapter = createInfoWindowAdapter(item, true, view)
                setBottomSheetData(item)
                clickedInfowindow[this] = item
                tag = true  //다음 이벤트 처리를 위해 다시 tag boolean값을 클릭된 상태로 초기화한다.
            } else {    //클릭된 상태에서 클릭
                setBottomSheetData(item)
                storeWindowBehavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
                this!!.adapter = createInfoWindowAdapter(item, false, view)
                clickedInfowindow.clear()
                tag = false //다음 이벤트 처리를 위해 다시 tag boolean값을 클릭이 안된 상태로 초기화한다.
            }
        }
    }

    //서클원 생성함수
    private fun createCircle(lat:Double, lng:Double, m:Double, naverMap: NaverMap) : CircleOverlay {
        return CircleOverlay().apply {
            tag = m
            center = LatLng(lat, lng)
            radius = m
            color = MapHelper.circleColor
            map = naverMap
            circleOverlay.add(this)
        }
    }

    //식당정보 바텀시트 데이터 설정함수
    private fun setBottomSheetData(item : StoreEntity) {
        binding.include.item = item
        with(binding.include) {
            storeBottomLayout.setOnClickListener {
                val intent = Intent(owner, INFORM_02::class.java)
                intent.putExtra(STORE, item)
                startActivity(intent)
            }
            Glide.with(owner).load(item.photo).into(image)
            distance.text = SeoulMatCheap.getInstance().calculateDistance(item.lat, item.lng)
            score.text = item.score.toString()
        }
        storeWindowBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    //오버레이 클릭상태 초기화
    fun initiateOverlay() {
        if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            if(clickedInfowindow.isNotEmpty()) {
                val infoWindow = clickedInfowindow.keyAt(0)
                with(infoWindow) {
                    adapter = createInfoWindowAdapter(clickedInfowindow[infoWindow]!!, false, view)
                    tag = false
                }
                clickedInfowindow.clear()
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
        createCircle(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y, distance, naverMap)
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
    private fun reappearMarkers(markers : Vector<Marker>, infowindows: Vector<InfoWindow>) {
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

}