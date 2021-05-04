package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.GridItem
import kr.co.mapo.project_seoulmatcheap.ui.activity.CATEGORY_01_01

class GridRecyclerViewAdapter(
    private val gridList: MutableList<GridItem>)
    : RecyclerView.Adapter<GridRecyclerViewAdapter.GridHolder>(){

    inner class GridHolder(rowRoot: View) : RecyclerView.ViewHolder(rowRoot) {
        val gridIV : ImageView = rowRoot.findViewById(R.id.iv_grid_image)
        val gridTV : TextView = rowRoot.findViewById(R.id.tv_grid_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): GridRecyclerViewAdapter.GridHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_grid,parent,false)
        return GridHolder(view)
    }

    override fun getItemCount(): Int {
        return gridList.size
    }

    override fun onBindViewHolder(holder: GridRecyclerViewAdapter.GridHolder, position: Int) {
        val gridData = gridList[position]
        with(holder) {
            gridIV.setImageResource(gridData.gridimage)
            gridTV.text = gridData.gridtitle
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, CATEGORY_01_01::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }
}
