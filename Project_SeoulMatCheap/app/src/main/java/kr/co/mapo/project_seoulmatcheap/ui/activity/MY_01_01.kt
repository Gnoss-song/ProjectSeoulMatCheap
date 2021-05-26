package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Item
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.FavoritEntity
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0101Binding
import kr.co.mapo.project_seoulmatcheap.system.KEY
import kr.co.mapo.project_seoulmatcheap.system.STORE
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InformDetailAdapter
import java.io.Serializable


class MY_01_01 : AppCompatActivity() {
    private lateinit var binding: ActivityMy0101Binding
    private lateinit var favoritData : List<FavoritEntity>
    private lateinit var adapter: InformDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)

        binding = ActivityMy0101Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //백버튼
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            setTitle(R.string.myfavorite_title)
        }

        with(binding) {
            recycler.layoutManager = LinearLayoutManager(this@MY_01_01)
            AppDatabase(this@MY_01_01)!!.storeDAO().getFavorite().observe(this@MY_01_01, {
                favoritData = it
                adapter = InformDetailAdapter(favoritData,this@MY_01_01)
                recycler.adapter = adapter
                supportActionBar!!.setTitle("찜 목록 (${it.size})")
            })
        }
        binding.recycler.layoutManager = LinearLayoutManager(this@MY_01_01)


        //화면 이동 MY_01_01_01
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this,MY_01_01_01::class.java)
            intent.putExtra(KEY,favoritData as Serializable)
            startActivity(intent)
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //어댑터
    inner class InformDetailAdapter (
        private val itemList: List<FavoritEntity>,
        private val owner : AppCompatActivity
    ) : RecyclerView.Adapter<InformDetailAdapter.ViewHolderClass>() {

        inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val marketIV: ImageView = itemView.findViewById(R.id.marketIV)
            val name: TextView = itemView.findViewById(R.id.name)
            val address: TextView = itemView.findViewById(R.id.address)
            val distance: TextView = itemView.findViewById(R.id.distance)
            val score: TextView = itemView.findViewById(R.id.score)
            val sort: TextView = itemView.findViewById(R.id.sort)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.inform_01, parent, false
            )
            return ViewHolderClass(view)
        }
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val itemData = itemList[position]
            with(holder) {
                Glide.with(owner).load(itemData.photo).into(marketIV)
                name.text = itemData.name
                address.text = itemData.address
                distance.text = SeoulMatCheap.getInstance().calculateDistance(itemData.lat, itemData.lng)
                score.text = "0.0"
                sort.text = itemData.category
            }
            holder.itemView.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val item = AppDatabase(this@MY_01_01)!!.storeDAO().getStoreDetail(itemData.id)
                    val target = Intent(owner, INFORM_02::class.java)
                    target.putExtra(STORE, item[0])

                    owner.startActivity(target)
                }
            }
        }
        override fun getItemCount() = itemList.size
    }
}