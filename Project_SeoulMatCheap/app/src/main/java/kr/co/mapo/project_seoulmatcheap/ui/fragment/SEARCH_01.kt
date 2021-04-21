package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.os.Bundle
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
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap


class SEARCH_01(val owner: AppCompatActivity) : Fragment(){

    companion object {
        fun newInstance(owner: AppCompatActivity) : Fragment {
            return SEARCH_01(owner)
        }
    }

    private lateinit var binding : FragmentSearch01Binding

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
        setView()
    }

    private fun setView() {
        with(binding) {
            toolbar.apply {
                setOnMenuItemClickListener {
                    if(it.itemId == R.id.search) {
                        Toast.makeText(owner, "검색 버튼 누름", Toast.LENGTH_SHORT).show()
                    }
                    return@setOnMenuItemClickListener true
                }
            }
            recyclerView.apply {
                layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
            }
        }
    }
}