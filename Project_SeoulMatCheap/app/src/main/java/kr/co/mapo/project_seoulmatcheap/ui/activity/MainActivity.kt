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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(binding.container.id, CATEGORY_01()).commit()
        }
        setView()
    }

    private fun setView() {
        with(binding.bottomNavigationView) {
            setOnNavigationItemSelectedListener(this@MainActivity)
            
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.map -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, MAP_01()).commit()
                return true
            }
            R.id.search -> {
                val intent = Intent(this, SEARCH_01::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                return true
            }
            R.id.main -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, CATEGORY_01()).commit()
                return true
            }
            R.id.mat -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, MATCHEAP_01()).commit()
                return true
            }
            R.id.my -> {
                supportFragmentManager.beginTransaction().replace(binding.container.id, MY_01()).commit()
                return true
            }
        }
        return false
    }


}