package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMainBinding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.fragment.*

class MainActivity : AppCompatActivity() {

    //private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val location by lazy { SeoulMatCheap().getLocation(this) }
    private lateinit var tabLayout : TabLayout

    lateinit var my01fragment: MY_01


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, CATEGORY_01()).commit()
        }
        init()
    }

    private fun init() {
        tabLayout = findViewById(R.id.tabLayout)
        setView()
    }

    private fun setView() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            //선택할 때
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    0 ->  {
                        tab.text = getString(R.string.app_meun1)
                        supportFragmentManager.beginTransaction().replace(R.id.container, MAP_01.getInstance(this@MainActivity, location)).commit()
                        Log.e("[TEST]", "${location?.latitude}, ${location?.longitude}")
                    }
                    1 -> {
                        tab.text = getString(R.string.app_meun2)
                        supportFragmentManager.beginTransaction().replace(R.id.container, SEARCH_01.getInstance(this@MainActivity)).commit()
                    }
                    2 -> {
                        tab.text = getString(R.string.app_meun3)
                        supportFragmentManager.beginTransaction().replace(R.id.container, CATEGORY_01()).commit()
                    }
                    3 -> {
                        tab.text = getString(R.string.app_meun4)
                        supportFragmentManager.beginTransaction().replace(R.id.container, MATCHEAP_01()).commit()
                    }
                    4 -> {
                        tab.text = getString(R.string.app_meun5)
                        supportFragmentManager.beginTransaction().replace(R.id.container, MY_01()).commit()
                    }
                }
            }

            //선택이 풀릴 때
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.text = null
            }

            //선택된 상태에서 다시 선택될 때
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }


}