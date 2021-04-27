package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.balloon.balloon
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentSearch0101Binding
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
import kr.co.mapo.project_seoulmatcheap.ui.adpater.AutoCompleteAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.SearchHistoryAdapter

//검색성공
class SEARCH_01_01(
    private val owner : AppCompatActivity,
    private val word : String
    ) : Fragment(), TextWatcher, View.OnTouchListener {
    companion object {
        fun newInstance(owner: AppCompatActivity, word: String) : Fragment {
            return SEARCH_01_01(owner, word)
        }
    }

    private lateinit var binding : FragmentSearch0101Binding
    private lateinit var preferences : SharedPreferences
    private lateinit var filterAdapter : AutoCompleteAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearch0101Binding.inflate(inflater, container, false)
        binding.searchEditText.setText(word)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val test = arrayListOf("자동", "자동완성", "자동완성테스트", "자동완성테스트1", "자동완성테스트2", "자동완성테스트3", "완성", "테스트")
        filterAdapter = AutoCompleteAdapter(test, owner)
        preferences = owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
        searchHistoryAdapter = SearchHistoryAdapter(preferences.all.values.toMutableList(), owner)
        setView()
    }

    private fun setView() {
        with(binding) {
            toolbar.apply {
                setOnMenuItemClickListener {
                    if(it.itemId == R.id.search) {
                        Toast.makeText(owner, "검색 버튼 누름", Toast.LENGTH_SHORT).show()
                        savePreference(searchEditText.text.toString())
                        owner.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, SEARCH_01_01.newInstance(owner, searchEditText.text.toString()))
                            .commit()
                    }
                    return@setOnMenuItemClickListener true
                }
            }
            autoCompleteRecyclerView.apply {
                layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
                adapter = filterAdapter
                setOnTouchListener(this@SEARCH_01_01)
            }
            searchEditText.apply {
                addTextChangedListener(this@SEARCH_01_01)
            }
        }
    }

    private fun savePreference(word : String) {
        val edit = preferences.edit()
        edit.putString(word, word).apply()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        with(binding.autoCompleteRecyclerView) {
            if(!s.isNullOrEmpty()) {
                filterAdapter.filter.filter(s)
                visibility = View.VISIBLE
                Log.e("[자동완성 어댑터]", "${filterAdapter.itemCount}")
            } else {
                visibility = View.GONE
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val imm = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(owner.currentFocus?.windowToken, 0)
        binding.autoCompleteRecyclerView.visibility = View.GONE
        return true
    }

}