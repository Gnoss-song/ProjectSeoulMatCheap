package kr.co.mapo.project_seoulmatcheap.ui.adpater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.ui.fragment.INFORM_02_1
import kr.co.mapo.project_seoulmatcheap.ui.fragment.INFORM_02_2

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-23
 * @desc
 */

class InformViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    lateinit var item : StoreEntity

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            0 -> fragment = INFORM_02_1(item)
            1 -> fragment = INFORM_02_2()
        }
        return fragment
    }
}