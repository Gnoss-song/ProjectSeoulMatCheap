package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setAlpha
import com.skydoves.balloon.*
import kr.co.mapo.project_seoulmatcheap.R

class MY_01 : Fragment() {

    val balloon = createBalloon(requireContext()) {
        setArrowSize(10)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(65)
        setArrowPosition(0.7f)
        setCornerRadius(4f)
        setAlpha(0.9f)
        setText("You can access your profile from now on.")
        setTextColorResource(R.color.white_93)
        setTextIsHtml(true)
        setIconDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile))
        setBackgroundColorResource(R.color.colorPrimary)
        setOnBalloonClickListener(onBalloonClickListener)
        setBalloonAnimation(BalloonAnimation.FADE)
        setLifecycleOwner(lifecycleOwner)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_01, container, false)
    }

}