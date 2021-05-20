package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.GridItem
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentCategory01Binding
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.activity.CATEGORY_01_01
import kr.co.mapo.project_seoulmatcheap.ui.activity.CATEGORY_01_03
import kr.co.mapo.project_seoulmatcheap.ui.adpater.GridRecyclerViewAdapter

class CATEGORY_01(val owner:AppCompatActivity) : Fragment(),View.OnClickListener {
    companion object {
        fun newInstance(owner: AppCompatActivity) : Fragment {
            return CATEGORY_01(owner)
        }
    }
    private lateinit var binding: FragmentCategory01Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategory01Binding.inflate(layoutInflater)
        binding.moveTV.isSelected = true
        setHasOptionsMenu(true)

        SeoulMatCheap.getInstance().address.observe(viewLifecycleOwner, Observer {
            binding.categoryLocationTV.text = it
        })

        val manager = GridLayoutManager(context,4)
        with(binding.gridRecyclerView) {
            layoutManager = manager
            adapter = GridRecyclerViewAdapter(gridData())
        }
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMain.apply {
            inflateMenu(R.menu.category_toolbar)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.toolbar_help -> {
                        val intent_tran = Intent(context, CATEGORY_01_03::class.java)
                        startActivity(intent_tran)
                    }
                }
                true
            }
        }
        binding.moveTV.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val balloonView = layoutInflater.inflate(R.layout.balloon_view, null)
            with(builder) {
                setCancelable(true)
                setView(balloonView)
                show().window?.setBackgroundDrawable(null)
            }
        }
    }

    private fun init() {
        binding.apply {
            button1GS.setOnClickListener(this@CATEGORY_01)
            button2YC.setOnClickListener(this@CATEGORY_01)
            button3GR.setOnClickListener(this@CATEGORY_01)
            button4YDP.setOnClickListener(this@CATEGORY_01)
            button5GC.setOnClickListener(this@CATEGORY_01)
            button6DJ.setOnClickListener(this@CATEGORY_01)
            button7GA.setOnClickListener(this@CATEGORY_01)
            button8SC.setOnClickListener(this@CATEGORY_01)
            button9GN.setOnClickListener(this@CATEGORY_01)
            button10SP.setOnClickListener(this@CATEGORY_01)
            button11GD.setOnClickListener(this@CATEGORY_01)
            button12DB.setOnClickListener(this@CATEGORY_01)
            button13GB.setOnClickListener(this@CATEGORY_01)
            button14NW.setOnClickListener(this@CATEGORY_01)
            button15JN.setOnClickListener(this@CATEGORY_01)
            button16MP.setOnClickListener(this@CATEGORY_01)
            button17GJ.setOnClickListener(this@CATEGORY_01)
            button18DDM.setOnClickListener(this@CATEGORY_01)
            button19SB.setOnClickListener(this@CATEGORY_01)
            button20JL.setOnClickListener(this@CATEGORY_01)
            button21SD.setOnClickListener(this@CATEGORY_01)
            button22YS.setOnClickListener(this@CATEGORY_01)
            button23J.setOnClickListener(this@CATEGORY_01)
            button24SDM.setOnClickListener(this@CATEGORY_01)
            button25EP.setOnClickListener(this@CATEGORY_01)
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent(context, CATEGORY_01_01::class.java)
        startActivity(intent)

        binding.apply {
            when(v?.id) {
                button1GS.id -> intent
                button25EP.id -> intent
                button3GR.id -> intent
                button4YDP.id -> intent
                button5GC.id -> intent
                button6DJ.id -> intent
                button7GA.id -> intent
                button8SC.id -> intent
                button9GN.id -> intent
                button10SP.id -> intent
                button11GD.id -> intent
                button12DB.id -> intent
                button13GB.id -> intent
                button14NW.id -> intent
                button15JN.id -> intent
                button16MP.id -> intent
                button17GJ.id -> intent
                button18DDM.id -> intent
                button19SB.id -> intent
                button20JL.id -> intent
                button21SD.id -> intent
                button22YS.id -> intent
                button23J.id -> intent
                button24SDM.id -> intent
                button25EP.id -> intent
            }
        }
    }

    private fun gridData(): MutableList<GridItem> {
        val gridData = mutableListOf<GridItem>()
        gridData.add(GridItem(R.drawable.icon_hansik, getString(R.string.category1)))
        gridData.add(GridItem(R.drawable.icon_japan,getString(R.string.category2)))
        gridData.add(GridItem(R.drawable.icon_china,getString(R.string.category3)))
        gridData.add(GridItem(R.drawable.icon_food,getString(R.string.category4)))
        gridData.add(GridItem(R.drawable.icon_wash,getString(R.string.category5)))
        gridData.add(GridItem(R.drawable.icon_beauty,getString(R.string.category6)))
        gridData.add(GridItem(R.drawable.icon_hotel,getString(R.string.category7)))
        gridData.add(GridItem(R.drawable.icon_store,getString(R.string.category8)))

        return gridData
    }
}