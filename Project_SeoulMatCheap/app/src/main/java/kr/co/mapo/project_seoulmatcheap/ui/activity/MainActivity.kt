package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.tabs.TabLayout
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMainBinding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.system.UserPrefs
import kr.co.mapo.project_seoulmatcheap.ui.fragment.*

class MainActivity : AppCompatActivity() {

    private var pressedTime : Long = 0
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CATEGORY_01(this@MainActivity))
                .commit()
        }
        init()
    }

    private fun init() {
        val seoulMatCheap = SeoulMatCheap.getInstance()
        seoulMatCheap.setLocation(this)
        setView()
    }

    private fun setView() {
        with(binding.bottomNavigationView) {
            selectedItemId = R.id.main
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.map -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MAP_01.newInstance(this@MainActivity))
                            .commit()
                    }
                    R.id.search -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, SEARCH_01.newInstance(this@MainActivity))
                            .commit()
                    }
                    R.id.main -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, CATEGORY_01(this@MainActivity))
                            .commit()
                    }
                    R.id.my -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MY_01.newInstance(this@MainActivity))
                            .commit()
                    }
                }
                return@setOnNavigationItemSelectedListener true
            }
        }

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > pressedTime + 2000) {
            pressedTime = System.currentTimeMillis()
            SeoulMatCheap.getInstance().showToast(this, "한 번 더 누르면 종료됩니다.")
            return
        }
        if (System.currentTimeMillis() <= pressedTime + 2000) {
            finish()
        }
    }

}
