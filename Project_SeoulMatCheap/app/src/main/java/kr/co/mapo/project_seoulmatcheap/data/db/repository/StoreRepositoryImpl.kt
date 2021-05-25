package kr.co.mapo.project_seoulmatcheap.data.db.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    override fun deleteFavorite(id: Int) {
        GlobalScope.launch {
            dao.deleteFavorite(id)
            val store = dao.getStoreDetail(id)[0]
            store.liked = false
            Log.e("[결과]", "$store")
            dao.insertStore(store)
        }
    }
}