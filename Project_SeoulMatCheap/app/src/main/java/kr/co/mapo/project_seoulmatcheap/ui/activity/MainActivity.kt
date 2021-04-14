package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMainBinding
import kr.co.mapo.project_seoulmatcheap.ui.fragment.MY_01

class MainActivity : AppCompatActivity() {

    lateinit var my01fragment: MY_01


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        my01fragment = MY_01()

        binding.button.setOnClickListener {

            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.add(R.id.framelayout, my01fragment)
            ft.commit()
            binding.button.visibility = View.GONE
        }
    }
}