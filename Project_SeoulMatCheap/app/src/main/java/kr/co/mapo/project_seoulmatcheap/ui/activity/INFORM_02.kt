package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform02Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InformViewPagerAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InfromReviewAdapter
import kr.co.mapo.project_seoulmatcheap.ui.fragment.INFORM_02_1

private const val PERMISSION_REQUEST_CODE = 100

class INFORM_02 : AppCompatActivity() {

    private lateinit var binding : ActivityInform02Binding
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("[테스트]", "테스트")
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
        val viewPagerAdapter = InformViewPagerAdapter(this@INFORM_02)
        with(binding) {
            reviewRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@INFORM_02, LinearLayoutManager.HORIZONTAL, false)
                adapter = InfromReviewAdapter()
            }
            buttonReview.setOnClickListener {
                if(viewPager.currentItem == 0) {    //리뷰더보기
                    buttonReview.text = resources.getString(R.string.review_fold)
                    viewPager.currentItem = 1
                } else {
                    buttonReview.text = resources.getString(R.string.review_further)
                    viewPager.currentItem = 0
                }
            }
            viewPager.apply {
                adapter = viewPagerAdapter
                currentItem = 0
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        buttonReview.text = if(position == 0) resources.getString(R.string.review_further)
                                            else resources.getString(R.string.review_fold)
                    }
                })
            }
            indicator.setViewPager2(binding.viewPager)
            buttonWrite.setOnClickListener {
                startActivity(Intent(this@INFORM_02, INFORM_02_02::class.java))
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
}