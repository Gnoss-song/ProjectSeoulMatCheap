package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.MenuEntity
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentInform021Binding


class INFORM_02_1(
    private val item : StoreEntity) : Fragment()  {

    private lateinit var owner: AppCompatActivity
    lateinit var binding : FragmentInform021Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inform_02_1, container, false)
        binding.item = item
        init()
        return binding.root
    }

    private fun init() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textTime.text = if(item?.time == "null") "-" else item?.time
            textClosed.text = if(item?.closed == "null") "-" else item?.closed
            recyclerViewMenu.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                AppDatabase(requireContext())!!.storeDAO().getMenu(item!!.id).observe(viewLifecycleOwner, {
                        adapter = MenuAdapter(it)
                    })
            }

            buttonCall.setOnClickListener {

                val tt = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+item!!.tel))
                startActivity(tt)
            }
            buttonNavi.setOnClickListener {
                val nv = Intent(Intent.ACTION_VIEW, Uri.parse("navermaps://?menu=location&pinType=place&lat=${item!!.lat}&lng=${item!!.lng}&title=${item!!.name}")).apply {
                    `package` = "com.nhn.android.nmap"
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(nv)
            }
        }
    }

    inner class MenuAdapter(private val menuList:List<MenuEntity>) : RecyclerView.Adapter<MenuAdapter.HolderView>() {
        inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val menu_name : TextView = itemView.findViewById(R.id.menu_name)
            val menu_cost : TextView = itemView.findViewById(R.id.menu_cost)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
            return HolderView(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_menu, parent, false))
        }
        override fun onBindViewHolder(holder: HolderView, position: Int) {
            val item = menuList[position]
            with(holder) {
                menu_name.text = item.menu
                menu_cost.text = item.price.toString()
            }
        }
        override fun getItemCount() = menuList.size
    }
}