package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityLogin01Binding

const val TAG = "[TEST]"

class LOGIN_01 : AppCompatActivity() {

    private val binding by lazy { ActivityLogin01Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
            kakaoLogin.setOnClickListener {
                //카카오 로그인
                // 로그인 공통 callback 구성
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)
                    } else if (token != null) {
                        Log.e(TAG, "로그인 성공 ${token.accessToken}")
                        // 토큰 정보 보기
                        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                            if (error != null) {
                                Log.e(TAG, "토큰 정보 보기 실패", error)
                            }
                            else if (tokenInfo != null) {
                                Log.e(TAG, "토큰 정보 보기 성공" +
                                        "\n회원번호: ${tokenInfo.id}" +
                                        "\n만료시간: ${tokenInfo.expiresIn} 초")
                            }
                        }
                        // 사용자 정보 요청 (기본)
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", error)
                            }
                            else if (user != null) {
                                Log.e(TAG, "사용자 정보 요청 성공" +
                                        "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                            }
                        }
                        startActivity(Intent(this@LOGIN_01, SPLASH_01::class.java))
                    }
                }
                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LOGIN_01)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@LOGIN_01, callback = callback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@LOGIN_01, callback = callback)
                }
            }
        }
    }

}