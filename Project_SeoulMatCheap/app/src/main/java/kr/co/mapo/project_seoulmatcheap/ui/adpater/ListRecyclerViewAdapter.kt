package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.ListItem

class ListRecyclerViewAdapter (
    private val listList:MutableList<ListItem>)
    : RecyclerView.Adapter<ListRecyclerViewAdapter.ListHolder>(){

    inner class ListHolder(rowRoot: View) : RecyclerView.ViewHolder(rowRoot) {
        val image : ImageView = rowRoot.findViewById(R.id.marketIV)
        val name: TextView = rowRoot.findViewById(R.id.name)
        val address: TextView = rowRoot.findViewById(R.id.address)
        val sort: TextView = rowRoot.findViewById(R.id.sort)
        val distance: TextView = rowRoot.findViewById(R.id.distance)
        val score: TextView = rowRoot.findViewById(R.id.score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecyclerViewAdapter.ListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inform_01,parent,false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int {
        return listList.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val listData = listList[position]
        with(holder) {
            image.setImageResource(listData.linearIV)
            name.text = listData.name
            address.text = listData.address
            sort.text = listData.sort
            distance.text = listData.distance
            score.text = listData.score
        }
    }
}