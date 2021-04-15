package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat.hasOnClickListeners
import androidx.core.view.ViewCompat.setAlpha
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.naver.maps.map.a.f
import com.skydoves.balloon.*
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMy01Binding
import kr.co.mapo.project_seoulmatcheap.ui.activity.MainActivity

class MY_01: Fragment() {
    lateinit var my01fragment:MY_01
    lateinit var my0101fragment: MY_01_01
    lateinit var matcheap0102fragment: MATCHEAP_01_02
    lateinit var my0102fragment: MY_01_02
    lateinit var my0103fragment: MY_01_03

    private val binding by lazy { FragmentMy01Binding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        my01fragment = MY_01()
        my0101fragment = MY_01_01()
        my0102fragment = MY_01_02()
        my0103fragment = MY_01_03()
        matcheap0102fragment = MATCHEAP_01_02()


        //다이얼로그//
        binding.btnDialog.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_my, null)
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
                LayoutInflater.from(requireContext()).inflate(R.layout.fragment_helpcontest, null)
            val mBuilder =
                androidx.appcompat.app.AlertDialog.Builder(requireContext()).setView(mHelpView)
            val mAlertDialog = mBuilder.show()
            val okButton = mHelpView.findViewById<Button>(R.id.btn_help_ok)
            okButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        //프래그먼트 이동하기


        val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()

        binding.btnFavorite.setOnClickListener {
            ft.add(R.id.my_container, my0101fragment)
            ft.addToBackStack(null)
            ft.commit()
        }

        binding.btnMyreview.setOnClickListener {
                ft.add(R.id.my_container, my0102fragment)
                ft.addToBackStack(null)
                ft.commit()

        }
        binding.btnMyreport.setOnClickListener {
                ft.add(R.id.my_container, matcheap0102fragment)
                ft.addToBackStack(null)
                ft.commit()

        }
        binding.btnNotice.setOnClickListener {
                ft.add(R.id.my_container, my0103fragment)
                ft.addToBackStack(null)
                ft.commit()
        }
    }
}
