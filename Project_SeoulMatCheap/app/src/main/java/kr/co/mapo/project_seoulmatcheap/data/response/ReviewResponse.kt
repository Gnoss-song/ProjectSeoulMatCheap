package kr.co.mapo.project_seoulmatcheap.data.response


import com.google.gson.annotations.SerializedName

class ReviewResponse : ArrayList<ReviewResponse.ReviewResponseItem>(){
    data class ReviewResponseItem(
        @SerializedName("reviewContent")
        val reviewContent: String,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("reviewId")
        val reviewId: Int,
        @SerializedName("storeId")
        val storeId: Int,
        @SerializedName("storeName")
        val storeName: String,
        @SerializedName("memberId")
        val memberId: Int,
        @SerializedName("nickname")
        val nickname: String
    )
}