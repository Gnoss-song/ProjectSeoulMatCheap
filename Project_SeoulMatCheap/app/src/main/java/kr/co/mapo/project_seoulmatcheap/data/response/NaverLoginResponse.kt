package kr.co.mapo.project_seoulmatcheap.data.response


import com.google.gson.annotations.SerializedName

data class NaverLoginResponse(
    @SerializedName("resultcode")
    val resultcode: String = "", // 00
    @SerializedName("message")
    val message: String = "", // success
    @SerializedName("response")
    val response: Response = Response()
) {
    data class Response(
        @SerializedName("email")
        val email: String = "", // openapi@naver.com
        @SerializedName("nickname")
        val nickname: String = "", // OpenAPI
        @SerializedName("profile_image")
        val profileImage: String = "", // https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif
        @SerializedName("age")
        val age: String = "", // 40-49
        @SerializedName("gender")
        val gender: String = "", // F
        @SerializedName("id")
        val id: String = "", // 32742776
        @SerializedName("name")
        val name: String = "", // 오픈 API
        @SerializedName("birthday")
        val birthday: String = "", // 10-01
        @SerializedName("birthyear")
        val birthyear: String = "", // 1900
        @SerializedName("mobile")
        val mobile: String = "" // 010-0000-0000
    )
}