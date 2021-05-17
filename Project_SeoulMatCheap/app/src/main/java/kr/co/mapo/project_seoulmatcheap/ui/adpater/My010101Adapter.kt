package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.app.Activity
import android.content.Intent
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Item
import kr.co.mapo.project_seoulmatcheap.databinding.Inform0101Binding
import kr.co.mapo.project_seoulmatcheap.ui.activity.INFORM_02

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-27
 * @desc
 */

class My010101Adapter (
        private val itemList: MutableList<Item>
) : RecyclerView.Adapter<My010101Adapter.ViewHolderClass>(){

    // CheckBox의 클릭 상태를 저장할 array 객체
    private val checkboxStatus = SparseBooleanArray()


    inner class ViewHolderClass(private val binding : Inform0101Binding) : RecyclerView.ViewHolder(binding.root) {

        val itemView = itemView as LinearLayout

        val marketIV: ImageView = itemView.findViewById(R.id.marketIV)
        val name: TextView = itemView.findViewById(R.id.name)
        val address: TextView = itemView.findViewById(R.id.address)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val score: TextView = itemView.findViewById(R.id.score)
        val sort: TextView = itemView.findViewById(R.id.sort)

        fun bind(itemList:Item) = with(binding){
            checkboxUser.isChecked = checkboxStatus[adapterPosition]

            checkboxUser.setOnClickListener {
                if (!checkboxUser.isChecked)
                    checkboxStatus.put(adapterPosition, false)
                else
                    checkboxStatus.put(adapterPosition, true)
                notifyItemChanged(adapterPosition)

            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolderClass
            = ViewHolderClass(Inform0101Binding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val itemData = itemList[position]
        with(holder){
            marketIV.setImageResource(itemData.marketIV)
            name.text = itemData.name
            address.text = itemData.address
            distance.text = itemData.distance
            score.text = itemData.score
            sort.text = itemData.sort
            bind(itemList[position])
        }

        holder.itemView.setOnClickListener{
            val target = Intent(Activity(), INFORM_02::class.java)
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

