package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentMap0102Binding
import kr.co.mapo.project_seoulmatcheap.system.*
import java.util.*

const val FILTER = "filter_bottomSheet_dialog"

class MAP_01_02(val map: MAP_01) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMap0102Binding

    //필터목록저장
    var filterSort = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.filterBottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_01_02, container, false)
        binding.filter = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        with(binding) {
            if(filterSort.contains(SORT_HANSIK)) {
                buttonHansik.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_hansik, null)
                }
                textHansik.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }
            if(filterSort.contains(SORT_CHINA)) {
                buttonChina.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_china, null)
                }
                textChina.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }
            if(filterSort.contains(SORT_JAPAN)) {
                buttonJapan.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_japan, null)
                }
                textJapan.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }
            if(filterSort.contains(SORT_FOOD)) {
                buttonFood.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_food, null)
                }
                textFood.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }
            if(filterSort.contains(SORT_WASH)) {
                buttonWash.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_wash, null)
                }
                textWash.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }
            if(filterSort.contains(SORT_BEAUTY)) {
                buttonBeauty.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_beauty, null)
                }
                textBeauty.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }
            if(filterSort.contains(SORT_HOTEL)) {
                buttonHotel.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_hotel, null)
                }
                textHotel.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }
            if(filterSort.contains(SORT_STORE)) {
                buttonStore.apply {
                    isChecked = true
                    background = requireContext().resources.getDrawable(R.drawable.icon_store, null)
                }
                textStore.apply {
                    setBackgroundColor(MapHelper.circleColor)
                    setTextColor(MapHelper.mainColor)
                    typeface = MapHelper.bold
                }
            }

            button100.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    optionDistance(text100, isChecked)
                    map.filterDistance(100.0)
                }
            }
            button500.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    optionDistance(text500, isChecked)
                    map.filterDistance(500.0)
                }
            }
            button2000.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    optionDistance(text2000, isChecked)
                    map.filterDistance(2000.0)
                }
            }
        }
    }

    fun onClickSortToggle(v : View) {
        with(binding) {
            when(v.id) {
                //버튼
                R.id.button_hansik -> optionSort(v, R.drawable.icon_hansik, textHansik, R.drawable.icon_hansik_off)
                R.id.button_china -> optionSort(v, R.drawable.icon_china, textChina, R.drawable.icon_china_off)
                R.id.button_japan -> optionSort(v, R.drawable.icon_japan, textJapan, R.drawable.icon_japan_off)
                R.id.button_food -> optionSort(v, R.drawable.icon_food, textFood, R.drawable.icon_food_off)
                R.id.button_wash -> optionSort(v, R.drawable.icon_wash, textWash, R.drawable.icon_wash_off)
                R.id.button_beauty -> optionSort(v, R.drawable.icon_beauty, textBeauty, R.drawable.icon_beauty_off)
                R.id.button_hotel -> optionSort(v, R.drawable.icon_hotel, textHotel, R.drawable.icon_hotel_off)
                R.id.button_store -> optionSort(v, R.drawable.icon_store, textStore, R.drawable.icon_store_off)
                R.id.button_initiate -> map.filterInitialize()
                //텍스트뷰
                R.id.text_hansik -> optionSortT(buttonHansik, R.drawable.icon_hansik, v, R.drawable.icon_hansik_off)
                R.id.text_china -> optionSortT(buttonChina, R.drawable.icon_china, v, R.drawable.icon_china_off)
                R.id.text_japan -> optionSortT(buttonJapan, R.drawable.icon_japan, v, R.drawable.icon_japan_off)
                R.id.text_food -> optionSortT(buttonFood, R.drawable.icon_food, v, R.drawable.icon_food_off)
                R.id.text_wash -> optionSortT(buttonWash, R.drawable.icon_wash, v, R.drawable.icon_wash_off)
                R.id.text_beauty -> optionSortT(buttonBeauty, R.drawable.icon_beauty, v, R.drawable.icon_beauty_off)
                R.id.text_hotel -> optionSortT(buttonHotel, R.drawable.icon_hotel, v, R.drawable.icon_hotel_off)
                R.id.text_store -> optionSortT(buttonStore, R.drawable.icon_store, v, R.drawable.icon_store_off)
            }
        }
    }

    private fun optionSort(v1: View, on_image: Int, v2 : View, off_image: Int) {
        val button = v1 as ToggleButton
        val textView = v2 as TextView
        val sort = textView.text.toString().trim()
        if(button.isChecked) {  //체크되어있을 때
            button.background = requireContext().resources.getDrawable(on_image, null)
            with(v2) {
                setBackgroundColor(MapHelper.circleColor)
                setTextColor(MapHelper.mainColor)
                typeface = Typeface.DEFAULT_BOLD
            }
            filterSort.add(sort)
        } else {
            button.background = requireContext().resources.getDrawable(off_image, null)
            with(v2) {
                background = null
                setTextColor(MapHelper.blackColor)
                typeface = null
            }
            filterSort.remove(sort)
        }
        map.filterSort(filterSort)
    }

    private fun optionSortT(v1: View, on_image: Int, v2 : View, off_image: Int) {
        val button = v1 as ToggleButton
        val textView = v2 as TextView
        val sort = textView.text.toString().trim()
        if(!button.isChecked) {  //체크 안되어있을 때
            with(button) {
                background = requireContext().resources.getDrawable(on_image, null)
                isChecked = true
            }
            with(v2) {
                setBackgroundColor(MapHelper.circleColor)
                setTextColor(MapHelper.mainColor)
                typeface = Typeface.DEFAULT_BOLD
            }
            filterSort.add(sort)
        } else {
            with(button) {
                background = requireContext().resources.getDrawable(off_image, null)
                isChecked = false
            }
            with(v2) {
                background = null
                setTextColor(MapHelper.blackColor)
                typeface = null
            }
            filterSort.remove(sort)
        }
        map.filterSort(filterSort)
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

    override fun onPause() {
        super.onPause()
        map.initiateOverlay()
    }

}