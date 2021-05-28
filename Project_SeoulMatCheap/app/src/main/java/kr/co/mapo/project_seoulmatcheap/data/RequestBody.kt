package kr.co.mapo.project_seoulmatcheap.data

import com.google.gson.annotations.SerializedName

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-23
 * @desc
 */

data class LoginBody (
    var email: String = "", // string
    var level: Int = 0, // 0
    var memberModifier: String = "", // string
    var memberWriter: String = "", // string
    var nickname: String = "", // string
    var point: Int = 0, // 0
    var profileUrl: String = "" // string
    )

data class ReviewBody (
    var memberId : Int = 28,
    var storeId : Int = 18,
    val reviewContent : String,
    var rating : Float,
    var reviewWriter : String = "test 입력자",
    var reviewModifier : String = "test 수정자"
)