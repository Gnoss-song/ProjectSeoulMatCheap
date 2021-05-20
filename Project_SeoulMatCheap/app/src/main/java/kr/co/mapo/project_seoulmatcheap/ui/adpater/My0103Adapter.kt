package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.animation.ValueAnimator
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.response.NotifyResponse
import kr.co.mapo.project_seoulmatcheap.ui.activity.MY_01_03

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-20
 * @desc
 */
class My0103Adapter (
    private val list: List<NotifyResponse.Data>) : RecyclerView.Adapter<My0103Adapter.ViewHolderClass>() {
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
                R.id.button_before -> {
                    button_before.visibility = View.GONE
                    button_after.visibility = View.VISIBLE
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
                R.id.button_after -> {
                    button_before.visibility = View.VISIBLE
                    button_after.visibility = View.GONE
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