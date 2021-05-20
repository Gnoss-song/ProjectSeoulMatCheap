package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMap0101Binding
import kr.co.mapo.project_seoulmatcheap.system.ADDRESS
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InformListAdapter

data class Test(val image : String, val name : String, val address : String, val kind : String, val distance: Double, val rate : Double)

class MAP_01_01 : AppCompatActivity() {

    private val binding by lazy { ActivityMap0101Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_icon)
            title = intent.getStringExtra(ADDRESS)
        }
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        var list = arrayListOf<Test>()
        list = addData(list)
        setView(list)
    }

    private fun addData(list: ArrayList<Test>) : ArrayList<Test> {
        list.add(Test("https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20200720_213%2F1595187108144mKFsY_JPEG%2FadS9aicq3pwvOVuInoxj9dlR.jpg",
        "테스트1", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.4, 4.1))
        list.add(Test("https://images.unsplash.com/photo-1555655364-2ee07ae4284d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1350&q=80",
            "테스트2", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.6, 3.1))
        list.add(Test("https://images.unsplash.com/photo-1537327859127-324cb0367b00?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1351&q=80",
            "테스트3", "서울특별시 성동구 뚝섬로3길 18", "꽃", 1.2, 4.3))
        list.add(Test("https://images.unsplash.com/reserve/3waHuaQXRsCuMiUYzARw_IMG_3379.jpg?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1350&q=80",
            "테스트4", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.1, 4.2))
        list.add(Test("https://images.unsplash.com/photo-1469130167642-e0f5f7c41e0c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1267&q=80",
            "테스트5", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.3, 4.1))
        list.add(Test("https://images.unsplash.com/photo-1460968165139-8bbbbb12869c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
            "테스트6", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.4, 4.0))
        list.add(Test("https://images.unsplash.com/photo-1462470583607-1e62336830e5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
            "테스트7", "서울특별시 성동구 뚝섬로3길 19", "꽃", 1.4, 3.1))
        list.add(Test("https://images.unsplash.com/photo-1462798548638-59b18a8ba04e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1489&q=80",
            "테스트8", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.7, 3.5))
        list.add(Test("https://images.unsplash.com/photo-1464693117936-6dada4d0523b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
            "테스트9", "서울특별시 성동구 뚝섬로3길 16", "꽃", 1.5, 4.1))
        list.add(Test("https://images.unsplash.com/photo-1464983013084-558d02a70711?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1348&q=80",
            "테스트10", "서울특별시 성동구 뚝섬로3길 16", "꽃", 1.2, 3.2))
        list.add(Test("https://images.unsplash.com/photo-1464881882122-b5cfd8c67be0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1355&q=80",
            "테스트11", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.1, 4.1))
        list.add(Test("https://images.unsplash.com/photo-1465508148758-b54e78ed5efb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1350&q=80",
            "테스트12", "서울특별시 성동구 뚝섬로3길 16", "꽃", 0.9, 4.1))
        return list
    }

    private fun setView(list: ArrayList<Test>) {
        with(binding) {
            recyclerView.apply {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                    this@MAP_01_01,
                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = kr.co.mapo.project_seoulmatcheap.ui.adpater.InformListAdapter(list)
            }
            /*
            sortDistanceBtn.apply {
                setOnClickListener {
                    list.sortBy { it.distance }
                    recyclerView.adapter = InformListAdapter(list)
                }
            }
            sortScoreBtn.apply {
                setOnClickListener {
                    list.sortBy { it.rate }
                    recyclerView.layoutManager = LinearLayoutManager(this@MAP_01_01, LinearLayoutManager.VERTICAL, true)
                    recyclerView.adapter = InformListAdapter(list)
                }
            }
             */
        }
    }
}