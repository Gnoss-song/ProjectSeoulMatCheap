package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentInform021Binding


class INFORM_02_1 : Fragment() {

    lateinit var binding : FragmentInform021Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInform021Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonCall.setOnClickListener {
                val tt = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01077777777"))
                startActivity(tt)
            }
            buttonNavi.setOnClickListener {

            }
        }
    }
}