package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentDialogMyServiceBinding
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMy01Binding

class DIALOG_MY_SERVICE : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDialogMyServiceBinding.inflate(inflater,container,false)
        return binding.root

              }
    }