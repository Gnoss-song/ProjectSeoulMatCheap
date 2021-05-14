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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Item
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0101Binding


class MY_01_01 : AppCompatActivity() {
    private lateinit var binding: ActivityMy0101Binding
    private lateinit var itemData: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)

        binding = ActivityMy0101Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //데이터 테스트
        val itemData = mutableListOf<Item>()
        with(itemData){
            add(Item(R.drawable.solip, "솔잎식당", "서울특별시 마포구 마포대로4길 46 (도화동)", "한식", "1.1km", "4.8"))
            add(Item(R.drawable.poonyeon, "풍년갈비", "서울특별시 마포구 동교로 264 (연남동", "한식", "1.4km", "4.4"))
            add(Item(R.drawable.wellbeing, "웰빙뚝배기", "서울특별시 마포구 동교로12길 21 (서교동)", "한식", "2.2km", "3.3"))
            add(Item(R.drawable.western, "웨스턴후라이드라이스", "서울특별시 마포구 홍익로 26 (동교동)", "일식", "0.5km", "4.5"))
            add(Item(R.drawable.halbum, "할범탕수육", "서울특별시 양천구 목동중앙북로 15 (목동)", "분식", "4.4km", "5.0"))
        }
        //백버튼
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            setTitle(R.string.myfavorite_title)
        }
        //리사이클러뷰 어댑터 연결
        val adapter = InformDetailAdapter(itemData,this)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

        //구분선
//        val  dividerItemDecoration =  DividerItemDecoration(binding.recycler.context, LinearLayoutManager(this).orientation)
//        binding.recycler.addItemDecoration(dividerItemDecoration)

        //화면 이동 MY_01_01_01
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this,MY_01_01_01::class.java)
            startActivity(intent)
        }
    }
    //백버튼 활성화
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //어댑터
    inner class InformDetailAdapter (
        private val itemList: MutableList<Item>,
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
                marketIV.setImageResource(itemData.marketIV)
                name.text = itemData.name
                address.text = itemData.address
                distance.text = itemData.distance
                score.text = itemData.score
                sort.text = itemData.sort
            }
            holder.itemView.setOnClickListener {
                val target = Intent(owner, INFORM_02::class.java)
                target.putExtra("marketIV", itemData.marketIV)
                target.putExtra("name", itemData.name)
                target.putExtra("address", itemData.address)
                target.putExtra("distance", itemData.distance)
                target.putExtra("score", itemData.score)
                target.putExtra("sort", itemData.sort)
                owner.startActivity(target)
            }
        }
        override fun getItemCount() = itemList.size
    }
}