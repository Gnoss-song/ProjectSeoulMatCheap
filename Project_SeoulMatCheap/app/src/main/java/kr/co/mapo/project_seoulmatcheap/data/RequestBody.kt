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
    var memberId: Int = 0, // 0
    var memberModifier: String = "", // string
    var memberWriter: String = "", // string
    var modifyDate: String = "", // 2021-05-23T12:55:09.396Z
    var nickname: String = "", // string
    var point: Int = 0, // 0
    var profileUrl: String = "", // string
    var registerDate: String = "" // 2021-05-23T12:55:09.396Z
    )