package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0101Binding
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0102Binding
//import kr.co.mapo.project_seoulmatcheap.ui.adpater.My0102Adapter

class MY_01_02 : AppCompatActivity() {
    private lateinit var binding: ActivityMy0102Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_01_01)

        binding = ActivityMy0102Binding.inflate(layoutInflater)
        setContentView(binding.root)


    }

//    val adapter3 = My0102Adapter(itemData)
//    binding.recycler3.adapter = adapter3
//    binding.recycler3.layoutManager = LinearLayoutManager(this)

}