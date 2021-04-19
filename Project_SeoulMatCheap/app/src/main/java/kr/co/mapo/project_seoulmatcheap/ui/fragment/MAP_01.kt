package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMap01Binding

class MAP_01 : Fragment() {

    companion object {

    }

    private lateinit var binding : FragmentMap01Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_01, container,false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "현재위치가 들어감"
    }

    fun go(v:View) {
        requireActivity().supportFragmentManager.beginTransaction().add(R.id.filter, MAP_01_02())
            .addToBackStack(null)
            .commit()
    }


}