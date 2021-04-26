package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Item
import kr.co.mapo.project_seoulmatcheap.data.MY0103Item
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0103Binding
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InformDetailAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.My0103Adapter
import java.util.*

class MY_01_03 : AppCompatActivity() {
    private lateinit var binding: ActivityMy0103Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMy0103Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemData = mutableListOf<MY0103Item>()
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km"))
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km"))
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km"))
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km"))
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km"))
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km"))
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km"))


        //리사이클러뷰 어댑터 연결
        val adapter2 = My0103Adapter(itemData)

        binding.recycler2.adapter = adapter2

        binding.recycler2.layoutManager = LinearLayoutManager(this)

        //화면 이동 MY_01_01_01

//        binding.btnEdit.setOnClickListener {
//            val intent = Intent(this, MY_01_01_01::class.java)
//            startActivity(intent)
//        }
    }
}
