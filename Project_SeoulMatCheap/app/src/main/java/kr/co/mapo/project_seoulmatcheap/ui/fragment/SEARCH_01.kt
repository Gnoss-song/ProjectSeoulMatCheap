package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentSearch01Binding
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
import kr.co.mapo.project_seoulmatcheap.system.SearchHistoryPrefs
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.AutoCompleteAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.SearchHistoryAdapter

open class SEARCH_01(
    private val owner: AppCompatActivity
    ) : Fragment() {

    companion object {
        fun newInstance(owner: AppCompatActivity) : Fragment {
            return SEARCH_01(owner)
        }
    }

    private lateinit var binding : FragmentSearch01Binding
    private lateinit var preferences : SharedPreferences
    private lateinit var filterAdapter : AutoCompleteAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearch01Binding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun init() {
        preferences = owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
        filterAdapter = AutoCompleteAdapter(SeoulMatCheap.getInstance().filterList, owner)
        searchHistoryAdapter = SearchHistoryAdapter(SearchHistoryPrefs.getSearchHistory(owner), owner)
    }

    private fun setView() {
        with(binding) {
            toolbar.apply {
                setOnMenuItemClickListener {
                    if(it.itemId == R.id.search) {
                        if (searchEditText.text?.isNotEmpty() == true) goSearch(searchEditText.text.toString().trim())
                        else SeoulMatCheap.getInstance().showToast(owner, "검색어를 입력해주세요")
                    }
                    return@setOnMenuItemClickListener true
                }
            }
            recyclerView.apply {
                layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
                adapter = searchHistoryAdapter
                setOnTouchListener { v, event ->
                    val imm = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(owner.currentFocus?.windowToken, 0)
                }
            }
            searchEditText.apply {
                //텍스트입력 이벤트
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if(!s.isNullOrEmpty()) {
                            filterAdapter.filter.filter(s)
                            binding.recyclerView.adapter = filterAdapter
                        } else {
                            binding.recyclerView.adapter = searchHistoryAdapter
                        }
                    }
                    override fun afterTextChanged(s: Editable?) {
                    }

                })
                //엔터키 이벤트
                setOnEditorActionListener { v, actionId, event ->
                    val word = searchEditText.text.toString().trim()
                    //키보드 내리기
                    val inputManager = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                    binding.searchEditText.clearFocus()
                    if(word.isNotEmpty()) {
                        SeoulMatCheap.getInstance().showToast(owner, "${word}(을)를 검색합니다.")
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            goSearch(word)
                        } else { //그냥 엔터 쳤을 때
                            goSearch(word)
                        }
                    } else SeoulMatCheap.getInstance().showToast(owner, "검색어를 입력해주세요")
                    return@setOnEditorActionListener false
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.searchEditText.text = null
    }

    private fun goSearch(word: String) {
        SearchHistoryPrefs.saveSearchWord(owner, word)
        GlobalScope.launch(Dispatchers.IO) {
            val list = AppDatabase(owner)!!.storeDAO().searchStore("%$word%")
            if(list.isNotEmpty()) { //검색성공
                //검색성공
                owner.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SEARCH_01_01.newInstance(owner, word, list))
                    .commit()
            } else {    //검색실패
                owner.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SEARCH_01_02.newInstance(owner, word))
                    .commit()
                launch(Dispatchers.Main) {SeoulMatCheap.getInstance().showToast(owner, "검색결과가 없습니다.")}

            }
        }
    }

}