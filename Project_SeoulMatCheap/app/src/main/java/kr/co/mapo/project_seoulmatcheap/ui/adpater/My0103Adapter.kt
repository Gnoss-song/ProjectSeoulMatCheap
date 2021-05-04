package kr.co.mapo.project_seoulmatcheap.ui.adpater
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */

import android.animation.ValueAnimator
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.MY0103Item


class My0103Adapter(
    private val itemList: MutableList<MY0103Item>
) : RecyclerView.Adapter<My0103Adapter.ViewHolderClass>() {
   private val selectedItems = SparseBooleanArray()
   private var prePosition = -1
    inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val itemView = itemView as LinearLayout
        val my0103title: TextView = itemView.findViewById(R.id.my_01_03_title)
        val my0103sort: TextView = itemView.findViewById(R.id.my_01_03_sort)
        val my0103date: TextView = itemView.findViewById(R.id.my_01_03_date)
        val my0103content: TextView = itemView.findViewById(R.id.my_01_03_content)

        fun init() {
            my0103title.setOnClickListener(this)
            my0103date.setOnClickListener(this)
            my0103sort.setOnClickListener(this)
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
        val itemData = itemList[position]
        with(holder) {
            init()
            my0103title.text = itemData.my0103title
            my0103sort.text = itemData.my0103sort
            my0103date.text = itemData.my0103date
            my0103content.text = itemData.my0103content
            changeVisibility(selectedItems[position])
        }
    }

    override fun getItemCount() = itemList.size
}