package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.*
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivitySplash01Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap

private const val SPLASH_DELAY = 1000L
private const val PERMISSION_REQUEST_CODE = 100
private const val PERMISSION_FAIL = -1
private const val PERMISSION_REQUIRE = 0

class SPLASH_01 : AppCompatActivity() {

    private lateinit var binding : ActivitySplash01Binding
    private lateinit var connectivityManager : ConnectivityManager
    private lateinit var netWorkCallback : ConnectivityManager.NetworkCallback
    private var network_state = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash01Binding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        netWorkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                goMainActivity()
            }
        }
        requestPermission()
    }

    override fun onRestart() {
        super.onRestart()
        requestPermission()
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CALL_PHONE
                ), PERMISSION_REQUEST_CODE)
        } else {
            resultPermission(PERMISSION_REQUIRE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE) {
            resultPermission(grantResults[0])
        }
    }

    private fun resultPermission(result: Int) {
        when(result) {
            PERMISSION_FAIL -> {
                SeoulMatCheap.getInstance().showToast(this, getString(R.string.permission_notice))
                if(Build.VERSION.SDK_INT > M) {
                    val appDetail = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
                        addCategory(Intent.CATEGORY_DEFAULT)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(appDetail)
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
                }
            }
            PERMISSION_REQUIRE -> { //퍼미션 완료 -> 네트워크 확인 후 메인액티비티로
                //네트워크 연결상태
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if(capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
                    || capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true) {
                    network_state = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        goMainActivity()
                    }, SPLASH_DELAY)
                //네트워크 연결X
                } else {
                    SeoulMatCheap.getInstance().showToast(this, getString(R.string.network_notice))
                    val builder =  NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        .build()
                    connectivityManager.registerNetworkCallback(builder, netWorkCallback)
                }
            }
        }
    }

    private fun goMainActivity() {
        val intent = Intent(this@SPLASH_01, MainActivity::class.java)
        SeoulMatCheap.getInstance().setLocation(this)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!network_state) {
            connectivityManager.bindProcessToNetwork(null)
            connectivityManager.unregisterNetworkCallback(netWorkCallback)
        }
    }
}