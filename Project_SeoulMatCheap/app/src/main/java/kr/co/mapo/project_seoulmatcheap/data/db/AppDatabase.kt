package kr.co.mapo.project_seoulmatcheap.data.db

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.data.db.repository.StoreDAO
import kr.co.mapo.project_seoulmatcheap.system.DOWNLOADMENU
import kr.co.mapo.project_seoulmatcheap.system.DOWNLOADSTORE
import kr.co.mapo.project_seoulmatcheap.system.USER_PRESFS
import java.io.InputStream

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-24
 * @desc room db 생성 관리
 */

@Database(
    entities = [StoreEntity::class, MenuEntity::class, FavoritEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storeDAO() : StoreDAO
    companion object {
        @Volatile var instance : AppDatabase? = null
        operator fun invoke(context: Context) : AppDatabase? {
            if(instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "matcheap.db")
                        .fallbackToDestructiveMigration()   //데이터베이스를 한번 생성후 중간에 테이블 변화가 있을 때 과거의 데이터베이스를 무시하고 새로 생성
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
    fun loadStore(context: Context) {   //식당정보 추가하기
        val prefs = context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
        if(!prefs.getBoolean(DOWNLOADMENU, false)) {
            val assetManager = context.resources.assets
            val inputStream = assetManager.open("store.txt")
            inputStream.bufferedReader().readLines().forEach {
                val token = it.split("\t")
                Log.e("[FILE]", "$token")
                val input = StoreEntity(
                    token[0].toInt(),
                    token[1],
                    token[2],
                    token[3],
                    token[4].toInt(),
                    token[5],
                    token[6],
                    token[7],
                    token[8],
                    token[9],
                    token[10],
                    token[11].toInt(),
                    token[12],
                    token[13].toDouble(),
                    token[14].toDouble(),
                    false
                )
                CoroutineScope(Dispatchers.IO).launch {
                    storeDAO().insertStore(input)
                }
            }
            prefs.edit().putBoolean(DOWNLOADSTORE, true).apply()
        }
        CoroutineScope(Dispatchers.IO).launch {
            val output = storeDAO().getTotalStore()
            Log.e("[STORE]", "${output.size}")
        }
    }
    fun loadMenu(context: Context) {    //메뉴정보 추가하기
        val prefs = context.getSharedPreferences(USER_PRESFS, Application.MODE_PRIVATE)
        if(!prefs.getBoolean(DOWNLOADMENU, false)) {
            val assetManager= context.resources.assets
            val inputStream= assetManager.open("menu.txt")
            Log.e("ㅎㅎ", "${prefs.getBoolean(DOWNLOADMENU, true)}")
            inputStream.bufferedReader().readLines().forEach {
                val token = it.split("\t")
                Log.e("[FILE]", "$token")
                val input = MenuEntity(null, token[0].toInt(), token[1], token[2], token[3].toInt())
                CoroutineScope(Dispatchers.IO).launch {
                    storeDAO().insertMenu(input)
                }
            }
            prefs.edit().putBoolean(DOWNLOADMENU, true).apply()
        }
        CoroutineScope(Dispatchers.IO).launch {
            val output = storeDAO().getTotalMenu()
            Log.e("[MENU]", "${output.size}")
        }
    }
}