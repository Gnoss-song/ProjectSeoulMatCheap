package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMap0101Binding
import kr.co.mapo.project_seoulmatcheap.system.ADDRESS
import kr.co.mapo.project_seoulmatcheap.system.LIST
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.ListRecyclerViewAdapter

class MAP_01_01 : AppCompatActivity() {

    private val binding by lazy { ActivityMap0101Binding.inflate(layoutInflater) }
    private var storelist = listOf<StoreEntity>()
    private var sortedlist = listOf<StoreEntity>()

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
        storelist = intent.getSerializableExtra(LIST) as List<StoreEntity>
        sortedlist = storelist.sortedBy { SeoulMatCheap.getInstance().calculateDistanceDou(it.lat, it.lng) }
        setView()
    }

    private fun setView() {
        with(binding) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(
                    this@MAP_01_01,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                //adapter = ListRecyclerViewAdapter(list,this@MAP_01_01)
            }
            categoryScore.setOnClickListener {
              //  recyclerView.adapter = InformListAdapter(list, this@MAP_01_01)
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
                // recyclerView.adapter = InformListAdapter(list, this@MAP_01_01)
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
}