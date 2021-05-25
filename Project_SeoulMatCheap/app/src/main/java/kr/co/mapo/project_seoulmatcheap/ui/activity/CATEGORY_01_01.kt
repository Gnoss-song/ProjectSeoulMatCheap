package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityCategory0101Binding
import kr.co.mapo.project_seoulmatcheap.ui.fragment.CATEGORY_01_01_01
import kr.co.mapo.project_seoulmatcheap.ui.fragment.CATEGORY_01_02

class CATEGORY_01_01 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_01_01)
        val CATEGORY_01_01_01 = CATEGORY_01_01_01(intent.getStringExtra("key"))
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, CATEGORY_01_01_01).commit()
        }

        var tab_category = findViewById<TabLayout>(R.id.tab_category)
        val CATEGORY_01_02 = CATEGORY_01_02(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this!!.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            title = intent.getStringExtra("key")
        }

        tab_category.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.frameLayout, CATEGORY_01_01_01).commit()
                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, CATEGORY_01_02).commit()
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}