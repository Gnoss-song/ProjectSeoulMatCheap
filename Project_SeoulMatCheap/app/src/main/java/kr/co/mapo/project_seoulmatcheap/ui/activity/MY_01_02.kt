package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
                Model(Model.IMAGE_TYPE2, "모르겠어집", "2021.03.15", 4.5f,R.drawable.solip,R.drawable.ic_homeaddress,null,getText(R.string.review2).toString()),
                Model(Model.IMAGE_TYPE3, "맛있어요집", "2021.03.10", 4.0f,R.drawable.poonyeon,R.drawable.custom_checkbox,R.drawable.solip,getText(R.string.review3).toString()),
                Model(Model.IMAGE_TYPE3, "1번입니다", "2021.03.07", 2.0f,R.drawable.poonyeon,R.drawable.solip,R.drawable.wellbeing,getText(R.string.review4).toString()),
                Model(Model.IMAGE_TYPE, "2번입니다", "2021.02.10", 3.5f,R.drawable.wellbeing,null,null,getText(R.string.review5).toString()),
                Model(Model.IMAGE_TYPE2, "3번입니다", "2021.01.15", 3.0f,R.drawable.halbum,R.drawable.poonyeon,null,getText(R.string.review6).toString()),
                Model(Model.IMAGE_TYPE2, "4번입니다", "2020.12.23", 1.5f,R.drawable.western,R.drawable.western,null,getText(R.string.review7).toString()),
                Model(Model.IMAGE_TYPE, "5번입니다", "2020.11.03", 5.0f,R.drawable.solip,null,null,getText(R.string.review8).toString()),
                Model(Model.IMAGE_TYPE3, "6번입니다", "2020.07.22", 1.0f,R.drawable.poonyeon,R.drawable.solip,R.drawable.poonyeon,getText(R.string.review9).toString()),
                Model(Model.IMAGE_TYPE, "7번입니다", "2020.12.12", 1.5f,R.drawable.wellbeing,null,null,getText(R.string.review10).toString()),
                Model(Model.IMAGE_TYPE, "8번입니다", "2020.03.03", 4.5f,R.drawable.halbum,null,null,getText(R.string.review11).toString()),
                Model(Model.IMAGE_TYPE2, "9번입니다", "2020.01.03", 4.0f,R.drawable.solip,R.drawable.western,null,getText(R.string.review12).toString())
        )
        //아이템 구분선
        val  dividerItemDecoration =
                DividerItemDecoration(binding.recycler3.context, LinearLayoutManager(this).orientation)
        binding.recycler3.addItemDecoration(dividerItemDecoration)

        val adpater = My0102Adapter(list ,this)
        binding.recycler3.layoutManager = LinearLayoutManager(this)
        binding.recycler3.adapter = adpater
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
