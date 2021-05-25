package kr.co.mapo.project_seoulmatcheap.data

import java.io.Serializable

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-28
 * @desc
 */
data class Model(
        val type: Int, val title: String,val date : String, var ratingBar: Float, val IV : Int,val IV2 : Int? ,val IV3 : Int?, val review: String)
    :Serializable
{
    companion object {
        const val IMAGE_TYPE = 1
    }
}