package kr.co.mapo.project_seoulmatcheap.data.db.repository

import androidx.lifecycle.MutableLiveData
import kr.co.mapo.project_seoulmatcheap.data.db.FavoritEntity
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-25
 * @desc
 */

interface StoreRepository {

    fun addFavorite(favoritEntity: FavoritEntity)
    fun deleteFavorite(id : List<Int>)
    fun deleteFavorite2(id : List<FavoritEntity>)

}