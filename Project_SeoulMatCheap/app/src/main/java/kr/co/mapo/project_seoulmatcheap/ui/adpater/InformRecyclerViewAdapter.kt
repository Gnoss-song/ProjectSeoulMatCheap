package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.InformItem

/**
 * @author user
 * @email syk525678@naver.com
 * @created 2021-05-18
 * @desc
 */
class InformRecyclerViewAdapter(
    private val informList:MutableList<InformItem>)
    :RecyclerView.Adapter<InformRecyclerViewAdapter.InformHolder>(){

    inner class InformHolder(rowRoot : View) : RecyclerView.ViewHolder(rowRoot) {
        val informIV : ImageView = rowRoot.findViewById(R.id.informIV)
        val informDate : TextView = rowRoot.findViewById(R.id.informDate)
        val informName : TextView = rowRoot.findViewById(R.id.informName)
        val informRatingBar : ScaleRatingBar = rowRoot.findViewById(R.id.informRatingBar)
        val informImage : ImageView = rowRoot.findViewById(R.id.informImage)
        val informText : TextView = rowRoot.findViewById(R.id.informText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inform_item,parent,false)
        return InformHolder(view)
    }

    override fun getItemCount(): Int {
        return informList.size
    }

    override fun onBindViewHolder(holder: InformHolder, position: Int) {
        val infromData = informList[position]
        with(holder) {
            informIV.setImageResource(infromData.informIV)
            informDate.text = infromData.informDate
            informName.text = infromData.informName
            informRatingBar.rating = infromData.informRatingBar
            informImage.setImageResource(infromData.informImage)
            informText.text = infromData.informText
        }
    }
}