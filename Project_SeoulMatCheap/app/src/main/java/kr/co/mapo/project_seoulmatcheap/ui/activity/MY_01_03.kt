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
        itemData.add(MY0103Item("솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent"))
        itemData.add(MY0103Item("솔잎식당2", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent2"))
        itemData.add(MY0103Item("솔잎식당3", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent3"))
        itemData.add(MY0103Item("솔잎식당4", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent4"))
        itemData.add(MY0103Item("솔잎식당5", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent5"))
        itemData.add(MY0103Item("솔잎식당6", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent6"))
        itemData.add(MY0103Item("솔잎식당7", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent7"))
        itemData.add(MY0103Item("솔잎식당8", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent8"))
        itemData.add(MY0103Item("솔잎식당9", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent9"))
        itemData.add(MY0103Item("솔잎식당10", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent10"))
        itemData.add(MY0103Item("솔잎식당11", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent11"))
        itemData.add(MY0103Item("솔잎식당12", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent12"))
        itemData.add(MY0103Item("솔잎식당13", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent13"))
        itemData.add(MY0103Item("솔잎식당14", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1kmcontent14"))



        //리사이클러뷰 어댑터 연결
        val adapter2 = My0103Adapter(itemData)

        binding.recycler2.adapter = adapter2

        binding.recycler2.layoutManager = LinearLayoutManager(this)



    }
}
