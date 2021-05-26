package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentSearch0102Binding
import kr.co.mapo.project_seoulmatcheap.system.SearchHistoryPrefs
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.AutoCompleteAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.SearchHistoryAdapter

//검색실패
class SEARCH_01_02 (
        private val owner : AppCompatActivity,
        private val word : String
    ) : Fragment() {

    companion object {
        fun newInstance(owner: AppCompatActivity, word: String) : Fragment {
            return SEARCH_01_02(owner, word)
        }
    }

    private lateinit var binding: FragmentSearch0102Binding
    private lateinit var filterAdapter : AutoCompleteAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearch0102Binding.inflate(inflater, container, false)
        binding.searchEditText.setText(word)
        setHasOptionsMenu(true)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun init() {
        owner.setSupportActionBar(binding.toolbar)
        filterAdapter = AutoCompleteAdapter(SeoulMatCheap.getInstance().filterList, owner)
        searchHistoryAdapter = SearchHistoryAdapter(SearchHistoryPrefs.getSearchHistory(owner), owner)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
    }

    private fun setView() {
        with(owner.supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
        }
        with(binding) {
            autoCompleteRecyclerView.apply {
                layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
                adapter = filterAdapter
                setOnTouchListener { v, event ->
                    val imm = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(owner.currentFocus?.windowToken, 0)
                    autoCompleteRecyclerView.visibility = View.GONE
                    searchEditText.clearFocus()
                    return@setOnTouchListener true
                }
            }
            searchEditText.apply {
                //텍스트입력 이벤트
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        with(autoCompleteRecyclerView) {
                            if(!s.isNullOrEmpty()) {
                                filterAdapter.filter.filter(s)
                                visibility = View.VISIBLE
                            } else {
                                visibility = View.GONE
                            }
                        }
                    }
                    override fun afterTextChanged(s: Editable?) {
                    }
                })
                //포커스 이벤트
                setOnFocusChangeListener { v, hasFocus ->
                    filterAdapter.filter.filter(searchEditText.text.toString().trim())
                    binding.autoCompleteRecyclerView.visibility = if(hasFocus) View.VISIBLE else View.GONE
                }
                //엔터키 이벤트
                setOnEditorActionListener { v, actionId, event ->
                    //키보드 내리기
                    val inputManager = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                    binding.searchEditText.clearFocus()
                    val word = searchEditText.text.toString().trim()
                    if(searchEditText.text?.isNotEmpty() == true) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                owner.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SEARCH_01.newInstance(owner))
                    .commit()
            }
            R.id.search -> {
                SeoulMatCheap.getInstance().showToast(owner, "검색 버튼 누름")
                if (binding.searchEditText.text?.isNotEmpty() == true) goSearch(binding.searchEditText.text.toString().trim())
                else SeoulMatCheap.getInstance().showToast(owner, "검색어를 입력해주세요")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goSearch(word: String) {
        SearchHistoryPrefs.saveSearchWord(owner, word)
        //검색
        GlobalScope.launch(Dispatchers.IO) {
            val list = AppDatabase(owner)!!.storeDAO().searchStore("%$word%")
            if(list.isNotEmpty()) { //검색성공
                owner.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SEARCH_01_01.newInstance(owner, word, list))
                    .commit()
            } else {
                SeoulMatCheap.getInstance().showToast(owner, "검색결과가 없습니다.")
            }
        }
        //키보드 내리기
        val inputManager = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(owner.currentFocus?.windowToken, 0)
        binding.searchEditText.clearFocus()
    }

}