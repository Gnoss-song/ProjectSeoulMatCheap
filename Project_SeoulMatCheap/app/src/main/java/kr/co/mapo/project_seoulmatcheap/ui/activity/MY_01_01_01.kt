package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.db.AppDatabase
import kr.co.mapo.project_seoulmatcheap.data.db.FavoritEntity
import kr.co.mapo.project_seoulmatcheap.data.db.repository.StoreRepositoryImpl
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy010101Binding
import kr.co.mapo.project_seoulmatcheap.databinding.Inform0101Binding
import kr.co.mapo.project_seoulmatcheap.system.KEY
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap

class MY_01_01_01 : AppCompatActivity() {
    private lateinit var binding: ActivityMy010101Binding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMy010101Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val itemData = intent.getSerializableExtra(KEY) as List<FavoritEntity>
        //백버튼
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            title ="찜 목록 (${itemData.size})"
        }

        //리사이클러뷰 어댑터 연결

        val adapter = My010101Adapter(itemData.toMutableList(), this)
        binding.recycler4.adapter = adapter
        binding.recycler4.layoutManager = LinearLayoutManager(this)

        //버튼 클릭
        binding.btnDelete.setOnClickListener {
            val mDeleteView =
                LayoutInflater.from(this).inflate(R.layout.fragment_dialog_my_delete, null)
            val mBuilder = androidx.appcompat.app.AlertDialog.Builder(this).setView(mDeleteView)
            val mAlertDialog = mBuilder.show().apply {
                window?.setBackgroundDrawable(null)
            }
            val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
            val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
            okButton.setOnClickListener {
                Toast.makeText(this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                adapter.requestRemove(click = true)
                mAlertDialog.dismiss()
            }
            cancelButton.setOnClickListener {
                Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                mAlertDialog.dismiss()
            }
        }
    }

    //백버튼 활성화
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //어댑터
    inner class My010101Adapter(
        private val itemList: MutableList<FavoritEntity>,
        private val owner: AppCompatActivity
    ) : RecyclerView.Adapter<My010101Adapter.ViewHolderClass>() {
        //체크박스 상태 저장
        private val dao = AppDatabase(owner)!!.storeDAO()
        private var checkboxStatus = SparseBooleanArray()

        inner class ViewHolderClass(private val binding: Inform0101Binding) :
            RecyclerView.ViewHolder(binding.root) {

            val marketIV: ImageView = itemView.findViewById(R.id.marketIV)
            val name: TextView = itemView.findViewById(R.id.name)
            val address: TextView = itemView.findViewById(R.id.address)
            val distance: TextView = itemView.findViewById(R.id.distance)
            val score: TextView = itemView.findViewById(R.id.score)
            val sort: TextView = itemView.findViewById(R.id.sort)

            fun bind(itemList:FavoritEntity) = with(binding){
                checkboxUser.isChecked = checkboxStatus[adapterPosition]
                checkboxUser.setOnClickListener {
                    if (!checkboxUser.isChecked)
                        checkboxStatus.put(adapterPosition, false)
                    else
                        itemList.checked = true
                    checkboxStatus.put(adapterPosition, true)
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass =
            ViewHolderClass(
                Inform0101Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val itemData = itemList[position]
            with(holder) {
                Glide.with(owner).load(itemData.photo).into(marketIV)
                name.text = itemData.name
                address.text = itemData.address
                distance.text = SeoulMatCheap.getInstance().calculateDistance(itemData.lat,itemData.lng)
                score.text = "${itemData.score}"
                sort.text = itemData.category

                bind(itemList[position])
            }
        }

        override fun getItemCount() = itemList.size

        //삭제함수
        @RequiresApi(Build.VERSION_CODES.P)
        fun requestRemove(click: Boolean) {

            if (click) {
                var i = 0
                val check = StoreRepositoryImpl(dao)
                val deleteList = itemList.toList()
                while (itemList.size != 0 && i < itemList.size) {
                    if (itemList[i].checked) {
                        itemList.removeAt(i)
                        notifyDataSetChanged()
                    } else
                        i++
                }
                check.deleteFavorite2(deleteList)
            }
            checkboxStatus.clear()
        }
    }
}