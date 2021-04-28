package kr.co.mapo.project_seoulmatcheap.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-07
 * @desc 서울맛칩 서버 API 요청 레트로핏
 */

private const val TARGET_ADDRESS = ""
private const val MEMBER_CATEGORY_CODE = "member_category_code"
private const val EMAIL = "email"
private const val PROFILE_URL = "profile_url"
private const val NICKNAME = "nickname"

interface MatCheapService {

    @FormUrlEncoded
    @POST()
    fun serviceLogin (
        @Field(MEMBER_CATEGORY_CODE) member_category_code : String,
        @Field(EMAIL) email : String,
        @Field(PROFILE_URL) profile_url : String,
        @Field(NICKNAME) nickname : String
    )

}