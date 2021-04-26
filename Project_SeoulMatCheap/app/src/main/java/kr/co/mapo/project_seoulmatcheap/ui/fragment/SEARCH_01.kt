package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentSearch01Binding
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
import kr.co.mapo.project_seoulmatcheap.ui.adpater.AutoCompleteAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.SearchHistoryAdapter

class SEARCH_01(val owner: AppCompatActivity) : Fragment(), TextWatcher {

    companion object {
        fun newInstance(owner: AppCompatActivity) : Fragment {
            return SEARCH_01(owner)
        }
    }

    private lateinit var binding : FragmentSearch01Binding
    private lateinit var preferences : SharedPreferences
    private lateinit var list : MutableList<*>
    private lateinit var filterAdapter : AutoCompleteAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearch01Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        preferences = owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
        list = preferences.all.values.toMutableList()
        val test = arrayListOf("자동", "자동완성", "자동완성테스트", "자동완성테스트1", "자동완성테스트2", "자동완성테스트3", "완성", "테스트")
        filterAdapter = AutoCompleteAdapter(test, owner)
        Log.e("[자동완성]", "${test.size}")
        searchHistoryAdapter = SearchHistoryAdapter(list, owner)

        Log.e("[히스토리]", "${list.size}")
        setView()
    }

    private fun setView() {
        with(binding) {
            toolbar.apply {
                setOnMenuItemClickListener {
                    if(it.itemId == R.id.search) {
                        Toast.makeText(owner, "검색 버튼 누름", Toast.LENGTH_SHORT).show()
                        savePreference(searchEditText.text.toString())
                    }
                    return@setOnMenuItemClickListener true
                }
            }
            recyclerView.apply {
                layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
                adapter = searchHistoryAdapter
            }
            searchEditText.apply {
                addTextChangedListener(this@SEARCH_01)
            }
        }
    }

    fun savePreference(word : String) {
        val edit = preferences.edit()
        preferences.edit().putString(word, word).apply()
    }

    override fun onPause() {
        super.onPause()
        binding.searchEditText.text = null
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(!s.isNullOrEmpty()) {
            filterAdapter.filter.filter(s)
            with(binding.recyclerView) {
                layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
                adapter = filterAdapter
                Log.e("[TEST]", "${filterAdapter.itemCount}")
            }
        } else {
            binding.recyclerView.adapter = searchHistoryAdapter
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }
}