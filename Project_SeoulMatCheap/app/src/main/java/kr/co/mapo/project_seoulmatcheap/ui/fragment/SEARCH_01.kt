package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.R

class SEARCH_01(val activity: AppCompatActivity) : Fragment() {

    companion object {
        fun getInstance(owner: AppCompatActivity) : Fragment {
            return SEARCH_01(owner)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_01, container, false)
    }

}