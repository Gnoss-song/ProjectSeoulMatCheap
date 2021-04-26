package kr.co.mapo.project_seoulmatcheap.ui.adpater
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */

import android.animation.ValueAnimator
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

//    // Item의 클릭 상태를 저장할 array 객체
//    private val selectedItems = SparseBooleanArray()
//
//    // 직전에 클릭됐던 Item의 position
//    private var prePosition = -1


    inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemView = itemView as LinearLayout


        val my0103title: TextView = itemView.findViewById(R.id.my_01_03_title)
        val my0103sort: TextView = itemView.findViewById(R.id.my_01_03_sort)
        val my0103date: TextView = itemView.findViewById(R.id.my_01_03_date)
        val my0103content: TextView = itemView.findViewById(R.id.my_01_03_content)


//        override fun onClick(v: View) {
//            when (v.id) {
//                R.id.my_01_03_title -> {
//                    if (selectedItems[layoutPosition]) {
//                        // 펼쳐진 Item을 클릭 시
//                        selectedItems.delete(layoutPosition)
//                    } else {
//                        // 직전의 클릭됐던 Item의 클릭상태를 지움
//                        selectedItems.delete(prePosition)
//                        // 클릭한 Item의 position을 저장
//                        selectedItems.put(layoutPosition, true)
//                    }
//                    // 해당 포지션의 변화를 알림
//                    if (prePosition != -1) notifyItemChanged(prePosition)
//                    notifyItemChanged(layoutPosition)
//                    // 클릭된 position 저장
//                    prePosition = layoutPosition
//                }
//            }
//        }
//
//
//        /**
//         * 클릭된 Item의 상태 변경
//         * @param isExpanded Item을 펼칠 것인지 여부
//         */
//        fun changeVisibility(isExpanded: Boolean) {
//            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
//            val height = 200
//
//            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
//            val va =
//                if (isExpanded) ValueAnimator.ofInt(0, height) else ValueAnimator.ofInt(height, 0)
//            // Animation이 실행되는 시간, n/1000초
//            va.duration = 600
//            va.addUpdateListener { animation -> // value는 height 값
//                val value = animation.animatedValue as Int
//                // TextView의 높이 변경
//                my0103content.layoutParams.height = value
//                my0103content.requestLayout()
//                // TextView가 실제로 사라지게하는 부분
//                my0103content.visibility = if (isExpanded) View.VISIBLE else View.GONE
//            }
//            // Animation start
//            va.start()
//        }
//
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
            my0103title.text = itemData.my0103title
            my0103sort.text = itemData.my0103sort
            my0103date.text = itemData.my0103date
            my0103content.text = itemData.my0103content
            my0103content.setOnClickListener{
                my0103content.visibility = View.VISIBLE
            }
//            changeVisibility(selectedItems[position])
        }



    }
    override fun getItemCount() = itemList.size


}