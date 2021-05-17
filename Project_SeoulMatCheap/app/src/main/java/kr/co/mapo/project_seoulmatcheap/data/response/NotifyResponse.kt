package kr.co.mapo.project_seoulmatcheap.data.response


import com.google.gson.annotations.SerializedName

data class NotifyResponse(
    @SerializedName("result")
    val result: Result,
    @SerializedName("description")
    val description: String,
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Result(
        @SerializedName("resultCode")
        val resultCode: String,
        @SerializedName("resultMessage")
        val resultMessage: String
    )

    data class Data(
        @SerializedName("noticeId")
        val noticeId: Any?,
        @SerializedName("noticeTitle")
        val noticeTitle: String,
        @SerializedName("noticeContent")
        val noticeContent: String,
        @SerializedName("noticeModifier")
        val noticeModifier: String,
        @SerializedName("modifyDate")
        val modifyDate: String
    )
}