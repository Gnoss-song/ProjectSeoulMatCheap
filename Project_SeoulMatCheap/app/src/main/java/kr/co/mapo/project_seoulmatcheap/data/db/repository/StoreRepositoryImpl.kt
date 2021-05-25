package kr.co.mapo.project_seoulmatcheap.data.db.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.co.mapo.project_seoulmatcheap.data.db.FavoritEntity
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity

class StoreRepositoryImpl(private val dao: StoreDAO) : StoreRepository {

    override fun addFavorite(favoritEntity: FavoritEntity) {
        GlobalScope.launch {
            dao.addFavorite(favoritEntity)
            val store = dao.getStoreDetail(favoritEntity.id)[0]
            store.liked = true
            Log.e("[결과]", "$store")
            dao.insertStore(store)
        }
    }

    override fun deleteFavorite(id: List<Int>) {
        GlobalScope.launch {
            for (i in id.indices) {
                dao.deleteFavorite(id[i])
                val store = dao.getStoreDetail(id[i])[0]
                store.liked = false
                Log.e("[결과]", "$store")
                dao.insertStore(store)
            }
        }
    }

    override fun deleteFavorite2(id: List<FavoritEntity>) {
        Log.e("[리스트]", "$id")
        GlobalScope.launch {
            for (i in id.indices) {
                Log.e("[테스트]", "2")
                dao.deleteFavorite(id[i].id)
                val store = dao.getStoreDetail(id[i].id)[0]
                store.liked = false
                Log.e("[결과]", "$store")
                dao.insertStore(store)
            }
        }
    }
}