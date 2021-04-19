package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMap0101Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.fragment.ADDRESS

class MAP_01_01 : AppCompatActivity() {

    private val binding by lazy { ActivityMap0101Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this!!.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            this!!.title = intent.getStringExtra(ADDRESS)
        }
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        setView()
    }

    private fun setView() {
    }
}