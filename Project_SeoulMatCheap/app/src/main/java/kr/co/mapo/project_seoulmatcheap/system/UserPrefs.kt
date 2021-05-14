package kr.co.mapo.project_seoulmatcheap.system

import android.app.Application
import android.content.Context
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-28
 * @desc 로그인 세션관리 object
 */

private const val TAG = "[LOGIN]"

object UserPrefs {

    private const val USER_EMAIL = "email"
    private const val OAUTH_CODE = "oauth_code"

    // oauth_code -> Int값, 카카오로그인(0), 네이버로그인(1)
    fun saveUserEmail(context: Context, input: String, code : Int) {
        val prefs = context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
        with(prefs.edit()) {
            putInt(OAUTH_CODE, code)
            putString(USER_EMAIL, input)
        }.apply()
    }

    fun getUserEmail(context: Context) : String {
        return context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
            .getString(USER_EMAIL, "").toString()
    }

    fun logout(context: Context) : Boolean {
        val prefs = context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
        val editor = prefs.edit()
        val login_code = prefs.getInt(OAUTH_CODE, -1)
        Log.e("[TEST]", "$login_code")
        when(prefs.getInt(OAUTH_CODE, -1)) {
            0 -> { //카카오 로그아웃
                Log.e("[TEST]", "카카오 로그아웃")
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e(TAG, "연결 끊기 실패", error)
                    }
                    else {
                        Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                    }
                }
            }
            1 -> { //네이버 로그아웃
                Log.e("[TEST]", "네이버 로그아웃")
                val mOAuthLoginInstance = OAuthLogin.getInstance()
                //mOAuthLoginInstance.logout(context)
                val isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(context)
                Log.e("[TEST]", "$isSuccessDeleteToken")
                if (!isSuccessDeleteToken) {
                    // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                    // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                    Log.e(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(context));
                    Log.e(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(context));
                }
            }
        }
        editor.clear().apply()
        return true
    }
}