package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.LinearItem

class LinearRecyclerViewAdapter(private val linearList: MutableList<LinearItem>)
    : RecyclerView.Adapter<LinearRecyclerViewAdapter.LinearHolder>() {

    inner class LinearHolder(rowRoot: View) : RecyclerView.ViewHolder(rowRoot) {
        val linearIV: ImageView = itemView.findViewById(R.id.reviewitem)
        val linearName: TextView = itemView.findViewById(R.id.name)
        val linearAddress: TextView = itemView.findViewById(R.id.address)
        val linearScore: TextView = itemView.findViewById(R.id.score)
        val linearDistance: TextView = itemView.findViewById(R.id.distance)
        val linearSort: TextView = itemView.findViewById(R.id.sort)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearRecyclerViewAdapter.LinearHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inform_01,parent,false)
        return LinearHolder(view)
    }

    override fun getItemCount(): Int {
        return linearList.size
    }

    override fun onBindViewHolder(holder: LinearRecyclerViewAdapter.LinearHolder, position: Int) {
        val linearData = linearList[position]
        with(holder) {
            linearIV.setImageResource(linearData.image)
            linearName.text = linearData.name
            linearAddress.text = linearData.address
            linearScore.text = linearData.kind
            linearDistance.text = String.format("%.1fkm", linearData.distance)
            linearSort.text = linearData.rate.toString()
        }
    }

}