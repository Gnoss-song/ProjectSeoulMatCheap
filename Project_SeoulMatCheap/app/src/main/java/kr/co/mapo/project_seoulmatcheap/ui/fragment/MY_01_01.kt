package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kr.co.mapo.project_seoulmatcheap.R

data class Item(val marketIV:Int,
                var name:String = "",
                var address: String = "",
                var sort: String ="",
                var distance : String = "",
                var score : String = "")

class MY_01_01 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_01_01, container, false)
    }

}
