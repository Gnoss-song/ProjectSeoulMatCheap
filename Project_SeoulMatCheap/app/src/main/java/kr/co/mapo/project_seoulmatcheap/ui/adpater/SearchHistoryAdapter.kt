package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R

/**
 * Created by SANDY on 2021-04-20
 */
class SearchHistoryAdapter(val list : ArrayList<String>) : RecyclerView.Adapter<SearchHistoryAdapter.HolderView>() {

    inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val remove_btr : ImageButton = itemView.findViewById(R.id.remove_btn)
        val serchword : TextView = itemView.findViewById(R.id.searchWord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_searchhistory, parent, false)
        return HolderView(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        with(holder) {
            serchword.text = list[position]
            remove_btr.setOnClickListener {
                list.removeAt(position)
            }
        }
    }
}