package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setAlpha
import androidx.fragment.app.FragmentTransaction
import com.skydoves.balloon.*
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMy01Binding
import kr.co.mapo.project_seoulmatcheap.system.UserPrefs

class MY_01(val owner: AppCompatActivity): Fragment(), View.OnClickListener {

    companion object {
        fun newInstance (owner: AppCompatActivity) : Fragment {
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
        with(binding) {
            btnDialog.apply {
                setOnClickListener(this@MY_01)
            }
            btnLogout.apply {
                setOnClickListener(this@MY_01)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_dialog -> {
                val mDialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_my, null)
                val mBuilder =
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setView(mDialogView)
                        .setTitle(R.string.dialog_title)
                val mAlertDialog = mBuilder.show()
                val okButton = mDialogView.findViewById<Button>(R.id.popup_ok)
                okButton.setOnClickListener {
                    mAlertDialog.dismiss()

                    //        binding.btnHelp.setOnClickListener{
//            val ft: FragmentTransaction = .beginTransaction()
//            ft.add(R.id.framelayout, my01fragment)
//            ft.commit()
//            binding.button.visibility = View.VISIBLE
//        }
                }
            }
            R.id.btn_logout -> UserPrefs.logout(owner)
        }
    }
}

