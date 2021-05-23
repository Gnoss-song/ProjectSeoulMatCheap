package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.MatCheapService
import kr.co.mapo.project_seoulmatcheap.data.NaverService
import kr.co.mapo.project_seoulmatcheap.data.response.NaverLoginResponse
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityLogin01Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.system.UserPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "[Login]"

class LOGIN_01 : AppCompatActivity() {

    private val binding by lazy { ActivityLogin01Binding.inflate(layoutInflater) }
    private lateinit var mOAuthLoginModule : OAuthLogin
    private lateinit var loginService : MatCheapService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //SharedPreferences 안에 이메일 값이 저장되어 있을 때 -> SPLASH_01로 이동
        if(UserPrefs.getUserEmail(this).isNotBlank()) {
            goNextActivity()
        }
        init()
    }

    private fun init() {
        //네이버 아이디 로그인 초기화
        mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            this,
            getString(R.string.OAUTH_CLIENT_ID),
            getString(R.string.OAUTH_CLIENT_SECRET),
            getString(R.string.OAUTH_CLIENT_NAME)
        )
        loginService = MatCheapService.invoke(this)
        setView()
    }

    private fun setView() {
        with(binding) {
            loginTest.setOnClickListener {
                UserPrefs.saveUserEmail(this@LOGIN_01, "test", code = -1)
                goNextActivity()
//                loginService.serviceLogin()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe ( {  //성공
//                        Log.e("[TEST]", it.toString())
//                        UserPrefs.saveUserEmail(this@LOGIN_01, "test", code = -1)
//                        goNextActivity()
//                    },{ //실패
//                        Log.e("[TEST]", "서버요청 실패, $it")
//                    } )
            }
            kakaoLogin.setOnClickListener {
                //카카오 로그인
                // 로그인 공통 callback 구성
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)
                    } else if (token != null) {
                        Log.e(TAG, "로그인 성공1 ${token.accessToken}")
                        kakaoSucessCallback()
                    }
                }
                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LOGIN_01)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@LOGIN_01, callback = callback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@LOGIN_01, callback = callback)
                }
            }
            naverLogin.setOnClickListener {
                /**
                 * OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
                객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
                 */
                val mOAuthLoginHandler = object : OAuthLoginHandler(){
                    override fun run(p0: Boolean) {
                        if(p0) { //로그인 성공
                            naverSucessCallback()
                        } else { //로그인 실패
                            Log.e(TAG, mOAuthLoginModule.getLastErrorDesc(this@LOGIN_01))
                        }
                    }
                }
                mOAuthLoginModule.startOauthLoginActivity(this@LOGIN_01, mOAuthLoginHandler)
            }
        }
    }

    private fun kakaoSucessCallback() {
        // 토큰 정보 보기
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) Log.e(TAG, "토큰 정보 보기 실패", error)
            else if (tokenInfo != null) Log.e(TAG, "토큰 정보 보기 성공" + "\n회원번호: ${tokenInfo.id}" + "\n만료시간: ${tokenInfo.expiresIn} 초")
        }
        // 사용자 정보 요청
        UserApiClient.instance.me { user, error ->
            if (error != null) Log.e(TAG, "사용자 정보 요청 실패", error)
            else if (user != null) {
                Log.e(TAG, "${user.kakaoAccount?.profile?.nickname}")
                //이메일주소가 null일때
                if(user.kakaoAccount?.email == null) {
                    // 사용자 정보 요청 (추가 동의)
                    UserApiClient.instance.me { user, error ->
                        if (error != null) Log.e(TAG, "사용자 정보 요청 실패", error)
                        else if (user != null) {
                            Log.e(TAG, "사용자 정보 요청하기", error)
                            var scopes= mutableListOf<String>()
                            if (user.kakaoAccount?.emailNeedsAgreement == true) scopes.add("account_email")
                            if (scopes.count() > 0) {
                                Log.e(TAG, "사용자에게 추가 동의를 받아야 합니다.")
                                UserApiClient.instance.loginWithNewScopes(this, scopes) { token, error ->
                                    Log.e(TAG, "흠")
                                    if (error != null) Log.e(TAG, "사용자 추가 동의 실패", error)
                                    else Log.e(TAG, "allowed scopes: ${token!!.scopes}")
                                }
                            }
                        }
                    }
                } else {
                    //로그인처리
                    if (user.kakaoAccount?.profile?.nickname != null) {
                        UserPrefs.saveUserEmail(this@LOGIN_01, user.kakaoAccount?.email!!, 0)
                        SeoulMatCheap.getInstance().showToast(this@LOGIN_01,
                            user.kakaoAccount?.profile?.nickname + getString(R.string.login))
                        Log.e("[Kakao]", "${user.kakaoAccount.toString()}")
                        SeoulMatCheap.getInstance().showToast(this@LOGIN_01, "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        Log.e(TAG, "로그인 성공2 ${user.kakaoAccount?.profile?.nickname}")
                        goNextActivity()
                    } else {
                        Log.e(TAG, "로그인 실패2")
                    }
                }
            }
        }
    }

    private fun naverSucessCallback() {
        val accessToken: String = mOAuthLoginModule.getAccessToken(this@LOGIN_01)
        val naverLoginService = NaverService.invoke(this).getNaverEmail("Bearer "+accessToken)
        Log.e(TAG, "Bearer "+accessToken)
        naverLoginService.enqueue(object : Callback<NaverLoginResponse> {
            override fun onResponse(
                call: Call<NaverLoginResponse>,
                response: Response<NaverLoginResponse>
            ) {
                //서버에 회원 정보 전달
                if(response.isSuccessful) {
                    val result = response.body()!!.response
                    Log.e(TAG, "${result.email}\n${result.nickname}\n${result.profileImage}")
                    UserPrefs.saveUserEmail(this@LOGIN_01, result.email, 1)
                    goNextActivity()
                }
            }
            override fun onFailure(call: Call<NaverLoginResponse>, t: Throwable) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.e("[TEST]", "${UserPrefs.getUserEmail(this)}")
    }

    private fun goNextActivity() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            startActivity(Intent(this@LOGIN_01, PERMISSION_01::class.java))
        } else {
            startActivity(Intent(this@LOGIN_01, SPLASH_01::class.java))
        }
        finish()
    }
}
