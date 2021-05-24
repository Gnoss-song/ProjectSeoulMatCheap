package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.Test

/**
 * Created by SANDY on 2021-04-20
 */
class InformListAdapter(
    private val list: ArrayList<Test>,
    private val context: Context ) : RecyclerView.Adapter<InformListAdapter.HolderView>() {

    inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView : ImageView = itemView.findViewById(R.id.marketIV)
        val name : TextView = itemView.findViewById(R.id.name)
        val address : TextView = itemView.findViewById(R.id.address)
        val sort : TextView = itemView.findViewById(R.id.sort)
        val distance : TextView = itemView.findViewById(R.id.distance)
        val score : TextView = itemView.findViewById(R.id.score)

        fun setData(item : Test) {
            Glide.with(itemView).load(item.image).into(imageView)
            name.text = item.name
            address.text = item.address
            sort.text = item.kind
            distance.text = String.format("%.1fkm", item.distance)
            score.text = item.rate.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inform_01, parent, false)
        return HolderView(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        holder.setData(list[position])
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, INFORM_02::class.java))
        }
    }
}