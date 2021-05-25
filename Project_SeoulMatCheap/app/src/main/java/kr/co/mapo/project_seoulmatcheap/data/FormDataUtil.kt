package kr.co.mapo.project_seoulmatcheap.data

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-23
 * @desc
 */
object FormDataUtil {

    fun getBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
    }
//
//    fun getRequestBody(body : Any) : RequestBody {
//        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
//            JSONObject(body)
//
//        )
//    }

    //key : 파일 파라미터명
    fun getImageBody(key: String, file: File): MultipartBody.Part {
        val surveyBody = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(
            key,
            file.name,
            surveyBody
        )
    }

    //업종변환함수
//    fun getSort(sort : Int) : String = when(sort) {
//        1 ->
//    }
}