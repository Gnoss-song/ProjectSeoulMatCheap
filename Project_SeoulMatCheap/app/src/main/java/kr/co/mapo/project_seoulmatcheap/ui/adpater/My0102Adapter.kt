package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Model
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.system.KEY
import kr.co.mapo.project_seoulmatcheap.system.STORE
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02_02_01
import java.io.Serializable

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-21
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
        val title: TextView = itemView.findViewById(R.id.informName)
        val date : TextView = itemView.findViewById(R.id.informDate)
        var rate : ScaleRatingBar = itemView.findViewById(R.id.informRatingBar)
        val image : ImageView = itemView.findViewById(R.id.informImage)
        val review : TextView = itemView.findViewById(R.id.informText)
        val delete : TextView = itemView.findViewById(R.id.btn_delete)
        val modify : TextView = itemView.findViewById(R.id.btn_modify)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (viewType) {
            Model.IMAGE_TYPE -> {
                Log.e("TEST", "?")
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_review01, parent, false)
                ImageTypeViewHolder(view)
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
                    val mDeleteView = LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_delete, null)
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
                    val target= Intent(owner, INFORM_02_02_01::class.java)
                    target.putExtra(KEY,obj as Serializable)
                    owner.startActivity(target)
                }
            }
        }
        holder.itemView.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val list = AppDatabase(owner)!!.storeDAO().getStoreDetailName(obj.title)
                val intent = Intent(owner, INFORM_02::class.java)
                if(list.isNotEmpty()) {
                    intent.putExtra(STORE, list[0])
                }
                owner.startActivity(intent)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }
}