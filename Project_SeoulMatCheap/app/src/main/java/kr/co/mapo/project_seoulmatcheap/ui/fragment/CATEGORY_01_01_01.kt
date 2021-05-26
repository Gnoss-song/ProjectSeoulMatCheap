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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.ListRecyclerViewAdapter

class CATEGORY_01_01_01(private val owner : AppCompatActivity) : Fragment() {
    var key : String? = null
    var position : Int = -1
    var list = listOf<StoreEntity>()
    private lateinit var categoryRV : RecyclerView
    private lateinit var tabLayout2 : TabLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_01_01_01, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryRV = view.findViewById(R.id.categoryRV)
        tabLayout2 = view.findViewById(R.id.tabLayout2)
        val categoryScore = view.findViewById<TextView>(R.id.category_score)
        val categoryDistance = view.findViewById<TextView>(R.id.category_distance)
        with(categoryRV) {
            layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
            if (position == -1) {
                AppDatabase(owner)!!.storeDAO().getGuStore(key!!).observe(
                    viewLifecycleOwner, {
                        list = it
                        adapter = ListRecyclerViewAdapter(it, owner)
                    }
                )
            } else {
                AppDatabase(owner)!!.storeDAO().getSortStore(position!!).observe(
                    viewLifecycleOwner, {
                        val list = mutableListOf<StoreEntity>()
                        list.apply {
                            for(i in it) {
                                if (SeoulMatCheap.getInstance().calculateDistanceDou(i.lat, i.lng) <= 3.0)
                                    this.add(i)
                            }
                        }
                        this@CATEGORY_01_01_01.list = list
                        adapter = ListRecyclerViewAdapter(list, owner)
                    }
                )
            }
            tabLayout2.apply {
                if (position > -1) {
                    this.getTabAt(position+1)?.select()
                }
            }
            tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                //탭 선택
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tabPosition = tab?.position
                    if (position == -1) {
                        categoryRV.adapter = if (tabPosition != null && tabPosition > 0) {
                            val sortedList = arrayListOf<StoreEntity>().apply {
                                list.forEach {
                                    if(it.sort == tabPosition-1) this.add(it)
                                }
                            }
                            ListRecyclerViewAdapter(sortedList, owner)
                        } else {
                            ListRecyclerViewAdapter(list, owner)
                        }
                    } else {
                        if (tabPosition != null && tabPosition > 0) {
                            AppDatabase(owner)!!.storeDAO().getSortStore(tab.position - 1)
                                .observe(viewLifecycleOwner, {
                                    this@CATEGORY_01_01_01.list = it
                                    val list = mutableListOf<StoreEntity>()
                                    if (this@CATEGORY_01_01_01.position > -1) {
                                        list.apply {
                                            for (i in it) {
                                                if (SeoulMatCheap.getInstance()
                                                        .calculateDistanceDou(i.lat, i.lng) <= 3.0
                                                )
                                                    this.add(i)
                                            }
                                        }
                                        this@CATEGORY_01_01_01.list = list
                                    }
                                    categoryRV.adapter =
                                        ListRecyclerViewAdapter(this@CATEGORY_01_01_01.list, owner)
                                })
                        } else if (tabPosition == 0) {
                            AppDatabase(owner)!!.storeDAO().getAllStore()
                                .observe(viewLifecycleOwner, {
                                    this@CATEGORY_01_01_01.list = it
                                    val list = mutableListOf<StoreEntity>()
                                    list.apply {
                                        for (i in it) {
                                            if (SeoulMatCheap.getInstance()
                                                    .calculateDistanceDou(i.lat, i.lng) <= 3.0
                                            )
                                                this.add(i)
                                        }
                                    }
                                    this@CATEGORY_01_01_01.list = list
                                    categoryRV.adapter =
                                        ListRecyclerViewAdapter(this@CATEGORY_01_01_01.list, owner)
                                })
                        }
                    }
                }
            })
        }

        categoryScore.setOnClickListener {
            categoryRV.adapter =  ListRecyclerViewAdapter(this.list, owner)
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
            val sortedList = list.sortedBy { SeoulMatCheap.getInstance().calculateDistanceDou(it.lat, it.lng) }
            categoryRV.adapter =  ListRecyclerViewAdapter(sortedList, owner)
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

}