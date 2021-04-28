package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentProvider
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Model
import kr.co.mapo.project_seoulmatcheap.ui.activity.MY_01_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.MainActivity

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-27
 * @desc
 */
class My0102Adapter(private val list: List<Model>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ImageTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
        val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
        var rate : RatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
        val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
        val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
        val delete : Button = itemView.findViewById(R.id.btn_delete)

    }

    inner class ImageType2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
        val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
        var rate : RatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
        val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
        var image2 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV2)

        val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
    }

    inner class ImageType3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
        val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
        var rate : RatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
        val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
        var image2 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV2)
        var image3 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV3)
        val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
    }

    // getItemViewType의 리턴값 Int가 viewType으로 넘어온다.
    // viewType으로 넘어오는 값에 따라 viewHolder를 알맞게 처리해주면 된다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (viewType) {
            Model.IMAGE_TYPE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_review01_01, parent, false)
                ImageTypeViewHolder(view)
            }
            Model.IMAGE_TYPE2 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_review02_01, parent, false)
                ImageType2ViewHolder(view)
            }
            Model.IMAGE_TYPE3 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_review03_01, parent, false)
                ImageType3ViewHolder(view)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        val obj = list[position]

        when (obj.type) {
            Model.IMAGE_TYPE -> {
                (holder as ImageTypeViewHolder).title.text = obj.title
                holder.date.text = obj.date
                holder.rate.rating = obj.ratingBar
                holder.image.setImageResource(obj.IV)
                holder.review.text = obj.review
//                holder.delete.setOnClickListener() {
//
//                    Log.e("여기인가?","여기가에러")
//                    //삭제하기 버튼 클릭시
//                    val mDeleteView =
//                            LayoutInflater.from(MY_01_02()).inflate(R.layout.fragment_dialog_my_delete, null)
//                    Log.e("여기인가?","여기가에러22")
//                    val mBuilder =
//                            androidx.appcompat.app.AlertDialog.Builder(MY_01_02()).setView(mDeleteView)
//                    Log.e("여기인가?","여기가에러33")
//                    val mAlertDialog = mBuilder.show()
//                    mAlertDialog.window?.setLayout(700, 280)
//                    Log.e("여기인가?","여기가에러44")
//
//                    val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
//                    val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
//
//                    okButton.setOnClickListener {
//                        Toast.makeText(MY_01_02(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
//                        mAlertDialog.dismiss()
//                    }
//                    cancelButton.setOnClickListener {
//                        Toast.makeText(MY_01_02(), "취소되었습니다.", Toast.LENGTH_SHORT).show()
//                        mAlertDialog.dismiss()
//                    }
//            }
            }

            Model.IMAGE_TYPE2 -> {

                (holder as ImageType2ViewHolder).title.text = obj.title
                holder.date.text = obj.date
                holder.rate.rating = obj.ratingBar
                holder.image.setImageResource(obj.IV)
                holder.image2.setImageResource(obj.IV2!!)
                holder.review.text = obj.review
            }
            Model.IMAGE_TYPE3 -> {
                (holder as ImageType3ViewHolder).title.text = obj.title
                holder.date.text = obj.date
                holder.rate.rating = obj.ratingBar
                holder.image.setImageResource(obj.IV)
                holder.image2.setImageResource(obj.IV2!!)
                holder.image3.setImageResource(obj.IV3!!)
                holder.review.text = obj.review
            }
        }
    }
    // 여기서 받는 position은 데이터의 index다.
    override fun getItemViewType(position: Int): Int {
        Log.d("MultiViewTypeAdapter", "Hi, getItemViewType")
        return list[position].type
    }

}
