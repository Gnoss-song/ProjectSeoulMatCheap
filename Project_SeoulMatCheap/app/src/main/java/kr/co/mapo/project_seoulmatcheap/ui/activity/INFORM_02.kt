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
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform02Binding
import kr.co.mapo.project_seoulmatcheap.system.APP_NAME
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InformViewPagerAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InfromReviewAdapter
import kr.co.mapo.project_seoulmatcheap.ui.fragment.INFORM_02_1

private const val PERMISSION_REQUEST_CODE = 100

class INFORM_02 : AppCompatActivity() {

    private lateinit var binding : ActivityInform02Binding
    private var isLiked = false     //찜표시 저장 Boolean 변수
    private var isLiked_f = false   //초반 찜 Boolean값 저장

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
        menu?.get(0)?.icon = getDrawable(
            if(isLiked) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.like -> {
                if(isLiked) {   //찜이 눌린상태 : 찜 취소하기
                    item.icon = getDrawable(R.drawable.ic_favorite_off)
                    isLiked = false
                } else { //찜이 눌리지 않은 상태 : 찜하기
                    item.icon = getDrawable(R.drawable.ic_favorite_on)
                    isLiked = true
                }
                true
            }
            R.id.share -> {
                //다른 앱으로 간단한 데이터 보내기
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "공유할 내용")
                startActivity(Intent.createChooser(intent, APP_NAME))
                true
            }
            else -> false
        }
    }

    private fun createShareForm() {

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
        if(isLiked_f != isLiked) {  //찜 상태가 변함 -> 서버에 저장
            Log.e("[TEST]", "실행")
        }
    }
}