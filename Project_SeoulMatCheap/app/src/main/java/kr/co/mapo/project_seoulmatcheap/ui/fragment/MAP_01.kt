package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMap01Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.activity.MAP_01_01
import java.io.Serializable

const val ADDRESS = "address"

class MAP_01(
    val owner : AppCompatActivity,
    var x : Double,
    var y : Double
    ) : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(owner: AppCompatActivity, x: Double, y:Double) : Fragment {
            return MAP_01(owner, x, y)
        }
    }

    private lateinit var binding : FragmentMap01Binding
    private lateinit var naverMap : NaverMap
    private lateinit var mapFragment : MapFragment
    private var address = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_01, container,false)
        binding.fragment = this
        init()
        return binding.root
    }

    private fun init() {
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        address = SeoulMatCheap().getAddress(x, y, owner)
        if(address != null) {
            binding.toolbar.title = address
        } else {
            binding.toolbar.title = "GPS를 켜주세요"
        }
        mapFragment.getMapAsync(this)
    }

    //네이버지도 초기 설정
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        naverMap.apply {
            cameraPosition = setMapCamera(x, y)
        }
        createInfoWindow("테스트1", createMaker(x, y, R.drawable.icon_hansik))
        createInfoWindow("테스트2", createMaker(37.564338, 126.910794, R.drawable.icon_china))
        createInfoWindow("테스트3", createMaker(37.564110, 126.911041, R.drawable.icon_japan))
    }

    //지도카메라위치
    private fun setMapCamera(lat:Double, lng:Double) : CameraPosition {
        return CameraPosition(LatLng(lat, lng), 17.0)
    }

    //마커생성함수
    private fun createMaker(lat:Double, lng:Double, resource:Int) : Marker {
        return Marker().apply {
            position = LatLng(lat, lng)
            map = naverMap
            icon = OverlayImage.fromResource(resource)
            width = 150
            height = 150
            isForceShowIcon = true
        }
    }

    //정보창 생성함수
    private fun createInfoWindow(message : String, marker: Marker) : InfoWindow {
        return InfoWindow().apply {
            adapter = object : InfoWindow.DefaultTextAdapter(owner) {
                override fun getText(p0: InfoWindow): CharSequence {
                    return message
                }
            }
            open(marker)
        }
    }

    fun showFilter(v : View) {
        owner.supportFragmentManager.beginTransaction().add(R.id.filter, MAP_01_02(owner))
            .addToBackStack(null)
            .commit()
    }

    fun showList(v : View) {
        val intent = Intent(owner, MAP_01_01::class.java)
        intent.putExtra(ADDRESS, address)
        startActivity(intent)
    }

    fun resetLocation(v : View) {
        naverMap.apply {
            cameraPosition = setMapCamera(x, y)
        }
    }

}