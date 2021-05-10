package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform0202Binding
import kr.co.mapo.project_seoulmatcheap.ui.adpater.MultiImageAdapter
import java.lang.Exception

class INFORM_02_02 : AppCompatActivity() {
    private lateinit var binding: ActivityInform0202Binding
    private lateinit var recyclerview2: RecyclerView
    private lateinit var adapter: MultiImageAdapter


    private val OPEN_GALLERY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInform0202Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegistercam.setOnClickListener {
            openGallery()
        }
        private fun openGallery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(Intent.createChooser(intent,"Select Pictures"),1)
            startActivityForResult(intent, OPEN_GALLERY)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (data == null) { // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(applicationContext, "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG)
                    .show()
            } else { // 이미지를 하나라도 선택한 경우
                if (data.clipData == null) { // 이미지를 하나만 선택한 경우
                    Log.e("single choice: ", String.valueOf(data.data))
                    val imageUri = data.data
                    uriList.add(imageUri)
                    adapter = MultiImageAdapter(uriList, applicationContext)
                    recyclerview2.adapter = adapter
                    recyclerview2.layoutManager = LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        true
                    )
                } else { // 이미지를 여러장 선택한 경우
                    val clipData = data.clipData
                    Log.e("clipData", String.valueOf(clipData.getItemCount()))
                    if (clipData.getItemCount() > 10) { // 선택한 이미지가 11장 이상인 경우
                        Toast.makeText(
                            applicationContext,
                            "사진은 10장까지 선택 가능��니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else { // 선택한 이미지가 1장 이상 10장 이하인 경우
                        Log.e(TAG, "multiple choice")
                        for (i in 0 until clipData.getItemCount()) {
                            val imageUri = clipData.getItemAt(i).uri // 선택한 이미지들의 uri를 가져온다.
                            try {
                                uriList.add(imageUri) //uri를 list에 담는다.
                            } catch (e: Exception) {
                                Log.e("TAG", "File select error", e)
                            }
                        }
                        adapter = MultiImageAdapter(uriList, applicationContext)
                        recyclerview2.adapter = adapter // 리사이클러뷰에 어댑터 세팅
                        recyclerview2.layoutManager = LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL,
                            true
                        ) // 리사이클러뷰 수평 스크롤 적용
                    }
                }
            }
        }
        companion object {
            private val TAG = "MultiImageActivity"
        }
    }
}