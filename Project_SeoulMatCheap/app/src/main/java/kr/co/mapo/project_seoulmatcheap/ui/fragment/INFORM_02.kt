package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kr.co.mapo.project_seoulmatcheap.R

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-05-06
 * @desc
 */
class INFORM_02(
    private val owner: AppCompatActivity
) : Fragment() {
    companion object {
        fun newInstance(owner: AppCompatActivity): Fragment {
            return INFORM_02(owner)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inform_02, container, false)
    }
}
