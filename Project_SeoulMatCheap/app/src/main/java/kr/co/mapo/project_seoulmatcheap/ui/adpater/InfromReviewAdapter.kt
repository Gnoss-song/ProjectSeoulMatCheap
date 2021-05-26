package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willy.ratingbar.ScaleRatingBar
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.Review

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-16
 * @desc
 */
class InfromReviewAdapter(val list : List<Review>, val owner : AppCompatActivity) : RecyclerView.Adapter<InfromReviewAdapter.HolderView>() {
    inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.photo)
        val content = itemView.findViewById<TextView>(R.id.content)
       // val ratingBar : ScaleRatingBar = itemView.findViewById(R.id.ratingBar)

        fun set(item : Review) {
            imageView.setImageResource(item.photo)
            content.setText(item.content)
           // ratingBar.rating = item.rating
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        return HolderView(LayoutInflater.from(parent.context).inflate(R.layout.recycler_inform_review, parent, false))
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        holder.set(list[position])
    }

    override fun getItemCount(): Int {
        return 3
    }
}
