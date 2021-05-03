package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivitySplash01Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap

const val RESULT_SUCCESS = 0
const val RESULT_FAIL = -1

class SPLASH_01 : AppCompatActivity() {

    private lateinit var binding : ActivitySplash01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash01Binding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        requestPermission()
        setView()
    }

    private fun setView() {
        with(binding) {
            radomText.apply {
                val randomList = resources.getStringArray(R.array.splash_radom_guide)
                text = randomList[0]
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        requestPermission()
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        } else {
            goNext(0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100) {
            goNext(grantResults[0])
        }
    }

    private fun goNext(result: Int) {
        when(result) {
            -1 -> {
                SeoulMatCheap.getInstance().showToast(this, getString(R.string.permission_notice))
                if(Build.VERSION.SDK_INT > M) {
                    val appDetail = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                    appDetail.addCategory(Intent.CATEGORY_DEFAULT)
                    startActivity(appDetail)
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
                }
            }
            0 -> { //퍼미션 완료 -> 네트워크, GPS 켜짐 확인 후 메인액티비티로
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1500)
            }
        }
    }
}