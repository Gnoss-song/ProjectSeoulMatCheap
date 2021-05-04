package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.map.a.f
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
import kr.co.mapo.project_seoulmatcheap.ui.fragment.SEARCH_01_01

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-20
 * @desc 자동완성 리사이클러뷰 어댑터
 */
class AutoCompleteAdapter(
    private val unfilteredlist: ArrayList<String>,
    private val owner : AppCompatActivity
) : RecyclerView.Adapter<AutoCompleteAdapter.ViewHolder>(), Filterable {

    private var filteredList : ArrayList<String> = unfilteredlist
    private var constraint: String = ""

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val word : TextView = itemView.findViewById(R.id.word)
        fun changeTextColor(constraint : String) {
            val start = word.text.indexOf(constraint)
            val end = if (constraint.isNotEmpty()) start + constraint.length else 0
            (word.text as Spannable).apply {
                setSpan(ForegroundColorSpan(owner.getColor(R.color.main)), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
                setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AutoCompleteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item_autocomplete,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutoCompleteAdapter.ViewHolder, position: Int) {
        with(holder) {
            word.text = filteredList[position]
            changeTextColor(constraint)
            itemView.setOnClickListener {
                val edit = owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE).edit()
                edit.putString("word.text", word.text.toString().trim()).apply()
                owner.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SEARCH_01_01.newInstance(owner, word.text.toString()))
                    .commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val word = constraint.toString()
                this@AutoCompleteAdapter.constraint = word
                if(word.isEmpty()) {
                    filteredList = unfilteredlist
                } else {
                    val filteringList = arrayListOf<String>().apply {
                        for (item in unfilteredlist) {
                            if(item.toLowerCase().contains(word.toLowerCase())) this.add(item)
                        }
                    }
                    filteredList = filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }
}