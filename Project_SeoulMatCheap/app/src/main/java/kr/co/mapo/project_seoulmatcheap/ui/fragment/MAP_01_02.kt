package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMap0102Binding

class MAP_01_02(val owner : AppCompatActivity) : Fragment() {

    companion object {
        fun getInstance(owner : AppCompatActivity) : Fragment {
            return MAP_01_02(owner)
        }
    }

    private lateinit var binding : FragmentMap0102Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMap0102Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeBtn.setOnClickListener {
            owner.supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }
}