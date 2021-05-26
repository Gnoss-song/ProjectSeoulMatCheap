package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.MenuEntity
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentInform021Binding
import kr.co.mapo.project_seoulmatcheap.system.*


class INFORM_02_1(
    private val item : StoreEntity) : Fragment(), OnMapReadyCallback {

    private lateinit var owner: AppCompatActivity
    lateinit var binding : FragmentInform021Binding
    private lateinit var naverMap : NaverMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inform_02_1, container, false)
        binding.item = item
        init()
        return binding.root
    }

    private fun init() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textTime.text = if(item?.time == "null") "-" else item?.time
            textClosed.text = if(item?.closed == "null") "-" else item?.closed
            recyclerViewMenu.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                AppDatabase(requireContext())!!.storeDAO().getMenu(item!!.id).observe(viewLifecycleOwner, {
                        adapter = MenuAdapter(it)
                    })
            }
            buttonCall.setOnClickListener {

                val tt = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+item!!.tel))
                startActivity(tt)
            }
            buttonNavi.setOnClickListener {
                val nv = Intent(Intent.ACTION_VIEW, Uri.parse("navermaps://?menu=location&pinType=place&lat=${item!!.lat}&lng=${item!!.lng}&title=${item!!.name}")).apply {
                    `package` = "com.nhn.android.nmap"
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(nv)
            }
        }
    }

    override fun onMapReady(p0: NaverMap) {
        val locationSource = FusedLocationSource(this, 100)
        naverMap = p0
//        Log.e("[데이터 로딩 테스트]", "리스트 사이즈 : ${storeList.size}")
        naverMap.apply {
            cameraPosition = CameraPosition(LatLng(item.lat, item.lng), 16.0)
            with(locationOverlay) {
                isVisible = true
                position = LatLng(item.lat, item.lng)
                icon = MapHelper.map_gps_marker
                iconHeight = 100
                iconWidth = 100
            }
            minZoom = MAP_MIN_ZOOM
            addOnCameraChangeListener { reason, _ ->
                /*
                REASON_DEVELOPER: 개발자가 API를 호출해 카메라가 움직였음을 나타냅니다. 기본값입니다.
                REASON_GESTURE: 사용자의 제스처로 인해 카메라가 움직였음을 나타냅니다.
                REASON_CONTROL: 사용자의 버튼 선택으로 인해 카메라가 움직였음을 나타냅니다.
                REASON_LOCATION: 위치 트래킹 기능으로 인해 카메라가 움직였음을 나타냅니다.
                 */
            }
        }
    }
    inner class MenuAdapter(private val menuList:List<MenuEntity>) : RecyclerView.Adapter<MenuAdapter.HolderView>() {
        inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val menu_name : TextView = itemView.findViewById(R.id.menu_name)
            val menu_cost : TextView = itemView.findViewById(R.id.menu_cost)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
            return HolderView(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_menu, parent, false))
        }
        override fun onBindViewHolder(holder: HolderView, position: Int) {
            val item = menuList[position]
            with(holder) {
                menu_name.text = item.menu
                menu_cost.text = item.price.toString()
            }
        }
        override fun getItemCount() = menuList.size
    }
}