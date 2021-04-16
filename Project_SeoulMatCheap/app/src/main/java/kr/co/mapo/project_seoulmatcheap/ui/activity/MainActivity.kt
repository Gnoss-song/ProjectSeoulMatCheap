package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMainBinding
import kr.co.mapo.project_seoulmatcheap.ui.fragment.CATEGORY_01
import kr.co.mapo.project_seoulmatcheap.ui.fragment.MAP_01
import kr.co.mapo.project_seoulmatcheap.ui.fragment.MATCHEAP_01
import kr.co.mapo.project_seoulmatcheap.ui.fragment.MY_01

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var mapFragmet : MAP_01
    private lateinit var categoryFragment : CATEGORY_01
    private lateinit var matFragment : MATCHEAP_01
    private lateinit var myFragment : MY_01

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(binding.container.id, CATEGORY_01()).commit()
        }
        init()
        setView()
    }

    private fun init() {
        mapFragmet = MAP_01()
        categoryFragment = CATEGORY_01()
        matFragment = MATCHEAP_01()
        myFragment = MY_01()
    }

    private fun setView() {
        with(binding.bottomNavigationView) {
            setOnNavigationItemSelectedListener(this@MainActivity)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.map -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, mapFragmet).commit()
                return true
            }
            R.id.search -> {
                val intent = Intent(this, SEARCH_01::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                return true
            }
            R.id.main -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, categoryFragment).commit()
                return true
            }
            R.id.mat -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, matFragment).commit()
                return true
            }
            R.id.my -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, myFragment).commit()
                return true
            }
        }
        return false
    }


}