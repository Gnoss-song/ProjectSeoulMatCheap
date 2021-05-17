package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.MAP_01_01
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

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
    private val markersJapan = Vector<Marker>()
    private val markersChina = Vector<Marker>()
    private val markersFood = Vector<Marker>()
    private val markersWash = Vector<Marker>()
    private val markersBeauty = Vector<Marker>()
    private val markersHotel = Vector<Marker>()
    private val markersStore = Vector<Marker>()
    private val circleOverlay = Vector<CircleOverlay>()
    private val infoWindows = Vector<InfoWindow>()

    //오버레이 속성 전역변수
    private lateinit var unClickedAdapter : InfoWindow.ViewAdapter
    private lateinit var clickedAdapter : InfoWindow.ViewAdapter

    val map_marker = OverlayImage.fromResource(R.drawable.map_marker)

    val blackColor = owner.resources.getColor(R.color.black, null)
    val whiteColor = owner.resources.getColor(R.color.white, null)
    val clickedColor = ColorStateList.valueOf(owner.resources.getColor(R.color.black, null))
    val unClickedColor = ColorStateList.valueOf(owner.resources.getColor(R.color.white, null))
    val seoulColor = ColorStateList.valueOf(owner.resources.getColor(R.color.map_seoul, null))
    val matColor = ColorStateList.valueOf(owner.resources.getColor(R.color.map_mat, null))
    val likeColor = ColorStateList.valueOf(owner.resources.getColor(R.color.map_like, null))
    val circleColor = owner.resources.getColor(R.color.map_circle, null)

    val icon_hansik = OverlayImage.fromResource(R.drawable.icon_hansik)
    val icon_china = OverlayImage.fromResource(R.drawable.icon_china)
    val icon_japan = OverlayImage.fromResource(R.drawable.icon_japan)
    val icon_food = OverlayImage.fromResource(R.drawable.icon_food)
    val icon_beauty = OverlayImage.fromResource(R.drawable.icon_beauty)
    val icon_wash = OverlayImage.fromResource(R.drawable.icon_wash)
    val icon_hotel = OverlayImage.fromResource(R.drawable.icon_hotel)
    val icon_store = OverlayImage.fromResource(R.drawable.icon_store)

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
        list = Test().addData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SeoulMatCheap.getInstance().address.observe(viewLifecycleOwner, Observer {
            binding.toolbar.title = it
        })
        binding.include.storeBottomLayout.setOnClickListener {
            owner.startActivity(Intent(owner, INFORM_02::class.java))
        }
    }

    //네이버지도 초기 설정
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        naverMap.apply {
            val locationObserver = Observer<Location> {
                cameraPosition = setMapCamera(it.latitude, it.longitude)
                with(locationOverlay) {
                    isVisible = true
                    position = LatLng(it.latitude, it.longitude)
                    icon = map_marker
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
            setOnMapClickListener { _, _ ->
                if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
        list.forEach {
            val marker = createMaker(it)
            createInfoWindow (it, marker)
        }
    }

    //지도카메라위치
    private fun setMapCamera(lat:Double, lng:Double) : CameraPosition {
        return CameraPosition(LatLng(lat, lng), MAP_ZOOM)
    }

    //마커생성함수
    private fun createMaker(item: StoreTest) : Marker {
        return Marker().apply {
            position = LatLng(item.x, item.y)
            map = naverMap
            width = MARKER_SIZE
            height = MARKER_SIZE
            isForceShowIcon = true
            when(item.sort) {
                SORT_HANSIK -> {
                    icon = icon_hansik
                    markersHansik.add(this)
                }
                SORT_CHINA -> {
                    icon = icon_china
                    markersChina.add(this)
                }
                SORT_JAPAN -> {
                    icon = icon_japan
                    markersJapan.add(this)
                }
                SORT_FOOD -> {
                    icon = icon_food
                    markersFood.add(this)
                }
                SORT_BEAUTY -> {
                    icon = icon_beauty
                    markersBeauty.add(this)
                }
                SORT_WASH -> {
                    icon = icon_wash
                    markersWash.add(this)
                }
                SORT_HOTEL -> {
                    icon = icon_hotel
                    markersHotel.add(this)
                }
                else -> {
                    icon = icon_store
                    markersStore.add(this)
                }
            }
        }
    }

    //정보창 생성함수
    private fun createInfoWindow(item: StoreTest, marker: Marker) : InfoWindow {
        var clicked : Boolean = false
        return InfoWindow().apply {
            tag = item
            infoWindows.add(this)
            adapter = createInfoWindowAdapter(item, clicked)
            setOnClickListener {
                if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                setBottomSheetData(item)
                adapter = createInfoWindowAdapter(item, clicked)
                clicked = !clicked
                true
            }
            open(marker)
        }
    }

    //정보창전용 어댑터 생성 함수
    private fun createInfoWindowAdapter(item: StoreTest,  clicked : Boolean) : InfoWindow.ViewAdapter {
        return object : InfoWindow.ViewAdapter() {
            override fun getView(p0: InfoWindow): View {
                val color = when(item.code) {
                    0 -> seoulColor  //(임시)착한업소
                    1 -> matColor    //(임시)인증맛칩
                    else -> likeColor   //(임시) 찜
                }
                with(view) {
                    textName.text = item.name
                    if(!clicked) {
                        viewContent1.backgroundTintList = color
                        viewContent2.backgroundTintList = unClickedColor
                        viewBottom1.imageTintList = color
                        viewBottom2.imageTintList = unClickedColor
                        textName.apply {
                            typeface = null
                            setTextColor(blackColor)
                        }
                    } else {
                        viewContent1.backgroundTintList = clickedColor
                        viewContent2.backgroundTintList = clickedColor
                        viewBottom1.imageTintList = clickedColor
                        viewBottom2.imageTintList = clickedColor
                        textName.apply {
                            typeface = Typeface.DEFAULT_BOLD
                            setTextColor(whiteColor)
                        }
                    }
                    return view.root
                }
            }
        }
    }

    //서클원 생성함수
    private fun createCircle(lat:Double, lng:Double, m:Double) : CircleOverlay {
        return CircleOverlay().apply {
            tag = m
            center = LatLng(lat, lng)
            radius = m
            color = circleColor
            map = naverMap
            circleOverlay.add(this)
        }
    }

    //식당정보 바텀시트 데이터 설정함수
    private fun setBottomSheetData(item : StoreTest) {
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
                filterDialog.show(owner.supportFragmentManager, FILTER)
            }
            R.id.button_gps -> {
                naverMap.apply {
                    cameraPosition = setMapCamera(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y)
                }
            }
        }
    }

    fun filterData(sort: String?, distance: Double?) {
        if(distance != null) {
            if(circleOverlay.size > 0) {
                circleOverlay.forEach {
                    it.map = null
                }
            }
            createCircle(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y, distance)
        }
    }
}