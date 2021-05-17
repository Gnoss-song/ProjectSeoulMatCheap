package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-16
 * @desc
 */
class InfromReviewAdapter : RecyclerView.Adapter<InfromReviewAdapter.HolderView>() {
    inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        return HolderView(LayoutInflater.from(parent.context).inflate(R.layout.recycler_inform_review, parent, false))
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
    }

    override fun getItemCount(): Int = 3
}