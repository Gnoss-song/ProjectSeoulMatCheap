package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.arrayMapOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.*
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMap01Binding
import kr.co.mapo.project_seoulmatcheap.databinding.MapItemInfowindowBinding
import kr.co.mapo.project_seoulmatcheap.system.*
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.MAP_01_01
import java.io.Serializable
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
    private lateinit var storeList : List<StoreEntity>
    private lateinit var list2 : MutableList<StoreEntity>
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
    private val clickedInfowindow = arrayMapOf<InfoWindow, StoreEntity>()

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
        AppDatabase(owner)!!.storeDAO().getAllStore().observe(viewLifecycleOwner, {
            storeList = it
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SeoulMatCheap.getInstance().address.observe(viewLifecycleOwner, Observer {
            binding.toolbar.title = it
        })
        binding.include.storeBottomLayout.setOnClickListener {
            startActivity(Intent(owner, INFORM_02::class.java))
        }
//        list2 = list.apply {
//            forEach{
//                it.distance = SeoulMatCheap.getInstance().calculateDistanceDou(it.x, it.y)
//            }
//        }
//        list2.sortBy { it.distance }
    }

    //네이버지도 초기 설정
    override fun onMapReady(p0: NaverMap) {
        val locationSource = FusedLocationSource(this, 100)
        naverMap = p0
        Log.e("[데이터 로딩 테스트]", "리스트 사이즈 : ${storeList.size}")
        naverMap.apply {
            val locationObserver = Observer<Location> {
                cameraPosition = setMapCamera(it.latitude, it.longitude, MAP_ZOOM)
                with(locationOverlay) {
                    isVisible = true
                    position = LatLng(it.latitude, it.longitude)
                    icon = MapHelper.map_marker
                    iconHeight = NOW_ICON_SIZE
                    iconWidth = NOW_ICON_SIZE
                }
            }
            SeoulMatCheap.getInstance().location.observe(viewLifecycleOwner, locationObserver)
            minZoom = MAP_MIN_ZOOM
            setLocationSource(locationSource)
            locationTrackingMode = LocationTrackingMode.Face
            addOnCameraChangeListener { reason, _ ->
                /*
                REASON_DEVELOPER: 개발자가 API를 호출해 카메라가 움직였음을 나타냅니다. 기본값입니다.
                REASON_GESTURE: 사용자의 제스처로 인해 카메라가 움직였음을 나타냅니다.
                REASON_CONTROL: 사용자의 버튼 선택으로 인해 카메라가 움직였음을 나타냅니다.
                REASON_LOCATION: 위치 트래킹 기능으로 인해 카메라가 움직였음을 나타냅니다.
                 */
                Log.e("[REASON]", "$reason")
                if(reason != CameraUpdate.REASON_DEVELOPER) {
                    SeoulMatCheap.getInstance().location.removeObserver(locationObserver)
                }
            }
            setOnMapClickListener { _, _ ->
                initiateOverlay()
            }
        }
        storeList.forEach {
            val marker = createMaker(it, naverMap)
            createInfoWindow (it, marker)
        }
    }

    //지도 카메라 설정 함수
    fun setMapCamera(lat:Double, lng:Double, zoom : Double) : CameraPosition {
        return CameraPosition(LatLng(lat, lng), zoom)
    }

    //마커생성함수
    private fun createMaker(item: StoreEntity, naverMap: NaverMap) : Marker {
        return Marker().apply {
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
                Log.e("[$this]", "${clickedInfowindow.size}, $tag")
                onOverlayClick(this, item)
                true
            }
            open(marker)
        }
    }

    //인포윈도우 어댑터 생성 함수
    fun createInfoWindowAdapter(item: StoreEntity,  clicked : Boolean, view:MapItemInfowindowBinding) : InfoWindow.ViewAdapter {
        return object : InfoWindow.ViewAdapter() {
            override fun getView(p0: InfoWindow): View {
                var color : ColorStateList = MapHelper.seoulColor
                //찜 여부도 고려하는 연산 추가
                GlobalScope.launch(Dispatchers.IO) {
                    val favorited = AppDatabase(owner)!!.storeDAO().isFavorite(item.id)
                    if(favorited.isNotEmpty()) color = MapHelper.likeColor
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

    //서클원 생성함수
    fun createCircle(lat:Double, lng:Double, m:Double, naverMap: NaverMap) : CircleOverlay {
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

    //식당정보 바텀시트 데이터 설정함수
    fun setBottomSheetData(item : StoreEntity) {
        binding.include.item = item
        with(binding.include) {
            Glide.with(owner).load(item.photo).into(image)
            distance.text = SeoulMatCheap.getInstance().calculateDistance(item.lat, item.lng)
            score.text = "0.0"
        }
        storeWindowBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun mapButtonClick(v : View) {
        when(v.id) {
            R.id.button_list -> {
                val intent = Intent(owner, MAP_01_01::class.java)
                intent.putExtra(ADDRESS, SeoulMatCheap.getInstance().address.value)
                intent.putExtra(LIST, list2 as Serializable)
                startActivity(intent)
            }
            R.id.button_filter -> {
                filterDialog.filterSort = this.filterSort
                filterDialog.show(owner.supportFragmentManager, FILTER)
            }
            R.id.button_gps -> {
                naverMap.apply {
                    cameraPosition = setMapCamera(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y, MAP_ZOOM)
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

    override fun onStop() {
        super.onStop()
        initiateOverlay()
    }

}