package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy010101Binding
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0101Binding

class MY_01_01_01 : AppCompatActivity() {

    private lateinit var binding: ActivityMy010101Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_01_01)

        binding = ActivityMy010101Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}