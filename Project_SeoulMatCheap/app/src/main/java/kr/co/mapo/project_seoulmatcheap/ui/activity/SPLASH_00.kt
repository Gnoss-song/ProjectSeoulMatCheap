package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.mapo.project_seoulmatcheap.R

private const val SPLASH_DELAY = 600L

class SPLASH_00 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_00)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LOGIN_01::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}