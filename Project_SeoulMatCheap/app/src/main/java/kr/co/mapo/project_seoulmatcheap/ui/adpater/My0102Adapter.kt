package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Model
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02_02_01
import kr.co.mapo.project_seoulmatcheap.ui.activity.MY_01_02

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-27
 * @desc
 */
class My0102Adapter(
        private val list: MutableList<Model>,
        private val owner : Activity
        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    //아이템 제거
    private fun removeItem (position: Int){
        if(position>=0) {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    inner class ImageTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
        val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
        var rate : RatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
        val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
        val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
        val delete : Button = itemView.findViewById(R.id.btn_delete)
        val modify : Button = itemView.findViewById(R.id.btn_modify)
    }

    inner class ImageType2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
        val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
        var rate : RatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
        val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
        var image2 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV2)
        val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
        val delete : Button = itemView.findViewById(R.id.btn_delete)
        val modify : Button = itemView.findViewById(R.id.btn_modify)
    }

    inner class ImageType3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
        val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
        var rate : RatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
        val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
        var image2 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV2)
        var image3 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV3)
        val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
        val delete : Button = itemView.findViewById(R.id.btn_delete)
        val modify : Button = itemView.findViewById(R.id.btn_modify)
    }

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
                holder.delete.setOnClickListener {
                    val mDeleteView =LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_delete, null)
                    val mBuilder = androidx.appcompat.app.AlertDialog.Builder(owner).setView(mDeleteView)
                    val mAlertDialog = mBuilder.show().apply {
                        window?.setBackgroundDrawable(null)
                    }
                    val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
                    val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
                    okButton.setOnClickListener {
                        Toast.makeText(owner, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                        removeItem(position)
                    }
                    cancelButton.setOnClickListener {
                        Toast.makeText(owner, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                    }
                }
                holder.modify.setOnClickListener {
                    val target= Intent(owner,INFORM_02_02_01::class.java)
                    target.putExtra("title",obj.title)
                    target.putExtra("date",obj.date)
                    target.putExtra("rate",obj.ratingBar)
                    target.putExtra("image",obj.IV)
                    target.putExtra("review",obj.review)
                    owner.startActivity(target)
                }
            }
            Model.IMAGE_TYPE2 -> {
                (holder as ImageType2ViewHolder).title.text = obj.title
                holder.date.text = obj.date
                holder.rate.rating = obj.ratingBar
                holder.image.setImageResource(obj.IV)
                holder.image2.setImageResource(obj.IV2!!)
                holder.review.text = obj.review
                holder.delete.setOnClickListener {
                    val mDeleteView =
                            LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_delete, null)
                    val mBuilder =
                            androidx.appcompat.app.AlertDialog.Builder(owner).setView(mDeleteView)
                    val mAlertDialog = mBuilder.show()
                    mAlertDialog.window?.setBackgroundDrawable(null)
                    val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
                    val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
                    okButton.setOnClickListener {
                        Toast.makeText(owner, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                        removeItem(position)
                    }
                    cancelButton.setOnClickListener {
                        Toast.makeText(owner, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                    }
                }
                holder.modify.setOnClickListener {
                    val target= Intent(owner,INFORM_02_02_01::class.java)
                    target.putExtra("title",obj.title)
                    target.putExtra("date",obj.date)
                    target.putExtra("rate",obj.ratingBar)
                    target.putExtra("image",obj.IV)
                    target.putExtra("image2",obj.IV2)
                    target.putExtra("review",obj.review)
                    owner.startActivity(target)
                }
            }
            Model.IMAGE_TYPE3 -> {
                (holder as ImageType3ViewHolder).title.text = obj.title
                holder.date.text = obj.date
                holder.rate.rating = obj.ratingBar
                holder.image.setImageResource(obj.IV)
                holder.image2.setImageResource(obj.IV2!!)
                holder.image3.setImageResource(obj.IV3!!)
                holder.review.text = obj.review
                holder.delete.setOnClickListener() {
                    val mDeleteView =
                            LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_delete, null)
                    val mBuilder =
                            androidx.appcompat.app.AlertDialog.Builder(owner).setView(mDeleteView)
                    val mAlertDialog = mBuilder.show()
                    mAlertDialog.window?.setBackgroundDrawable(null)
                    val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
                    val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
                    okButton.setOnClickListener {
                        Toast.makeText(owner, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                        removeItem(position)
                    }
                    cancelButton.setOnClickListener {
                        Toast.makeText(owner, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                    }
                }
                holder.modify.setOnClickListener {
                    val target= Intent(owner,INFORM_02_02_01::class.java)
                    target.putExtra("title",obj.title)
                    target.putExtra("date",obj.date)
                    target.putExtra("rate",obj.ratingBar)
                    target.putExtra("image",obj.IV)
                    target.putExtra("image2",obj.IV2)
                    target.putExtra("image3",obj.IV3)
                    target.putExtra("review",obj.review)
                    owner.startActivity(target)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("MultiViewTypeAdapter", "Hi, getItemViewType")
        return list[position].type
    }

}
