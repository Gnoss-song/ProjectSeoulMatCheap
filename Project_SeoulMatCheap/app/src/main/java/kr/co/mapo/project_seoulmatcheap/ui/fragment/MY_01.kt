package kr.co.mapo.project_seoulmatcheap.ui.fragment
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMy01Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.system.UserPrefs
import kr.co.mapo.project_seoulmatcheap.ui.activity.*

class MY_01(private val owner : AppCompatActivity): Fragment() {
    companion object {
        fun newInstance(owner: AppCompatActivity) : Fragment {
            return MY_01(owner)
        }
    }
    private val binding by lazy { FragmentMy01Binding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //다이얼로그//
        binding.btnService.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_service, null)
            val mBuilder =
                androidx.appcompat.app.AlertDialog.Builder(owner).setView(mDialogView)
                    .setTitle(R.string.dialog_title)
            val mAlertDialog = mBuilder.show()
            val okButton = mDialogView.findViewById<Button>(R.id.popup_ok)
            okButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
        // 도움말//
        binding.btnHelp.setOnClickListener {
            val mHelpView =
                LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_help, null)
            val mBuilder =
                androidx.appcompat.app.AlertDialog.Builder(owner).setView(mHelpView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(null)
            val okButton = mHelpView.findViewById<Button>(R.id.btn_help_ok)
            okButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
        //로그아웃 //
        binding.btnLogout.setOnClickListener {
            val mLogoutView =
                LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_logout, null)
            val mBuilder =
                androidx.appcompat.app.AlertDialog.Builder(owner).setView(mLogoutView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(null)
            val okButton = mLogoutView.findViewById<Button>(R.id.btn_logout_ok)
            val cancelButton = mLogoutView.findViewById<Button>(R.id.btn_logout_no)

            okButton.setOnClickListener {
                //로그아웃
                if(UserPrefs.logout(owner)) {
                    SeoulMatCheap.getInstance().showToast(owner, "로그아웃 되었습니다.")
                    startActivity(Intent(owner, LOGIN_01::class.java))
                    owner.finish()
                } else {
                    SeoulMatCheap.getInstance().showToast(owner, "로그아웃을 할 수 없습니다.")
                }
                mAlertDialog.dismiss()
            }
            cancelButton.setOnClickListener{
                Toast.makeText(owner,"취소되었습니다.",Toast.LENGTH_SHORT).show()
                mAlertDialog.dismiss()
            }
        }
        //회원탈퇴
        binding.btnWithdrawal.setOnClickListener{
            val mWithdrawalView =
                    LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_withdrawal, null)
            val mBuilder =
                    androidx.appcompat.app.AlertDialog.Builder(owner).setView(mWithdrawalView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(null)
            val okButton = mWithdrawalView.findViewById<Button>(R.id.btn_withdrawl_ok)
            val cancelButton = mWithdrawalView.findViewById<Button>(R.id.btn_withdrawl_no)
            okButton.setOnClickListener {
                //탈퇴
                if(UserPrefs.logout(owner)) {
                    SeoulMatCheap.getInstance().showToast(owner, "탈퇴 되었습니다.")
                    startActivity(Intent(owner, LOGIN_01::class.java))
                    owner.finish()
                } else {
                    SeoulMatCheap.getInstance().showToast(owner, "탈퇴 할 수 없습니다.")
                }
                mAlertDialog.dismiss()
            }
            cancelButton.setOnClickListener{
                Toast.makeText(owner,"취소되었습니다.",Toast.LENGTH_SHORT).show()
                mAlertDialog.dismiss()
            }
        }
        //화면이동하기.
        binding.btnFavorite.setOnClickListener {
            val intent01 = Intent(activity,MY_01_01::class.java)
            startActivity(intent01)
        }
        binding.btnMyreview.setOnClickListener {
            val intent02 = Intent(activity, MY_01_02::class.java)
            startActivity(intent02)
        }
        binding.btnMyreport.setOnClickListener {
            val intent = Intent(activity, MATCHEAP_01_02::class.java)
            startActivity(intent)
        }
        binding.btnNotice.setOnClickListener {
            val intent03 = Intent(activity, MY_01_03::class.java)
            startActivity(intent03)
            Log.e("TEST", "여기인가?")
        }
        //건의사항 메일보내기
        binding.btnReport.setOnClickListener {
            val sendEmail = Intent(Intent.ACTION_SEND)
            sendEmail.type = "plain/Text"
            sendEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(getString(R.string.email)))
            sendEmail.putExtra(
                Intent.EXTRA_SUBJECT,
                "<" + getString(R.string.app_name) + " " + getString(R.string.report) + ">"
            )
            sendEmail.putExtra(
                Intent.EXTRA_TEXT,
                "기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n"
            )
            sendEmail.type = "message/rfc822"
            startActivity(sendEmail)
        }
    }
}
