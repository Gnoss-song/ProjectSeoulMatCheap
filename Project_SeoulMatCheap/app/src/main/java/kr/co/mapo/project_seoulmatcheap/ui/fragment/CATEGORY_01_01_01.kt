package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.ListItem
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentCategory010101Binding
import kr.co.mapo.project_seoulmatcheap.ui.adpater.ListRecyclerViewAdapter

class CATEGORY_01_01_01(private val owner : AppCompatActivity) : Fragment() {
    var key : String? = null
    var position : Int = -1
    private lateinit var binding : FragmentCategory010101Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_01_01_01, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.categoryRV) {
            layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL,false)
            if(position == -1) {
                AppDatabase(owner)!!.storeDAO().getGuStore(key!!).observe(
                    viewLifecycleOwner, {
                        adapter = ListRecyclerViewAdapter(it, owner)
                    }
                )
            } else {
                AppDatabase(owner)!!.storeDAO().getSortStore(position!!).observe(
                    viewLifecycleOwner, {
                        adapter = ListRecyclerViewAdapter(it, owner)
                    }
                )
            }
        }
        binding.apply {
            categoryScore.setOnClickListener {
                val list = listData().apply {
                    sortByDescending { it.score }
                }
                //categoryRV.adapter = ListRecyclerViewAdapter(list, owner)
                with(categoryDistance) {
                    typeface = null
                    setTextColor(resources.getColor(R.color.dot_edge, null))
                }
                with(categoryScore) {
                    typeface = Typeface.DEFAULT_BOLD
                    setTextColor(resources.getColor(R.color.main, null))
                }
            }
            categoryDistance.setOnClickListener {
                val list = listData().apply {
                    sortBy { it.distance }
                }
                //categoryRV.adapter = ListRecyclerViewAdapter(list)
                with(categoryDistance) {
                    typeface = Typeface.DEFAULT_BOLD
                    setTextColor(resources.getColor(R.color.main, null))
                }
                with(categoryScore) {
                    typeface = null
                    setTextColor(resources.getColor(R.color.dot_edge, null))
                }
            }
        }

    private fun listData(): MutableList<ListItem> {
        val list = mutableListOf<ListItem>()
        return list.apply {
            add(ListItem(R.drawable.solip, "솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km", "4.8"))
            add(ListItem(R.drawable.poonyeon, "풍년갈비", "서울특별시 마포구 동교로 264 (연남동", "한식", "1.4km", "4.4"))
            add(ListItem(R.drawable.wellbeing, "웰빙뚝배기", "서울특별시 송파구 동교로12길 21 (서교동)", "한식", "2.2km", "3.3"))
            add(ListItem(R.drawable.western, "웨스턴후라이드라이스", "서울특별시 서초구 홍익로 26 (동교동)", "일식", "0.5km", "4.5"))
            add(ListItem(R.drawable.halbum, "할범탕수육", "서울특별시 양천구 목동중앙북로 15 (목동)", "중식", "4.4km", "5.0"))
            add(ListItem(R.drawable.solip, "솔잎식당", "서울특별시 강남구 마포대로4길 46 (도화동)", "일식", "1.5km", "4.0"))
            add(ListItem(R.drawable.poonyeon, "풍년갈비", "서울특별시 영등포구 동교로 264 (연남동", "경양식", "0.4km", "3.4"))
            add(ListItem(R.drawable.wellbeing, "웰빙뚝배기", "서울특별시 종로구 동교로12길 21 (서교동)", "기타", "0.7km", "2.1"))
        }
    }
}