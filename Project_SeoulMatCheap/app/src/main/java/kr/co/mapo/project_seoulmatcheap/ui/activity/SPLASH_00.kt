package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.system.UserPrefs

private const val SPLASH_DELAY = 500L

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