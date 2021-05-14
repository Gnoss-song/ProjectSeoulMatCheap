package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import kr.co.mapo.project_seoulmatcheap.R

class PERMISSION_01 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission01)

        with(findViewById<TextView>(R.id.textView)) {
            (text as Spannable).apply {
            setSpan(ForegroundColorSpan(resources.getColor(R.color.main, null)), 0, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            setSpan(StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            }
        }
    }
    fun goNextActivity(v:View) {
        startActivity(Intent(this, SPLASH_01::class.java))
        finish()
    }
}