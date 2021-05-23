package kr.co.mapo.project_seoulmatcheap.data.response


import com.google.gson.annotations.SerializedName

data class MemberResponse(
    @SerializedName("email")
    var email: String = "", // string
    @SerializedName("level")
    var level: Int = 0, // 0
    @SerializedName("memberId")
    var memberId: Int = 0, // 0
    @SerializedName("memberModifier")
    var memberModifier: String = "", // string
    @SerializedName("memberWriter")
    var memberWriter: String = "", // string
    @SerializedName("modifyDate")
    var modifyDate: String = "", // 2021-05-23T12:55:09.413Z
    @SerializedName("nickname")
    var nickname: String = "", // string
    @SerializedName("point")
    var point: Int = 0, // 0
    @SerializedName("profileUrl")
    var profileUrl: String = "", // string
    @SerializedName("registerDate")
    var registerDate: String = "" // 2021-05-23T12:55:09.413Z
)