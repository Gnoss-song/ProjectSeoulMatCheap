package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform02Binding

class INFORM_02 : AppCompatActivity() {
    private lateinit var binding : ActivityInform02Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInform02Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}