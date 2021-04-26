package kr.co.mapo.project_seoulmatcheap.ui.adpater
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Item
import kr.co.mapo.project_seoulmatcheap.data.MY0103Item
import kr.co.mapo.project_seoulmatcheap.ui.fragment.INFORM_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.MY_01_01

class My0103Adapter (
    private val itemList: MutableList<MY0103Item>
    ) : RecyclerView.Adapter<My0103Adapter.ViewHolderClass>() {


    inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemView = itemView as LinearLayout


        val my0103title: TextView = itemView.findViewById(R.id.my_01_03_title)
        val my0103sort: TextView = itemView.findViewById(R.id.my_01_03_sort)
        val my0103date: TextView = itemView.findViewById(R.id.my_01_03_date)
        val my0103content: TextView = itemView.findViewById(R.id.my_01_03_content)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_my_01_03, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val itemData = itemList[position]
        with(holder) {
            my0103title.text = itemData.my0103title
            my0103sort.text = itemData.my0103sort
            my0103date.text = itemData.my0103date
            my0103content.text = itemData.my0103content
        }

//        holder.itemView.setOnClickListener{
//            //클릭시 이벤트 적기.
//
//
//        }
    }
    override fun getItemCount() = itemList.size


}