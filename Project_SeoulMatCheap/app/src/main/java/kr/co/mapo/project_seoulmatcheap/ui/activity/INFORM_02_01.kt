package kr.co.mapo.project_seoulmatcheap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.InformItem
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform0201Binding
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InformRecyclerViewAdapter

class INFORM_02_01 : AppCompatActivity() {
    private lateinit var binding: ActivityInform0201Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInform0201Binding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.informRV) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = InformRecyclerViewAdapter(informData())
        }
    }

    private fun informData(): MutableList<InformItem> {
        val informList = mutableListOf<InformItem>()
        return informList.apply {
            add(InformItem(R.drawable.icon_beauty,"2021-05-18","머리감자",3.5f,R.drawable.solip,"머리감다가 먹어봤는데 평범해요"))
            add(InformItem(R.drawable.icon_china,"2021-05-17","중식애호가",4.0f,R.drawable.solip,"띵호와 하오츠~ 하오츠~"))
            add(InformItem(R.drawable.icon_food,"2021-05-16","미식가",3.0f,R.drawable.solip,"냉정하게 3점 드립니다"))
            add(InformItem(R.drawable.icon_japan,"2021-05-15","일식매니아",4.5f,R.drawable.solip,"오이시~ 우마이~"))
            add(InformItem(R.drawable.icon_wash,"2021-05-14","세탁킹",2.5f,R.drawable.solip,"맛은 있는데 위생이 좀 그렇네요. 저는 다신 안갈듯"))
        }
    }
}