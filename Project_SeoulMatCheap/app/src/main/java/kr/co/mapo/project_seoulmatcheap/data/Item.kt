package kr.co.mapo.project_seoulmatcheap.data

import android.widget.CheckBox

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
data class Item(val marketIV:Int,
                var name:String = "",
                var address: String = "",
                var sort: String ="",
                var distance : String = "",
                var score : String = ""
                )