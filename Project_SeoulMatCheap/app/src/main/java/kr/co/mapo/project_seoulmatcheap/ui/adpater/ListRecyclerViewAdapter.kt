package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.ListItem
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.system.STORE
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02

class ListRecyclerViewAdapter (
    private val listList: List<StoreEntity>,
    private val owner : AppCompatActivity) : RecyclerView.Adapter<ListRecyclerViewAdapter.ListHolder>(){

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
            Glide.with(owner).load(listData.photo)
                .placeholder(R.drawable.inform_image)
                .error(R.drawable.inform_image)
                .into(image)
            name.text = listData.name
            address.text = listData.address
            sort.text = listData.category
            distance.text = SeoulMatCheap.getInstance().calculateDistance(listData.lat, listData.lng)
            score.text = "${listData.score}"
            itemView.setOnClickListener {
                val intent = Intent(owner, INFORM_02::class.java)
                intent.putExtra(STORE, listData)
                owner.startActivity(intent)
            }
        }
    }
}