package kr.co.mapo.project_seoulmatcheap.ui.adpater

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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.map.a.f
import kr.co.mapo.project_seoulmatcheap.R

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-06
 * @desc
 */
class AutoCompleteAdapter(
    val unfilteredlist: ArrayList<String>,
    val context: Context
) : RecyclerView.Adapter<AutoCompleteAdapter.ViewHolder>(), Filterable {

    private var filteredList : ArrayList<String> = unfilteredlist
    private lateinit var holderView : ViewHolder

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val word : TextView = itemView.findViewById(R.id.word)
        fun changeTextColor(constraint : String) {
            val start = word.text.indexOf(constraint)
            val end = if (start > 0) constraint.length-1 else 0
            val span = word.text as Spannable
            span.setSpan(ForegroundColorSpan(context.getColor(R.color.colorPrimary)), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            span.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
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
        Log.e("[흠]", "1")
        holderView = ViewHolder(view)
        return holderView
    }

    override fun onBindViewHolder(holder: AutoCompleteAdapter.ViewHolder, position: Int) {
        holder.word.text = filteredList[position]
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Log.e("[흠]", "2")
                val word = constraint.toString()
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
                Log.e("[흠]", "3")
                filteredList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }
}