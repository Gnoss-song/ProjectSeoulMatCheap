package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
import kr.co.mapo.project_seoulmatcheap.system.SearchHistoryPrefs
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.fragment.SEARCH_01_01
import kr.co.mapo.project_seoulmatcheap.ui.fragment.SEARCH_01_02

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-20
 * @desc 검색어히스토리 관리 리사이클러뷰 어댑터
 */
class SearchHistoryAdapter(
    private val list : MutableList<*>,
    private val owner: AppCompatActivity
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
                SearchHistoryPrefs.removeSearchWord(owner, item)
                list.removeAt(position)
                notifyDataSetChanged()
            }
            itemView.setOnClickListener {
                //검색요청
                val word = serchword.text.toString().trim()
                SeoulMatCheap.getInstance().showToast(owner, "${word}(을)를 검색합니다.")
                SearchHistoryPrefs.saveSearchWord(owner, word)
                GlobalScope.launch(Dispatchers.IO) {
                    val list = AppDatabase(owner)!!.storeDAO().searchStore("%$word%")
                    if(list.isNotEmpty()) { //검색성공
                        owner.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, SEARCH_01_01.newInstance(owner, word, list))
                            .commit()
                    } else {    //검색실패
                        owner.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, SEARCH_01_02.newInstance(owner, word))
                            .commit()
                    }
                }
                val inputManager = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(owner.currentFocus?.windowToken, 0)
            }
        }
    }
}