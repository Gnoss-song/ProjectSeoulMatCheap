package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMap0102Binding

class MAP_01_02(val owner : AppCompatActivity) : Fragment() {

    companion object {
        fun getInstance(owner : AppCompatActivity) : Fragment {
            return MAP_01_02(owner)
        }
    }

    private lateinit var binding : FragmentMap0102Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMap0102Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        with(binding) {
            buttonHansik.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_hansik, textHansik, R.drawable.icon_hansik_off)
                }
            }
            buttonChina.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_china, textChina, R.drawable.icon_china_off)
                }
            }
            buttonJapan.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_japan, textJapan, R.drawable.icon_japan_off)
                }
            }
            buttonFood.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_food, textFood, R.drawable.icon_food_off)
                }
            }
            buttonWash.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_wash, textWash, R.drawable.icon_wash_off)
                }
            }
            buttonBeauty.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_beauty, textBeauty, R.drawable.icon_beauty_off)
                }
            }
            buttonHotel.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_hotel, textHotel, R.drawable.icon_hotel_off)
                }
            }
            buttonStore.apply {
                setOnClickListener {
                    optionSort(it, R.drawable.icon_store, textStore, R.drawable.icon_store_off)
                }
            }
            button100.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    optionDistance(text100, isChecked)
                }
            }
            button500.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    optionDistance(text500, isChecked)
                }
            }
            button2000.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    optionDistance(text2000, isChecked)
                }
            }
        }
    }

    private fun optionSort(v1: View, on_image: Int, text : TextView, off_image: Int) {
        val button = v1 as ToggleButton
        if(button.isChecked) {
            button.background = owner.resources.getDrawable(on_image, null)
            with(text) {
                setTextColor(resources.getColor(R.color.main, null))
                typeface = Typeface.DEFAULT_BOLD
            }
        } else {
            button.background = owner.resources.getDrawable(off_image, null)
            with(text) {
                setTextColor(resources.getColor(R.color.black, null))
                typeface = null
            }
        }
    }

    private fun optionDistance(v:TextView, checked:Boolean) {
        with(v) {
            typeface = if(checked) {
                setTextColor(resources.getColor(R.color.main, null))
                Typeface.DEFAULT_BOLD
            } else {
                setTextColor(resources.getColor(R.color.black, null))
                null
            }
        }
    }

}