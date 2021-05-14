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

        binding.button1GS.setOnClickListener(this)
        binding.button2YC.setOnClickListener(this)
        binding.button3GR.setOnClickListener(this)
        binding.button4YDP.setOnClickListener(this)
        binding.button5GC.setOnClickListener(this)
        binding.button6DJ.setOnClickListener(this)
        binding.button7GA.setOnClickListener(this)
        binding.button8SC.setOnClickListener(this)
        binding.button9GN.setOnClickListener(this)
        binding.button10SP.setOnClickListener(this)
        binding.button11GD.setOnClickListener(this)
        binding.button12DB.setOnClickListener(this)
        binding.button13GB.setOnClickListener(this)
        binding.button14NW.setOnClickListener(this)
        binding.button15JN.setOnClickListener(this)
        binding.button16MP.setOnClickListener(this)
        binding.button17GJ.setOnClickListener(this)
        binding.button18DDM.setOnClickListener(this)
        binding.button19SB.setOnClickListener(this)
        binding.button20JL.setOnClickListener(this)
        binding.button21SD.setOnClickListener(this)
        binding.button22YS.setOnClickListener(this)
        binding.button23J.setOnClickListener(this)
        binding.button24SDM.setOnClickListener(this)
        binding.button25EP.setOnClickListener(this)

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
                setView(balloonView)
                show().window?.setBackgroundDrawable(null)
            }
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent(context, CATEGORY_01_01::class.java)
        startActivity(intent)

        when(v?.id) {
            binding.button1GS.id -> intent
            binding.button2YC.id -> intent
            binding.button3GR.id -> intent
            binding.button4YDP.id -> intent
            binding.button5GC.id -> intent
            binding.button6DJ.id -> intent
            binding.button7GA.id -> intent
            binding.button8SC.id -> intent
            binding.button9GN.id -> intent
            binding.button10SP.id -> intent
            binding.button11GD.id -> intent
            binding.button12DB.id -> intent
            binding.button13GB.id -> intent
            binding.button14NW.id -> intent
            binding.button15JN.id -> intent
            binding.button16MP.id -> intent
            binding.button17GJ.id -> intent
            binding.button18DDM.id -> intent
            binding.button19SB.id -> intent
            binding.button20JL.id -> intent
            binding.button21SD.id -> intent
            binding.button22YS.id -> intent
            binding.button23J.id -> intent
            binding.button24SDM.id -> intent
            binding.button25EP.id -> intent
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