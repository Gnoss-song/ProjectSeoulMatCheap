package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentCategory0102Binding

class CATEGORY_01_03 : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_01_03)

        val tranBtn = findViewById<ImageView>(R.id.transparent_closeBtn)
        tranBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.transparent_closeBtn -> finish()
        }
    }
}