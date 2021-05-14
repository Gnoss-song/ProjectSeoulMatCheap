package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.activity.MAP_01_01
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

const val ADDRESS = "address"
private const val MAP_ZOOM = 18.0

class MAP_01(
    val owner : AppCompatActivity
    ) : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(owner: AppCompatActivity) : Fragment {
            return MAP_01(owner)
        }
    }

    private lateinit var binding : FragmentMap01Binding
    private lateinit var naverMap : NaverMap
    private lateinit var storeWindowBehavior : BottomSheetBehavior<LinearLayout>
    private lateinit var list : MutableList<StoreTest>
    private lateinit var mapMarkers : Vector<Marker>
    private lateinit var mapCircleOverlay : Vector<CircleOverlay>
    private lateinit var infoWindows : Vector<InfoWindow>

    //infoWindow
    private lateinit var view: MapItemInfowindowBinding
    private val storeWindowState = MutableLiveData<Boolean>()
    private lateinit var clickedInfoWindow : ArrayMap<InfoWindow, StoreTest>

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
        mapMarkers = Vector()
        mapCircleOverlay = Vector()
        infoWindows = Vector()
        clickedInfoWindow = arrayMapOf()
        list = Test().addData()
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
        naverMap.apply {
            val locationObserver = Observer<Location> {
                cameraPosition = setMapCamera(it.latitude, it.longitude)
                with(locationOverlay) {
                    isVisible = true
                    position = LatLng(it.latitude, it.longitude)
                    icon = OverlayImage.fromResource(R.drawable.map_marker)
                    iconHeight = 200
                    iconWidth = 200
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
                    storeWindowState.value = false
                    if(clickedInfoWindow.isNotEmpty()) {
                        Log.e("[TEST]", "실행되나!")
                        clickedInfoWindow.forEach { (t, u) ->
                            t.adapter = createInfoWindowAdapter(u, false)
                            clickedInfoWindow.remove(t)
                        }
                    }

                }
            }
        }
        list.forEach {
            val marker = createMaker(
                it.x, it.y,
                when (it.sort) {
                    "한식" -> R.drawable.icon_hansik
                    "중식" -> R.drawable.icon_china
                    "경양식일식" -> R.drawable.icon_japan
                    "기타외식" -> R.drawable.icon_food
                    "미용" -> R.drawable.icon_beauty
                    "세탁" -> R.drawable.icon_wash
                    "숙박" -> R.drawable.icon_hotel
                    else -> R.drawable.icon_store
                }, it
            )
            mapMarkers.add(marker)
            createInfoWindow (it, marker)
        }
    }

    //지도카메라위치
    private fun setMapCamera(lat:Double, lng:Double) : CameraPosition {
        return CameraPosition(LatLng(lat, lng), MAP_ZOOM)
    }

    //마커생성함수
    private fun createMaker(lat:Double, lng:Double, resource:Int, item: StoreTest) : Marker {
        return Marker().apply {
            position = LatLng(lat, lng)
            map = naverMap
            icon = OverlayImage.fromResource(resource)
            width = 120
            height = 120
            isForceShowIcon = true
            setOnClickListener {
                setBottomSheetData(item)
                true
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
                clickedInfoWindow[this] = item
                setBottomSheetData(item)
                if(!clicked) {
                    Log.e("$this", "뭐가 실행됩니까 True")
                    adapter = createInfoWindowAdapter(item, true)
                    clicked = true
                } else {
                    Log.e("$this", "뭐가 실행됩니까 False")
                    adapter = createInfoWindowAdapter(item, false)
                    clicked = false
                    if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                        storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        storeWindowState.value = false
                    }
                }
                Log.e("[히히]", "${clickedInfoWindow.keys}")
                true
            }
            open(marker)
        }
    }

    //정보창전용 어댑터 생성 함수
    private fun createInfoWindowAdapter(item: StoreTest,  clicked : Boolean) : InfoWindow.ViewAdapter {
        val clickedColor = ColorStateList.valueOf(resources.getColor(R.color.black, null))
        val unClickedColor = ColorStateList.valueOf(resources.getColor(R.color.white, null))
        val color = ColorStateList.valueOf(resources.getColor(
            when(item.code) {
                0 -> R.color.map_seoul  //(임시)착한업소
                1 -> R.color.map_mat    //(임시)인증맛칩
                else -> R.color.map_like   //(임시) 찜
            }, null))
        return object : InfoWindow.ViewAdapter() {
            override fun getView(p0: InfoWindow): View {
                with(view) {
                    textName.text = item.name
                    if(!clicked) {
                        viewContent1.backgroundTintList = color
                        viewContent2.backgroundTintList = unClickedColor
                        viewBottom1.imageTintList = color
                        viewBottom2.imageTintList = unClickedColor
                        textName.apply {
                            typeface = null
                            setTextColor(resources.getColor(R.color.black, null))
                        }
                    } else {
                        viewContent1.backgroundTintList = clickedColor
                        viewContent2.backgroundTintList = clickedColor
                        viewBottom1.imageTintList = clickedColor
                        viewBottom2.imageTintList = clickedColor
                        textName.apply {
                            typeface = Typeface.DEFAULT_BOLD
                            setTextColor(resources.getColor(R.color.white, null))
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
            color = resources.getColor(R.color.map_circle, null)
            map = naverMap
            mapCircleOverlay.add(this)
        }
    }

    //식당정보 바텀시트 데이터 설정함수
    private fun setBottomSheetData(item : StoreTest) {
        with(binding.include) {
            Glide.with(owner).load(item.image).into(reviewitem)
            name.text = item.name
            address.text = item.address
            sort.text = item.sort
            distance.text = "${item.distance} km"
            score.text = item.score.toString()
        }
        storeWindowBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        storeWindowState.value = true
    }

    fun mapButtonClick(v : View) {
        when(v.id) {
            R.id.button_list -> {
                val intent = Intent(owner, MAP_01_01::class.java)
                intent.putExtra(ADDRESS, SeoulMatCheap.getInstance().address.value)
                startActivity(intent)
            }
            R.id.button_filter -> {
                MAP_01_02(this).show(owner.supportFragmentManager, FILTER)
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
            if(mapCircleOverlay.size > 0) {
                mapCircleOverlay.forEach {
                    it.map = null
                }
            }
            createCircle(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y, distance)
        }
    }
}