package kr.co.mapo.project_seoulmatcheap.ui.activity
/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-04-20
 * @desc
 */
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0101Binding
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityMy0103Binding

class MY_01_03 : AppCompatActivity() {

    private lateinit var binding: ActivityMy0103Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_01_01)

        binding = ActivityMy0103Binding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}