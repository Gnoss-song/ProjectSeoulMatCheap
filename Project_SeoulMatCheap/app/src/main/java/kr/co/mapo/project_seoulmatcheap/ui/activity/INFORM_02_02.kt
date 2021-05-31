package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.willy.ratingbar.BaseRatingBar
import com.willy.ratingbar.ScaleRatingBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.MatCheapService
import kr.co.mapo.project_seoulmatcheap.data.ReviewBody
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform020201Binding
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform0202Binding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.Multipart
import java.io.File

class INFORM_02_02 : AppCompatActivity() {

    private lateinit var binding: ActivityInform0202Binding
    private val OPEN_GALLERY = 1
    private lateinit var ratingscore: TextView
    private lateinit var ratingBar: ScaleRatingBar
    private var str: String? = null
    private var currentImageURI : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityInform0202Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.btnComplete.setOnClickListener {
            val mLogoutView =
                LayoutInflater.from(this).inflate(R.layout.fragment_dialog_inform_complete, null)
            val mBuilder = androidx.appcompat.app.AlertDialog.Builder(this).setView(mLogoutView)
            val mAlertDialog = mBuilder.show().apply {
                window?.setBackgroundDrawable(null)
            }
            val okButton = mLogoutView.findViewById<Button>(R.id.btn_complete_ok)
            val cancelButton = mLogoutView.findViewById<Button>(R.id.btn_complete_no)


            okButton.setOnClickListener {
                val file = File(currentImageURI)
                val input_json = JSONObject()
                input_json.put("memberId", 28)
                input_json.put("storeId", 9220)
                input_json.put("reviewContent", "되었으면 좋겟다아아")
                input_json.put("reviewWriter", "test 입력자")
                input_json.put("reviewModifier", "test 수정자")
                //화면이동 INFORM_02_01로. 가게이름의 정보를 가진채로.
                val input_body = RequestBody.create(MediaType.parse("text/plain"), input_json.toString())
                val files_body = RequestBody.create(MediaType.parse("image/*"), file)
                val files =
                    MultipartBody.Part.createFormData("files", file.name, files_body)
                val input = MultipartBody.Part.createFormData("input", input_json.toString())
                MatCheapService.invoke(this).writeReview(
                    input, files
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(this, "리뷰 작성 성공", Toast.LENGTH_SHORT).show()
                        Log.e("[결과]", "성공")
                    },{
                        Log.e("[결과]", "실패")
                    })
                mAlertDialog.dismiss()
            }
            cancelButton.setOnClickListener {
                Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                mAlertDialog.dismiss()
            }
        }

        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            setTitle(R.string.review_title)
        }
        ratingBar = findViewById(R.id.ratingBar)
        ratingscore = findViewById(R.id.ratingscore)
        ratingBar.setOnRatingChangeListener { ScaleRatingBar, rating, _ ->
            str = rating.toString()
            ratingscore.text = str
        }

        binding.imageselectlayout.setOnClickListener {
            openGallery()
        }

        binding.textView.setOnTouchListener(View.OnTouchListener { v, event ->
            if (binding.textView.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })
    }

    private fun openGallery(){
        val writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (writePermission == PackageManager.PERMISSION_DENIED
            || readPermission == PackageManager.PERMISSION_DENIED
        ) { // 권한 없어서 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 100
            )
        } else { // 권한 있음
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            startActivityForResult(intent, OPEN_GALLERY)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == OPEN_GALLERY){
                currentImageURI = data?.data.toString()
                try {
                    binding.imageView18.setImageURI(data?.data)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }else{
            Log.d("ActivityResult","something wrong")
        }
    }

}


