package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform02Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InfromReviewAdapter

private const val PERMISSION_REQUEST_CODE = 100

class INFORM_02 : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityInform02Binding
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInform02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            title = "홍콩"
            setHomeAsUpIndicator(R.drawable.ic_back_icon)
        }
        setView()
    }

    private fun setView() {
        with(binding) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@INFORM_02, LinearLayoutManager.HORIZONTAL, false)
                adapter = InfromReviewAdapter()
            }
            //좋아요버튼
            buttonLike.setOnClickListener {
            }
            //전화걸기
            buttonCall.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this@INFORM_02, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@INFORM_02,
                        arrayOf(Manifest.permission.CALL_PHONE), PERMISSION_REQUEST_CODE)
                } else {
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:1588-3468")))
                }
            }
            //길찾기
            buttonNavi.setOnClickListener {

            }
            buttonWrite.setOnClickListener(this@INFORM_02)
            buttonReview.setOnClickListener {
                startActivity(Intent(this@INFORM_02, INFORM_02_01::class.java))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_inform_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.share -> {
                //다른 앱으로 간단한 데이터 보내기
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "공유할 내용")
                startActivity(Intent.createChooser(intent, "System UI에 표시할 문구"))
                true
            }
            else -> false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE) {
            SeoulMatCheap.getInstance().showToast(this, "전화 권한을 승인해주세요.")
            ActivityCompat.requestPermissions(this@INFORM_02,
                arrayOf(Manifest.permission.CALL_PHONE), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_write -> {
                startActivity(Intent(this, INFORM_02_02_01::class.java))
            }
        }
    }
}