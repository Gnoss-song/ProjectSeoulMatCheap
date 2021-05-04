package kr.co.mapo.project_seoulmatcheap.ui.adpater
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Item
import kr.co.mapo.project_seoulmatcheap.ui.fragment.INFORM_02
import kr.co.mapo.project_seoulmatcheap.ui.activity.MY_01_01

class InformDetailAdapter (
    private val itemList: MutableList<Item>
    ) : RecyclerView.Adapter<InformDetailAdapter.ViewHolderClass>(){

    inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val marketIV: ImageView = itemView.findViewById(R.id.marketIV)
        val name: TextView = itemView.findViewById(R.id.name)
        val address: TextView = itemView.findViewById(R.id.address)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val score: TextView = itemView.findViewById(R.id.score)
        val sort: TextView = itemView.findViewById(R.id.sort)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.inform_01,parent,false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val itemData = itemList[position]
        with(holder){
            marketIV.setImageResource(itemData.marketIV)
            name.text = itemData.name
            address.text = itemData.address
            distance.text = itemData.distance
            score.text = itemData.score
            sort.text = itemData.sort
        }
        holder.itemView.setOnClickListener{
            val target = Intent(Activity(),INFORM_02::class.java)
            target.putExtra("marketIV",itemData.marketIV)
            target.putExtra("name",itemData.name)
            target.putExtra("address",itemData.address)
            target.putExtra("distance",itemData.distance)
            target.putExtra("score",itemData.score)
            target.putExtra("sort",itemData.sort)
        }
    }
    override fun getItemCount() = itemList.size
}

