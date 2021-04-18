package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMy01Binding
import kr.co.mapo.project_seoulmatcheap.ui.activity.*

class MY_01(val activity : AppCompatActivity): Fragment() {

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
                LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_my_service, null)
            val mBuilder =
                androidx.appcompat.app.AlertDialog.Builder(requireContext()).setView(mDialogView)
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
                LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_my_help, null)
            val mBuilder =
                androidx.appcompat.app.AlertDialog.Builder(requireContext()).setView(mHelpView)
            val mAlertDialog = mBuilder.show()
            val okButton = mHelpView.findViewById<Button>(R.id.btn_help_ok)
            okButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        //로그아웃 //
        binding.btnLogout.setOnClickListener {
            val mLogoutView =
                LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_my_logout, null)
            val mBuilder =
                androidx.appcompat.app.AlertDialog.Builder(requireContext()).setView(mLogoutView)
            val mAlertDialog = mBuilder.show()
            val okButton = mLogoutView.findViewById<Button>(R.id.btn_logout_ok)
            val cancelButton = mLogoutView.findViewById<Button>(R.id.btn_logout_no)

            okButton.setOnClickListener {
                Dialog
                mAlertDialog.dismiss()
            }
        }



        //프래그먼트 이동하기


        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

        binding.btnFavorite.setOnClickListener {
//            ft.add(R.id.my_container, my0101fragment)
//            ft.addToBackStack(null)
//            ft.commit()
//            activity.supportFragmentManager.beginTransaction().remove(this).commit()
//            activity.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
            val intent01 = Intent(getActivity(),MY_01_01::class.java)
            startActivity(intent01)
        }

        binding.btnMyreview.setOnClickListener {
            val intent02 = Intent(getActivity(), MY_01_02::class.java)
            startActivity(intent02)

        }
        binding.btnMyreport.setOnClickListener {
            val intent = Intent(getActivity(), MATCHEAP_01_02::class.java)
            startActivity(intent)

        }
        binding.btnNotice.setOnClickListener {
            val intent03 = Intent(getActivity(), MY_01_03::class.java)
            startActivity(intent03)
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
        fun onBackPressed() {
            return
        }
    }
}
