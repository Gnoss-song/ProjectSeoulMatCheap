package kr.co.mapo.project_seoulmatcheap.system

import android.app.Application
import android.content.Context
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import kr.co.mapo.project_seoulmatcheap.ui.activity.TAG

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-28
 * @desc 로그인 세션관리 object
 */

object UserPrefs {

    private const val USER_PRESFS = "user_prefs"
    private const val USER_EMAIL = "email"

    fun saveUserEmail(context: Context, input: String) {
        val prefs = context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(USER_EMAIL, input).apply()
    }

    fun getUserEmail(context: Context) : String {
        return context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
            .getString(USER_EMAIL, null).toString()
    }

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
        val editor = prefs.edit()
        //카카오 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
        //네이버 로그아웃
        OAuthLogin.getInstance().logout(context)
    }
}