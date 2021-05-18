package kr.co.mapo.project_seoulmatcheap.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.ActivityInform02Binding
import kr.co.mapo.project_seoulmatcheap.ui.adpater.InfromReviewAdapter


class INFORM_02 : AppCompatActivity() {

    private lateinit var binding : ActivityInform02Binding
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInform02Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        with(supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            title = null
            setHomeAsUpIndicator(R.drawable.ic_back_icon)
        }
        with(findViewById<RecyclerView>(R.id.reviewRecyclerView)) {
            layoutManager = LinearLayoutManager(this@INFORM_02, LinearLayoutManager.HORIZONTAL, false)
            adapter = InfromReviewAdapter()
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            val intent = Intent(this,INFORM_02_01::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_inform_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.like -> {
                isChecked = if(isChecked) {
                    item.setIcon(R.drawable.ic_favorite_off)
                    false
                } else {
                    item.setIcon(R.drawable.ic_favorite_on)
                    true
                }
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
    }

}