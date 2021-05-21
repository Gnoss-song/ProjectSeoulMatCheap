package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.NotifyService
import kr.co.mapo.project_seoulmatcheap.data.response.NotifyResponse
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0103Binding

class MY_01_03 : AppCompatActivity() {
    private lateinit var binding: ActivityMy0103Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMy0103Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler2)
        //백버튼
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            setTitle(R.string.notice_title)
        }

        NotifyService.invoke()
            .getTest().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                recyclerView.adapter = My0103Adapter(it.data)
                Log.e("[TEST]", "${My0103Adapter(it.data).itemCount}")
                // 요청성공 = onResponse
            }, {
                // 요청실패 = onFailure
            })
        //        //데이터 테스트
//        val itemData = mutableListOf<MY0103Item>()
//        with(itemData){
//            add(MY0103Item("04.21 안내", getText(R.string.notice).toString(), "2021-04-21", getText(R.string.notice1).toString()))
//            add(MY0103Item("04.14 안내", getText(R.string.notice).toString(), "2021-04-14", getText(R.string.notice2).toString()))
//            add(MY0103Item("04.01 업데이트", getText(R.string.update).toString(), "2021-04-01", getText(R.string.notice3).toString()))
//            add(MY0103Item("03.10 안내",  getText(R.string.notice).toString(), "2021-03-10",getText(R.string.notice4).toString() ))
//            add(MY0103Item("02.24 업데이트", getText(R.string.update).toString(), "2021-02-24", getText(R.string.notice5).toString()))
//            add(MY0103Item("01.27 업데이트", getText(R.string.update).toString(), "2021-01-27", getText(R.string.notice6).toString()))
//            add(MY0103Item("01.20 업데이트", getText(R.string.update).toString(), "2021-01-20", getText(R.string.notice7).toString()))
//            add(MY0103Item("01.13 안내",  getText(R.string.notice).toString(), "2021-01-13", getText(R.string.notice8).toString()))
//            add(MY0103Item("01.06 업데이트", getText(R.string.update).toString(), "2021-01-06", getText(R.string.notice9).toString()))
//            add(MY0103Item("10.23 업데이트", getText(R.string.update).toString(), "2020-10-23", getText(R.string.notice10).toString()))
//            add(MY0103Item("10.14 업데이트", getText(R.string.update).toString(), "2020-10-14", getText(R.string.notice11).toString()))
//            add(MY0103Item("09.16 안내",  getText(R.string.notice).toString(), "2020-09-16", getText(R.string.notice12).toString()))
//            add(MY0103Item("08.15 업데이트", getText(R.string.update).toString(), "2020-08-15", getText(R.string.notice13).toString()))
//            add(MY0103Item("08.14 안내",  getText(R.string.notice).toString(), "2020-08-14",getText(R.string.notice14).toString()))
//        }
//        //리사이클러뷰 어댑터 연결
//        val adapter2 = My0103Adapter(itemData)
//        binding.recycler2.adapter = adapter2
//        binding.recycler2.layoutManager = LinearLayoutManager(this)
//    }
        //백버튼 활성화
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //어댑터
    inner class My0103Adapter(
        private val list: List<NotifyResponse.Data>
    ) : RecyclerView.Adapter<My0103Adapter.ViewHolderClass>() {
        private val selectedItems = SparseBooleanArray()
        private var prePosition = -1
        inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val my0103title: TextView = itemView.findViewById(R.id.my_01_03_title)
            val my0103sort: TextView = itemView.findViewById(R.id.my_01_03_sort)
            val my0103date: TextView = itemView.findViewById(R.id.my_01_03_date)
            val my0103content: TextView = itemView.findViewById(R.id.my_01_03_content)
            val button_before : ImageView = itemView.findViewById(R.id.button_before)
            val button_after : ImageView = itemView.findViewById(R.id.button_after)
            fun init() {
                my0103title.setOnClickListener(this)
                my0103date.setOnClickListener(this)
                my0103sort.setOnClickListener(this)
                button_before.setOnClickListener(this)
                button_after.setOnClickListener(this)
            }
            fun setview(item : NotifyResponse.Data){
                my0103date.text= item.modifyDate
                my0103title.text= item.noticeTitle
                my0103content.text= item.noticeContent
                my0103sort.text=item.modifyDate
            }
            override fun onClick(v: View) {
                when (v.id) {
                    R.id.my_01_03_title -> {
                        if (selectedItems[layoutPosition]) {
                            selectedItems.delete(layoutPosition)
                        } else {
                            selectedItems.delete(prePosition)
                            selectedItems.put(layoutPosition, true)
                        }
                        if (prePosition != -1) notifyItemChanged(prePosition)
                        notifyItemChanged(layoutPosition)
                        prePosition = layoutPosition
                    }
                }
            }
            //공지사항 접고 펴는곳
            fun changeVisibility(isExpanded: Boolean) {
                val height = ViewGroup.LayoutParams.WRAP_CONTENT
                val va =
                    if (isExpanded) ValueAnimator.ofInt(0, height) else ValueAnimator.ofInt(height, 0)
                va.duration = 600
                va.addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    my0103content.layoutParams.height = value
                    my0103content.requestLayout()
                    my0103content.visibility = if (isExpanded) View.VISIBLE else View.GONE
                }
                va.start()
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_my_01_03, parent,false)
            val holder = ViewHolderClass(view)
            holder.itemView.setOnClickListener {
            }
            return ViewHolderClass(view)
        }
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
//            val itemData = list[position]
            holder.setview(list[position])
            with(holder) {
                init()
//                my0103title.text = itemData.noticeTitle
//                my0103sort.text = "dd"
//                my0103date.text = itemData.modifyDate
//                my0103content.text = itemData.noticeContent
                changeVisibility(selectedItems[position])
            }

        }
        override fun getItemCount():Int{
            return list.size
        }

    }

}
