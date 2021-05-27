package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.system.KEY
import kr.co.mapo.project_seoulmatcheap.system.POSITION
import kr.co.mapo.project_seoulmatcheap.ui.fragment.CATEGORY_01_01_01
import kr.co.mapo.project_seoulmatcheap.ui.fragment.CATEGORY_01_02

class CATEGORY_01_01 : AppCompatActivity() {
    var position = -1
    private lateinit var tab_category : TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_01_01)

        position = intent.getIntExtra(POSITION, -1)
        val category010101 = CATEGORY_01_01_01(this)

        val key = intent.getStringExtra(KEY)

        if(position > -1) {
            category010101.position = position
        } else {
            category010101.key = key
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, category010101).commit()
        }

        tab_category = findViewById(R.id.tab_category)
        val CATEGORY_01_02 = CATEGORY_01_02(this, key, position)
        Log.e("[0101]", "$position")
        Log.e("[0101]", "$key")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this!!.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            title = intent.getStringExtra(KEY)
        }

        tab_category.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.frameLayout, category010101).commit()
                    1 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, CATEGORY_01_02).commit()
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}