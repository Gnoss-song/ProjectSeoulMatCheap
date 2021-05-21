package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.ScaleRatingBar
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.Model
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0102Binding
import kr.co.mapo.project_seoulmatcheap.ui.adpater.My0102Adapter


class MY_01_02 : AppCompatActivity() {
    private lateinit var binding: ActivityMy0102Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMy0102Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //백버튼
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
            setTitle(R.string.myreview_title)
        }
        //데이터 테스트
        val list = mutableListOf(
                Model(Model.IMAGE_TYPE, "성수칼국수", "2021.04.03", 3.5f,R.drawable.halbum,null,null,getText(R.string.review1).toString()),
                Model(Model.IMAGE_TYPE, "모르겠어집", "2021.03.15", 4.5f,R.drawable.solip,null,null,getText(R.string.review2).toString()),
                Model(Model.IMAGE_TYPE, "맛있어요집", "2021.03.10", 4.0f,R.drawable.poonyeon,null,null,getText(R.string.review3).toString()),
                Model(Model.IMAGE_TYPE, "1번입니다", "2021.03.07", 2.0f,R.drawable.poonyeon,null,null,getText(R.string.review4).toString()),
                Model(Model.IMAGE_TYPE, "2번입니다", "2021.02.10", 3.5f,R.drawable.wellbeing,null,null,getText(R.string.review5).toString()),
                Model(Model.IMAGE_TYPE, "3번입니다", "2021.01.15", 3.0f,R.drawable.halbum,null,null,getText(R.string.review6).toString()),
                Model(Model.IMAGE_TYPE, "4번입니다", "2020.12.23", 1.5f,R.drawable.western,null,null,getText(R.string.review7).toString()),
                Model(Model.IMAGE_TYPE, "5번입니다", "2020.11.03", 5.0f,R.drawable.solip,null,null,getText(R.string.review8).toString()),
                Model(Model.IMAGE_TYPE, "6번입니다", "2020.07.22", 1.0f,R.drawable.poonyeon,null,R.drawable.poonyeon,getText(R.string.review9).toString()),
                Model(Model.IMAGE_TYPE, "7번입니다", "2020.12.12", 1.5f,R.drawable.wellbeing,null,null,getText(R.string.review10).toString()),
                Model(Model.IMAGE_TYPE, "8번입니다", "2020.03.03", 4.5f,R.drawable.halbum,null,null,getText(R.string.review11).toString()),
                Model(Model.IMAGE_TYPE, "9번입니다", "2020.01.03", 4.0f,R.drawable.solip,null,null,getText(R.string.review12).toString())
        )
        //아이템 구분선
        val  dividerItemDecoration =
                DividerItemDecoration(binding.recycler3.context, LinearLayoutManager(this).orientation)
        binding.recycler3.addItemDecoration(dividerItemDecoration)

        val adpater = My0102Adapter(list ,this)
        binding.recycler3.layoutManager = LinearLayoutManager(this)
        binding.recycler3.adapter = adpater
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

    /*
    //어댑터
    inner class My0102Adapter(
        private val list: MutableList<Model>,
        private val owner : Activity
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        //아이템 제거
        private fun removeItem (position: Int){
            if(position>=0) {
                list.removeAt(position)
                notifyDataSetChanged()
            }
        }
        inner class ImageTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
            val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
            var rate : ScaleRatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
            val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
            val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
            val delete : Button = itemView.findViewById(R.id.btn_delete)
            val modify : Button = itemView.findViewById(R.id.btn_modify)
        }
        inner class ImageType2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
            val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
            var rate : ScaleRatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
            val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
            var image2 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV2)
            val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
            val delete : Button = itemView.findViewById(R.id.btn_delete)
            val modify : Button = itemView.findViewById(R.id.btn_modify)
        }
        inner class ImageType3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.recycler_item_review_title)
            val date : TextView = itemView.findViewById(R.id.recycler_item_review_date)
            var rate : ScaleRatingBar = itemView.findViewById(R.id.recycler_item_review_rating)
            val image : ImageView = itemView.findViewById(R.id.recycler_item_review_IV)
            var image2 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV2)
            var image3 : ImageView = itemView.findViewById(R.id.recycler_item_review_IV3)
            val review : TextView = itemView.findViewById(R.id.recycler_item_review_content)
            val delete : Button = itemView.findViewById(R.id.btn_delete)
            val modify : Button = itemView.findViewById(R.id.btn_modify)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View?
            return when (viewType) {
                Model.IMAGE_TYPE -> {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_review01_01, parent, false)
                    ImageTypeViewHolder(view)
                }
                Model.IMAGE_TYPE2 -> {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_review02_01, parent, false)
                    ImageType2ViewHolder(view)
                }
                Model.IMAGE_TYPE3 -> {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_review03_01, parent, false)
                    ImageType3ViewHolder(view)
                }
                else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
            }
        }
        override fun getItemCount(): Int {
            return list.size
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
            val obj = list[position]
            when (obj.type) {
                Model.IMAGE_TYPE -> {
                    (holder as ImageTypeViewHolder).title.text = obj.title
                    holder.date.text = obj.date
                    holder.rate.rating = obj.ratingBar
                    holder.image.setImageResource(obj.IV)
                    holder.review.text = obj.review
                    holder.delete.setOnClickListener {
                        val mDeleteView = LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_delete, null)
                        val mBuilder = androidx.appcompat.app.AlertDialog.Builder(owner).setView(mDeleteView)
                        val mAlertDialog = mBuilder.show().apply {
                            window?.setBackgroundDrawable(null)
                        }
                        val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
                        val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
                        okButton.setOnClickListener {
                            Toast.makeText(owner, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                            mAlertDialog.dismiss()
                            removeItem(position)
                        }
                        cancelButton.setOnClickListener {
                            Toast.makeText(owner, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                            mAlertDialog.dismiss()
                        }
                    }
                    holder.modify.setOnClickListener {
                        val target= Intent(owner,INFORM_02_02_01::class.java)
                        target.putExtra("title",obj.title)
                        target.putExtra("date",obj.date)
                        target.putExtra("rate",obj.ratingBar)
                        target.putExtra("image",obj.IV)
                        target.putExtra("review",obj.review)
                        owner.startActivity(target)
                    }
                }
                Model.IMAGE_TYPE2 -> {
                    (holder as ImageType2ViewHolder).title.text = obj.title
                    holder.date.text = obj.date
                    holder.rate.rating = obj.ratingBar
                    holder.image.setImageResource(obj.IV)
                    holder.image2.setImageResource(obj.IV2!!)
                    holder.review.text = obj.review
                    holder.delete.setOnClickListener {
                        val mDeleteView =
                            LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_delete, null)
                        val mBuilder =
                            androidx.appcompat.app.AlertDialog.Builder(owner).setView(mDeleteView)
                        val mAlertDialog = mBuilder.show()
                        mAlertDialog.window?.setBackgroundDrawable(null)
                        val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
                        val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
                        okButton.setOnClickListener {
                            Toast.makeText(owner, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                            mAlertDialog.dismiss()
                            removeItem(position)
                        }
                        cancelButton.setOnClickListener {
                            Toast.makeText(owner, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                            mAlertDialog.dismiss()
                        }
                    }
                    holder.modify.setOnClickListener {
                        val target= Intent(owner,INFORM_02_02_01::class.java)
                        target.putExtra("title",obj.title)
                        target.putExtra("date",obj.date)
                        target.putExtra("rate",obj.ratingBar)
                        target.putExtra("image",obj.IV)
                        target.putExtra("image2",obj.IV2)
                        target.putExtra("review",obj.review)
                        owner.startActivity(target)
                    }
                }
                Model.IMAGE_TYPE3 -> {
                    (holder as ImageType3ViewHolder).title.text = obj.title
                    holder.date.text = obj.date
                    holder.rate.rating = obj.ratingBar
                    holder.image.setImageResource(obj.IV)
                    holder.image2.setImageResource(obj.IV2!!)
                    holder.image3.setImageResource(obj.IV3!!)
                    holder.review.text = obj.review
                    holder.delete.setOnClickListener() {
                        val mDeleteView =
                            LayoutInflater.from(owner).inflate(R.layout.fragment_dialog_my_delete, null)
                        val mBuilder =
                            androidx.appcompat.app.AlertDialog.Builder(owner).setView(mDeleteView)
                        val mAlertDialog = mBuilder.show()
                        mAlertDialog.window?.setBackgroundDrawable(null)
                        val okButton = mDeleteView.findViewById<Button>(R.id.btn_delete_ok)
                        val cancelButton = mDeleteView.findViewById<Button>(R.id.btn_delete_no)
                        okButton.setOnClickListener {
                            Toast.makeText(owner, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                            mAlertDialog.dismiss()
                            removeItem(position)
                        }
                        cancelButton.setOnClickListener {
                            Toast.makeText(owner, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                            mAlertDialog.dismiss()
                        }
                    }
                    holder.modify.setOnClickListener {
                        val target= Intent(owner,INFORM_02_02_01::class.java)
                        target.putExtra("title",obj.title)
                        target.putExtra("date",obj.date)
                        target.putExtra("rate",obj.ratingBar)
                        target.putExtra("image",obj.IV)
                        target.putExtra("image2",obj.IV2)
                        target.putExtra("image3",obj.IV3)
                        target.putExtra("review",obj.review)
                        owner.startActivity(target)
                    }
                }
            }
        }
        override fun getItemViewType(position: Int): Int {
            return list[position].type
        }
    }
     */
}
