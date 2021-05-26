package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.GridItem
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentCategory01Binding
import kr.co.mapo.project_seoulmatcheap.system.KEY
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.activity.CATEGORY_01_01
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
            adapter = GridRecyclerViewAdapter(gridData(), owner)
        }
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMain.apply {
            inflateMenu(R.menu.category_toolbar)
            setOnMenuItemClickListener {
                val mHelpView =
                    LayoutInflater.from(owner).inflate(R.layout.balloon_view, null)
                val mBuilder = androidx.appcompat.app.AlertDialog.Builder(owner).setView(mHelpView)
                val mAlertDialog = mBuilder.show().apply {
                    window?.setBackgroundDrawable(null)
                }
                val okButton = mHelpView.findViewById<Button>(R.id.btn_ok)
                okButton.setOnClickListener {
                    mAlertDialog.dismiss()
                }
                true
            }
        }
        binding.categoryLocationIV.setOnClickListener {
            Toast.makeText(context, "현재위치를 가져오는 중입니다.", Toast.LENGTH_SHORT).show()
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
        binding.apply {
            when(v?.id) {
                button1GS.id -> intent.putExtra(KEY,"강서구")
                button2YC.id -> intent.putExtra(KEY,"양천구")
                button3GR.id -> intent.putExtra(KEY,"구로구")
                button4YDP.id -> intent.putExtra(KEY,"영등포구")
                button5GC.id -> intent.putExtra(KEY,"금천구")
                button6DJ.id -> intent.putExtra(KEY,"동작구")
                button7GA.id -> intent.putExtra(KEY,"관악구")
                button8SC.id -> intent.putExtra(KEY,"서초구")
                button9GN.id -> intent.putExtra(KEY,"강남구")
                button10SP.id -> intent.putExtra(KEY,"송파구")
                button11GD.id -> intent.putExtra(KEY,"강동구")
                button12DB.id -> intent.putExtra(KEY,"도봉구")
                button13GB.id -> intent.putExtra(KEY,"강북구")
                button14NW.id -> intent.putExtra(KEY,"노원구")
                button15JN.id -> intent.putExtra(KEY,"중랑구")
                button16MP.id -> intent.putExtra(KEY,"마포구")
                button17GJ.id -> intent.putExtra(KEY,"광진구")
                button18DDM.id -> intent.putExtra(KEY,"동대문구")
                button19SB.id -> intent.putExtra(KEY,"성북구")
                button20JL.id -> intent.putExtra(KEY,"종로구")
                button21SD.id -> intent.putExtra(KEY,"성동구")
                button22YS.id -> intent.putExtra(KEY,"용산구")
                button23J.id -> intent.putExtra(KEY,"중구")
                button24SDM.id -> intent.putExtra(KEY,"서대문구")
                button25EP.id -> intent.putExtra(KEY,"은평구")
            }
        }
        startActivity(intent)
    }

    private fun gridData(): MutableList<GridItem> {
        val gridData = mutableListOf<GridItem>()
        gridData.add(GridItem(R.drawable.icon_hansik, getString(R.string.category1)))
        gridData.add(GridItem(R.drawable.icon_china,getString(R.string.category3)))
        gridData.add(GridItem(R.drawable.icon_japan,getString(R.string.category2)))
        gridData.add(GridItem(R.drawable.icon_food,getString(R.string.category4)))
        gridData.add(GridItem(R.drawable.icon_beauty,getString(R.string.category6)))
        gridData.add(GridItem(R.drawable.icon_wash,getString(R.string.category5)))
        gridData.add(GridItem(R.drawable.icon_hotel,getString(R.string.category7)))
        gridData.add(GridItem(R.drawable.icon_store,getString(R.string.category8)))

        return gridData
    }
}