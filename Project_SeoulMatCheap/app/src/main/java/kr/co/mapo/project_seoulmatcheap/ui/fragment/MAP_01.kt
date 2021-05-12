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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
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
    private lateinit var mapFragment : MapFragment
    private lateinit var storeWindowBehavior : BottomSheetBehavior<LinearLayout>
    private lateinit var list : MutableList<StoreTest>

    //infoWindow
    private lateinit var view: MapItemInfowindowBinding
    private val behavior_state = MutableLiveData<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_01, container,false)
        view = MapItemInfowindowBinding.inflate(inflater)
        binding.fragment = this
        init()
        return binding.root
    }

    private fun init() {
        list = addData()
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        storeWindowBehavior = BottomSheetBehavior.from(binding.include.storeBottomLayout)
        behavior_state.value = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SeoulMatCheap.getInstance().address.observe(viewLifecycleOwner, Observer {
            binding.toolbar.title = it
        })
        mapFragment.getMapAsync(this)
    }

    //네이버지도 초기 설정
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        naverMap.apply {
            val locationObserver = Observer<Location> {
                cameraPosition = setMapCamera(it.latitude, it.longitude)
                locationOverlay.apply {
                    isVisible = true
                    position = LatLng(it.latitude, it.longitude)
                    icon = OverlayImage.fromResource(R.drawable.map_marker)
                    iconHeight = 200
                    iconWidth = 200
                }
            }
            SeoulMatCheap.getInstance().location.observe(viewLifecycleOwner, locationObserver)
            symbolScale = 0.8f
            addOnCameraChangeListener { reason, _ ->
                if(reason != CameraUpdate.REASON_DEVELOPER) {
                    SeoulMatCheap.getInstance().location.removeObserver(locationObserver)
                }
            }
            setOnMapClickListener { _, _ ->
                if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    behavior_state.value = false
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
        var clicked = false
        return InfoWindow().apply {
            val behaviorObserver = Observer<Boolean> {
                adapter = createInfoWindowAdapter(item, it)
            }
            behavior_state.removeObserver(behaviorObserver)
            adapter = createInfoWindowAdapter(item, clicked)
            setOnClickListener {
                setBottomSheetData(item)
                if(!clicked) {
                    adapter = createInfoWindowAdapter(item, true)
                    clicked = true
                } else {
                    adapter = createInfoWindowAdapter(item, false)
                    clicked = false
                    if(storeWindowBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                        storeWindowBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        behavior_state.value = false
                    }
                    behavior_state.observe(viewLifecycleOwner, behaviorObserver)
                }
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
            center = LatLng(lat, lng)
            radius = m
            color = resources.getColor(R.color.map_circle, null)
            map = naverMap
        }
    }

    //식당정보 바텀시트 데이터 설정함수
    private fun setBottomSheetData(item : StoreTest) {
        with(binding.include) {
            Glide.with(owner).load(item.image).into(image)
            name.text = item.name
            address.text = item.address
            sort.text = item.sort
            distance.text = "${item.distance} km"
            score.text = item.score.toString()
        }
        storeWindowBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior_state.value = true
    }

    fun mapButtonClick(v : View) {
        when(v.id) {
            R.id.button_list -> {
                val intent = Intent(owner, MAP_01_01::class.java)
                intent.putExtra(ADDRESS, SeoulMatCheap.getInstance().address.value)
                startActivity(intent)
            }
            R.id.button_filter -> {
                MAP_01_02().show(owner.supportFragmentManager, FILTER)
            }
            R.id.button_gps -> {
                naverMap.apply {
                    cameraPosition = setMapCamera(SeoulMatCheap.getInstance().x, SeoulMatCheap.getInstance().y)
                }
            }
        }
    }

    fun addData() : MutableList<StoreTest> {
        return mutableListOf<StoreTest>().apply {
            add(StoreTest(0, "http://tearstop.seoul.go.kr/mulga/photo/20180927103419.jpg",
                "석화촌", "서울특별시 강남구 강남대로 240 (도곡동) 지하1층 B111", "한식", 0.0, 0.0, 37.4852595655063, 127.034429699919))
            add(StoreTest(0, "http://tearstop.seoul.go.kr/mulga/photo/20180927103419.jpg",
                "영동원삼겹살", "서울특별시 강남구 강남대로 520 (논현동)", "한식", 0.0, 0.0, 37.5086742929959, 127.023049521427))
            add(StoreTest(1, "http://tearstop.seoul.go.kr/mulga/photo/20200129104918.jpg",
                "고등어식당", "서울특별시 강남구 강남대로84길 33 (역삼동)", "한식", 0.0, 0.0, 37.4907495832408, 127.034043540718))
            add(StoreTest(1,"http://tearstop.seoul.go.kr/mulga/photo/20160122164003.jpg",
                "수타손짜장", "서울특별시 강남구 개포로 615 (개포동)  지하1층 110호", "중식", 0.0, 0.0, 37.4926592719052, 127.075585261975))
            add(StoreTest(1,"http://tearstop.seoul.go.kr/mulga/photo/20190218105050.jpg",
                "맛있는돈까스", "서울특별시 강남구 개포로 638 지상1층(일원동)", "경양식일식", 0.0, 0.0, 37.4943419725249, 127.082374584995))
            add(StoreTest(2,"http://tearstop.seoul.go.kr/mulga/photo/20200831134534.jpg",
                "요기루", "서울특별시 강남구 남부순환로 2917 청실종합상가 지하1층 9호", "기타외식", 0.0, 0.0, 37.4931669991596, 127.059165308545))
            add(StoreTest(1,"http://tearstop.seoul.go.kr/mulga/photo/20140424131215.jpg",
                "선경헤어샵", "서울특별시 강남구 삼성로 151 선경상가 지하1층 13호(대치동 선경아파트)", "미용", 0.0, 0.0, 37.4914886839937, 127.063832936668))
            add(StoreTest(0,"http://tearstop.seoul.go.kr/mulga/photo/20200831134534.jpg",
                "요기루", "서울특별시 강남구 남부순환로 2917 청실종합상가 지하1층 9호", "기타외식", 0.0, 0.0, 37.4931669991596, 127.059165308545))
            add(StoreTest(2,"http://tearstop.seoul.go.kr/mulga/photo/20200831134534.jpg",
                "요기루", "서울특별시 강남구 남부순환로 2917 청실종합상가 지하1층 9호", "기타외식", 0.0, 0.0, 37.4931669991596, 127.059165308545))
            add(StoreTest(2,"http://tearstop.seoul.go.kr/mulga/photo/20200831134534.jpg",
                "요기루", "서울특별시 강남구 남부순환로 2917 청실종합상가 지하1층 9호", "기타외식", 0.0, 0.0, 37.4931669991596, 127.059165308545))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20190218103305.jpg"	,	"행운식당"	,	"서울특별시 강남구 선릉로 328 1층 8호(대치동)"	,	"한식"	,	0.0	,	0.0	,	37.4990323	,	127.0518988	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20170208124305.jpg"	,	"반색식당"	,	"서울특별시 강남구 선릉로6길 19 (개포동)"	,	"한식"	,	0.0	,	0.0	,	37.48327066	,	127.0623594	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20140430172449.jpg"	,	"오복냉면"	,	"서울특별시 강남구 선릉로76길 21 (대치동)"	,	"한식"	,	0.0	,	0.0	,	37.50185462	,	127.0521782	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20140430160838.jpg"	,	"사천옥"	,	"서울특별시 강남구 선릉로86길 16 (대치동)"	,	"한식"	,	0.0	,	0.0	,	37.50325967	,	127.0510194	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20140430180509.jpg"	,	"여신김밥"	,	"서울특별시 강남구 선릉로90길 70 인텔빌딩 지상1층(대치동)"	,	"기타외식"	,	0.0	,	0.0	,	37.50549958	,	127.0548867	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20160707120636.jpg"	,	"만금성"	,	"서울특별시 강남구 언주로 117 상가동 지하1층 42호(도곡동 우성4차아파트)"	,	"중식"	,	0.0	,	0.0	,	37.48509823	,	127.0485993	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20190924131859.jpg"	,	"엄마손김밥"	,	"서울특별시 강남구 언주로 314 "	,	"기타외식"	,	0.0	,	0.0	,	37.49588839	,	127.046474	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20180124142411.jpg"	,	"M&M미용실"	,	"서울특별시 강남구 일원로3길 10 (일원동)"	,	"미용"	,	0.0	,	0.0	,	37.49122033	,	127.081545	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20160122163735.jpg"	,	"한우정육점식당"	,	"서울특별시 강남구 일원로5길 24 (일원동)"	,	"한식"	,	0.0	,	0.0	,	37.49132247	,	127.0826961	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20181226125147.jpg"	,	"행복한밥상"	,	"서울특별시 강남구 테헤란로 310 지하1층 109호(역삼동) "	,	"한식"	,	0.0	,	0.0	,	37.50285495	,	127.0447525	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20180829153855.JPG"	,	"쉼터기원"	,	"서울특별시 강동구 양재대로 1389 (성내동) 3층"	,	"기타"	,	0.0	,	0.0	,	37.5301863	,	127.1367678	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20130811185221.jpg"	,	"가나미용실"	,	"서울특별시 강동구 양재대로113길 14 (길동)"	,	"미용"	,	0.0	,	0.0	,	37.53815962	,	127.1392405	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20151120151458.jpg"	,	"토속칼국수"	,	"서울특별시 강동구 양재대로113길 8 (길동)"	,	"한식"	,	0.0	,	0.0	,	37.53809176	,	127.1395118	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20190920111525.jpg"	,	"다인"	,	"서울특별시 강동구 양재대로147길 7 (명일동)"	,	"한식"	,	0.0	,	0.0	,	37.55216962	,	127.1435324	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/00001163.jpg"	,	"재일세탁소"	,	"서울특별시 강북구 삼양로117길 18 (수유동)"	,	"세탁"	,	0.0	,	0.0	,	37.64402268	,	127.0152802	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20130617104826.jpg"	,	"토종집"	,	"서울특별시 강북구 인수봉로19나길 32 (수유동 삼안빌라)"	,	"한식"	,	0.0	,	0.0	,	37.6271477	,	127.0107264	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20110615094330.JPG"	,	"명동세탁소"	,	"서울특별시 강북구 인수봉로79길 57 (수유동)"	,	"세탁"	,	0.0	,	0.0	,	37.6432269	,	127.0086593	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20160718162724.jpg"	,	"신진미용실"	,	"서울특별시 강북구 한천로 1104 (수유동)"	,	"미용"	,	0.0	,	0.0	,	37.64412065	,	127.0213276	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20180112154246.jpg"	,	"아쿠아산소사우나"	,	"서울특별시 강북구 한천로 1131(수유동)"	,	"목욕"	,	0.0	,	0.0	,	37.64543795	,	127.0186256	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20190924135108.jpg"	,	"대청김밥"	,	"서울특별시 강남구 개포로621 SH 지하1층"	,	"기타외식"	,	0.0	,	0.0	,	37.494132	,	127.0787833	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20180919154038.png"	,	"커피디인"	,	"서울특별시 서대문구 거북골로20길3"	,	"기타외식"	,	0.0	,	0.0	,	37.57844835	,	126.9092918	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/a20.jpg"	,	"청진동해장국"	,	"서울특별시 관악구 관악로 105 (봉천동) 동산빌딩1층"	,	"한식"	,	0.0	,	0.0	,	37.47463102	,	126.9526004	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20120620163117.JPG"	,	"겨울나그네"	,	"서울특별시 광진구 능동로 147 (화양동)"	,	"경양식일식"	,	0.0	,	0.0	,	37.5439795	,	127.0722381	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20151228173019.jpg"	,	"코닥스튜디오"	,	"서울특별시 광진구 영화사로 4 (중곡동)"	,	"기타"	,	0.0	,	0.0	,	37.55844852	,	127.0880971	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20121108172207.jpg"	,	"테마모텔"	,	"서울특별시 영등포구 영중로6길 23-5 (영등포동3가)"	,	"숙박"	,	0.0	,	0.0	,	37.51798578	,	126.9083485	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20111104140652.jpg"	,	"독립문 모텔"	,	"서울특별시 종로구 통일로 220 (교북동)"	,	"숙박"	,	0.0	,	0.0	,	37.57273208	,	126.9602023	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20150608172509.JPG"	,	"서울초밥"	,	"서울특별시 영등포구 선유로 250 (양평동4가)"	,	"경양식일식"	,	0.0	,	0.0	,	37.53477955	,	126.8972956	))
            add(StoreTest(1,	"http://tearstop.seoul.go.kr/mulga/photo/20130612150704.jpg"	,	"선유일식"	,	"서울특별시 영등포구 영신로 17길3 (영등포본동)"	,	"경양식일식"	,	0.0	,	0.0	,	37.51418116	,	126.9076975	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20110727150223.JPG"	,	"무진장"	,	"서울특별시 용산구 원효로 212 (원효로2가)"	,	"경양식일식"	,	0.0	,	0.0	,	37.53747475	,	126.9656654	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20130715155527.jpg"	,	"모리모리"	,	"서울특별시 용산구 한강대로 281 (갈월동)"	,	"경양식일식"	,	0.0	,	0.0	,	37.54334884	,	126.9720758	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20120706150456.JPG"	,	"칭니"	,	"서울특별시 구로구 구로중앙로 198 (구로동)"	,	"중식"	,	0.0	,	0.0	,	37.50478644	,	126.8791027	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20170914140455.png"	,	"다래성"	,	"서울특별시 금천구 시흥대로 471-1 (독산동)"	,	"중식"	,	0.0	,	0.0	,	37.47574327	,	126.8981955	))
            add(StoreTest(2,	"http://tearstop.seoul.go.kr/mulga/photo/20170914152014.png"	,	"육합춘짜장짬뽕전문"	,	"서울특별시 금천구 시흥대로26길 5 (시흥동)"	,	"중식"	,	0.0	,	0.0	,	37.44185638	,	126.9041384	))
            add(StoreTest(0,	"http://tearstop.seoul.go.kr/mulga/photo/20180122111946.jpg"	,	"짬뽕늬우스"	,	"서울특별시 노원구 노해로81길 6 (상계동)"	,	"중식"	,	0.0	,	0.0	,	37.65530266	,	127.063099	))

        }
    }

}

data class StoreTest(
    val code : Int,
    val image : String,
    val name : String,
    val address : String,
    val sort : String,
    val distance : Double,
    val score : Double,
    val x : Double,
    val y : Double
)