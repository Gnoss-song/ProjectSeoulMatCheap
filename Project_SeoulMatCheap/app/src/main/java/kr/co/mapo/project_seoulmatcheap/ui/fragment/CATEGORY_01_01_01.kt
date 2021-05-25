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
import com.kakao.sdk.common.util.SdkLogLevel
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.ListItem
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentCategory010101Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.ListRecyclerViewAdapter

class CATEGORY_01_01_01(private val owner : AppCompatActivity) : Fragment() {
    var key : String? = null
    var position : Int = -1
    var list = listOf<StoreEntity>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_01_01_01, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryRV = view.findViewById<RecyclerView>(R.id.categoryRV)
        val tabLayout2 = view.findViewById<TabLayout>(R.id.tabLayout2)
        val categoryScore = view.findViewById<TextView>(R.id.category_score)
        val categoryDistance = view.findViewById<TextView>(R.id.category_distance)
        with(categoryRV) {
            layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
            if (position == -1) {
                AppDatabase(owner)!!.storeDAO().getGuStore(key!!).observe(
                    viewLifecycleOwner, {
                        adapter = ListRecyclerViewAdapter(it, owner)
                    }
                )
            } else {
                AppDatabase(owner)!!.storeDAO().getSortStore(position!!).observe(
                    viewLifecycleOwner, {
                        Log.e("[TEST]", "${it.size}")
                        val list = mutableListOf<StoreEntity>()
                        list.apply {
                            for(i in it) {
                                if (SeoulMatCheap.getInstance().calculateDistanceDou(i.lat, i.lng) <= 3.0)
                                    this.add(i)
                            }
                        }
                        this@CATEGORY_01_01_01.list = list
                        Log.e("[TEST]", "${list.size}")
                        Log.e("[설마?]", "${list.size}")
                        adapter = ListRecyclerViewAdapter(list, owner)
                    }
                )
            }

            tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {

                }
            })
        }

        categoryScore.setOnClickListener {
//                val list = listData().apply {
//                    sortByDescending { it.score }
//                }
            //categoryRV.adapter = ListRecyclerViewAdapter(list, owner)
            categoryRV.adapter =  ListRecyclerViewAdapter(list, owner)
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
//                val list = listData().apply {
//                    sortBy { it.distance }
//                }
            val sortedList = list.sortedBy { SeoulMatCheap.getInstance().calculateDistanceDou(it.lat, it.lng) }
            Log.e("[TEST]", sortedList[0].name)
            categoryRV.adapter =  ListRecyclerViewAdapter(sortedList, owner)
            with(categoryDistance) {
                typeface = Typeface.DEFAULT_BOLD
                setTextColor(resources.getColor(R.color.main, null))
            }
            with(categoryScore) {
                typeface = null
                setTextColor(resources.getColor(R.color.dot_edge, null))
                Log.e("[TEST]", list[0].name)
            }
        }
    }
}