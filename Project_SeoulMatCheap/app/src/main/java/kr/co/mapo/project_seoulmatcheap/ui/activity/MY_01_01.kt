package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0101Binding


class MY_01_01 : AppCompatActivity() {
    private lateinit var binding: ActivityMy0101Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_01_01)

        binding = ActivityMy0101Binding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    data class Item(val marketIV:Int,
                    var name:String = "",
                    var address: String = "",
                    var sort: String ="",
                    var distance : String = "",
                    var score : String = "")

}