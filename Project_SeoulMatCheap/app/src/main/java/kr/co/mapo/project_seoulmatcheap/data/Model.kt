package kr.co.mapo.project_seoulmatcheap.data

import android.widget.RatingBar

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-28
 * @desc
 */
data class Model(
        val type: Int, val title: String, val date: String, var ratingBar: Float, val IV: Int, var IV2: Int?, var IV3: Int?, val review: String)
{

    companion object {
        const val IMAGE_TYPE = 0
        const val IMAGE_TYPE2 = 1
        const val IMAGE_TYPE3 = 2

    }

}