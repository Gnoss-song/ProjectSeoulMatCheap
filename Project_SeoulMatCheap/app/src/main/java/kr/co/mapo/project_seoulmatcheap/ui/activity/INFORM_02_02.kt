package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.willy.ratingbar.BaseRatingBar
import com.willy.ratingbar.ScaleRatingBar
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform0202Binding


class INFORM_02_02 : AppCompatActivity() {

    //    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityInform0202Binding
    private val OPEN_GALLERY = 1
    private lateinit var ratingscore: TextView
    private lateinit var ratingBar: ScaleRatingBar
    private var str: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityInform0202Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
<<<<<<< Updated upstream
        recyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager =
            LinearLayoutManager(this@INFORM_02_02, LinearLayoutManager.HORIZONTAL, false)
=======
        // 다중 선택시 리사이클러뷰 코드.
//        recyclerView = findViewById(R.id.recyclerview2)
//        recyclerView.layoutManager = LinearLayoutManager(this@INFORM_02_02,LinearLayoutManager.HORIZONTAL,false)
>>>>>>> Stashed changes

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
                Toast.makeText(this, "리뷰 쓰기 성공", Toast.LENGTH_SHORT).show()

                //화면이동 INFORM_02_01로. 가게이름의 정보를 가진채로.

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
        ratingBar.setOnRatingChangeListener(BaseRatingBar.OnRatingChangeListener() { ScaleRatingBar, rating, _ ->
            str = rating.toString()
            ratingscore.text = str
        })
        binding.imageselectlayout.setOnClickListener {
            openGallery()
        }

//        binding.textView.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                binding.nestedScrollView.requestDisallowInterceptTouchEvent(true)
//            } else {
//
//            }
//            return false
//        }
        binding.textView.setOnTouchListener(OnTouchListener { v, event ->
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

<<<<<<< Updated upstream
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            val list = mutableListOf<Uri>().apply {
                if (requestCode == OPEN_GALLERY) {
                    if (data?.clipData == null) {
                        if (data != null) {
                            this.add(Uri.parse(data.dataString))
                        }
                    } else {
                        for (i in 0 until data.clipData!!.itemCount) {
                            this.add(data.clipData!!.getItemAt(i).uri)
                            if (data.clipData!!.itemCount > 3) {
                                Toast.makeText(
                                    applicationContext,
                                    "사진은 최대 3개까지 가능합니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            binding.scrollView.visibility = View.GONE
                        }
                    }
                }
=======



    }

    private fun openGallery() {
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
>>>>>>> Stashed changes
            }
            startActivityForResult(intent, OPEN_GALLERY)
        }
    }


    // 다중 선택시.
//        fun openGallary() {
//            val writePermission =
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            val readPermission =
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//            if (writePermission == PackageManager.PERMISSION_DENIED
//                || readPermission == PackageManager.PERMISSION_DENIED
//            ) { // 권한 없어서 요청
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                    ), 100
//                )
//            } else { // 권한 있음
//                val intent = Intent(Intent.ACTION_PICK).apply {
//                    type = "image/*"
//                    data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//                }
//                Log.d("TEST","8")
//                startActivityForResult(intent, OPEN_GALLERY)
//                Log.d("TEST","9")
//            }
//        }

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

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {

                var currentImageUrl: Uri? = data?.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    binding.imageView18.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            Log.d("ActivityResult", "something wrong")
        }
    }

}
    // 다중으로 선택시
//        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            super.onActivityResult(requestCode, resultCode, data)
//            val list = mutableListOf<Uri>().apply {
//                if (requestCode == OPEN_GALLERY) {
//                    if (data?.clipData == null) {
//                        if (data != null) {
//                            this.add(Uri.parse(data.dataString))
//                        }
//                    } else {
//                        for (i in 0 until data.clipData!!.itemCount) {
//                            this.add(data.clipData!!.getItemAt(i).uri)
//                            if (data.clipData!!.itemCount > 3) {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "사진은 최대 3개까지 가능합니다.",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                            binding.imageView18.visibility = View.GONE
//                        }
//                    }
//                }
//            }
//            recyclerView.adapter = MultiImageAdapter(list)
//        }
//        inner class MultiImageAdapter(private val list: List<Uri>) :
//            RecyclerView.Adapter<MultiImageAdapter.HolderView>() {
//
//            inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
//                val imageView: ImageView = itemView.findViewById(R.id.reviewitem)
//            }
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
//                Log.d("TEST","20")
//                return HolderView(
//                    LayoutInflater.from(parent.context)
//                        .inflate(R.layout.multi_image_item, parent, false)
//                )
//                Log.d("TEST","21")
//            }
//
//            override fun onBindViewHolder(holder: HolderView, position: Int) {
//                holder.imageView.setImageURI(list[position])
//                Log.d("TEST","22")
//            }
//
//            override fun getItemCount(): Int {
//                if (list.size > 3) {
//                    return 3
//                }
//                return list.size
//            }
//
//
//        }
//    }


