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
        itemData.add(MY0103Item("04.21 안내", getText(R.string.notice).toString(), "2021-04-21", getText(R.string.notice1).toString()))
        itemData.add(MY0103Item("04.14 안내", getText(R.string.notice).toString(), "2021-04-14", getText(R.string.notice2).toString()))
        itemData.add(MY0103Item("04.01 업데이트", getText(R.string.update).toString(), "2021-04-01", getText(R.string.notice3).toString()))
        itemData.add(MY0103Item("03.10 안내",  getText(R.string.notice).toString(), "2021-03-10",getText(R.string.notice4).toString() ))
        itemData.add(MY0103Item("02.24 업데이트", getText(R.string.update).toString(), "2021-02-24", getText(R.string.notice5).toString()))
        itemData.add(MY0103Item("01.27 업데이트", getText(R.string.update).toString(), "2021-01-27", getText(R.string.notice6).toString()))
        itemData.add(MY0103Item("01.20 업데이트", getText(R.string.update).toString(), "2021-01-20", getText(R.string.notice7).toString()))
        itemData.add(MY0103Item("01.13 안내",  getText(R.string.notice).toString(), "2021-01-13", getText(R.string.notice8).toString()))
        itemData.add(MY0103Item("01.06 업데이트", getText(R.string.update).toString(), "2021-01-06", getText(R.string.notice9).toString()))
        itemData.add(MY0103Item("10.23 업데이트", getText(R.string.update).toString(), "2020-10-23", getText(R.string.notice10).toString()))
        itemData.add(MY0103Item("10.14 업데이트", getText(R.string.update).toString(), "2020-10-14", getText(R.string.notice11).toString()))
        itemData.add(MY0103Item("09.16 안내",  getText(R.string.notice).toString(), "2020-09-16", getText(R.string.notice12).toString()))
        itemData.add(MY0103Item("08.15 업데이트", getText(R.string.update).toString(), "2020-08-15", getText(R.string.notice13).toString()))
        itemData.add(MY0103Item("08.14 안내",  getText(R.string.notice).toString(), "2020-08-14",getText(R.string.notice14).toString()))




        //리사이클러뷰 어댑터 연결
        val adapter2 = My0103Adapter(itemData)

        binding.recycler2.adapter = adapter2

        binding.recycler2.layoutManager = LinearLayoutManager(this)



    }
}
