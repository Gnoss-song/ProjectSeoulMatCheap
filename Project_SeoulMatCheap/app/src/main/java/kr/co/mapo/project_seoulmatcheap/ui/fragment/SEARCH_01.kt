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
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentSearch01Binding
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        preferences = owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
        val test = arrayListOf("자동", "자동완성", "자동완성테스트", "자동완성테스트1", "자동완성테스트2", "자동완성테스트3", "완성", "테스트")
        filterAdapter = AutoCompleteAdapter(test, owner)

        searchHistoryAdapter = SearchHistoryAdapter(preferences.all.values.toMutableList(), owner)
        Log.e("[히스토리]", "${preferences.all.values.toMutableList().size}")
        setView()
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
                            Log.e("[자동완성 어댑터]", "${filterAdapter.itemCount}")
                        } else {
                            binding.recyclerView.adapter = searchHistoryAdapter
                        }
                    }
                    override fun afterTextChanged(s: Editable?) {
                    }

                })
                //엔터키 이벤트
                setOnEditorActionListener { v, actionId, event ->
                    if(searchEditText.text?.isNotEmpty() == true) {
                        SeoulMatCheap.getInstance().showToast(owner, "검색요청")
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            goFail(searchEditText.text.toString().trim())
                        } else { //그냥 엔터 쳤을 때
                            goFail(searchEditText.text.toString().trim())
                        }
                    } else SeoulMatCheap.getInstance().showToast(owner, "검색어를 입력해주세요")
                    return@setOnEditorActionListener false
                }
            }
        }
    }

    private fun savePreference(word : String) {
        val edit = preferences.edit()
        edit.putString(word, word).apply()
    }

    override fun onPause() {
        super.onPause()
        binding.searchEditText.text = null
    }

    fun goSearch(word: String) {
        savePreference(word)
        owner.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SEARCH_01_01.newInstance(owner, word))
            .commit()
    }

    fun goFail(word: String) {
        savePreference(word)
        owner.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SEARCH_01_02.newInstance(owner, word))
            .commit()
    }

    override fun onResume() {
        super.onResume()
    }

}