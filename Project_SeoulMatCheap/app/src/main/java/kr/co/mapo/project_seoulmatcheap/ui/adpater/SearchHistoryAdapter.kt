package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
import kr.co.mapo.project_seoulmatcheap.ui.fragment.SEARCH_01_01

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-20
 * @desc 검색어히스토리 관리 리사이클러뷰 어댑터
 */
class SearchHistoryAdapter(
    val list : MutableList<*>,
    val owner: AppCompatActivity
    ) : RecyclerView.Adapter<SearchHistoryAdapter.HolderView>() {

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
        val item = list[position].toString()
        with(holder) {
            serchword.text = item
            remove_btr.setOnClickListener {
                owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
                    .edit()
                    .remove(item)
                    .apply()
                list.removeAt(position)
                notifyDataSetChanged()
            }
            itemView.setOnClickListener {
                val edit = owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE).edit()
                edit.putString("word.text", serchword.text.toString().trim())
                owner.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SEARCH_01_01.newInstance(owner, serchword.text.toString()))
                    .commit()
            }
        }
    }
}