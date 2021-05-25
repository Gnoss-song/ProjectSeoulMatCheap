package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.FavoritEntity
import kr.co.mapo.project_seoulmatcheap.data.db.MenuEntity
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.data.db.repository.StoreRepositoryImpl
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform02Binding
import kr.co.mapo.project_seoulmatcheap.system.APP_NAME
import kr.co.mapo.project_seoulmatcheap.system.STORE
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InformViewPagerAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InfromReviewAdapter

private const val PERMISSION_REQUEST_CODE = 100

class INFORM_02 : AppCompatActivity() {

    private lateinit var binding : ActivityInform02Binding
    private var isLiked = false     //찜표시 저장 Boolean 변수
    private var likeCount : Int = 5
    private var likeCount_f : Int = 5   //처음찜수
    private lateinit var item : StoreEntity
    private val dao = AppDatabase(this)!!.storeDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInform02Binding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        item = intent.getSerializableExtra(STORE) as StoreEntity
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            //title = item.name
            setHomeAsUpIndicator(R.drawable.ic_back_icon)
        }
        setView()
    }

    private fun setView() {
        val viewPagerAdapter = InformViewPagerAdapter(this@INFORM_02)
        viewPagerAdapter.item = item
        with(binding) {
            Glide.with(this@INFORM_02).load(item.photo).into(photo)
            textName.text = item.name
            textRating.text = item.rating_cnt.toString()
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
        dao.isFavorite(item?.id).observe(this, {
            if(it.isNotEmpty()) {
                isLiked = true
                menu?.get(0)?.icon = getDrawable(R.drawable.ic_favorite_on)
            } else {
                isLiked = false
                menu?.get(0)?.icon = getDrawable(R.drawable.ic_favorite_off)
            }
        })
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
                    likeCount --
                    item.icon = getDrawable(R.drawable.ic_favorite_off)
                    isLiked = false
                } else { //찜이 눌리지 않은 상태 : 찜하기
                    likeCount ++
                    item.icon = getDrawable(R.drawable.ic_favorite_on)
                    isLiked = true
                }
                binding.textLikeCnt.text = likeCount.toString()
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

    override fun onStop() {
        super.onStop()
        val repository = StoreRepositoryImpl(dao)
        if (likeCount > likeCount_f) {   //찜 추가
            repository.addFavorite(FavoritEntity(null, item.id, item.name, item.address, item.sort, item.photo, item.lng, item.lat))
            Log.e("[TEST]", "좋아요추가")
        } else if (likeCount < likeCount_f) {
            repository.deleteFavorite(item.id)
            Log.e("[TEST]", "좋아요삭제")
        }
    }
}