package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0101Binding
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0103Binding

class MY_01_03 : AppCompatActivity() {

    private lateinit var binding: ActivityMy0103Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_01_01)

        binding = ActivityMy0103Binding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}