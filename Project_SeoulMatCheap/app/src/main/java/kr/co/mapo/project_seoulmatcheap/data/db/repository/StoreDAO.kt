package kr.co.mapo.project_seoulmatcheap.data.db.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.mapo.project_seoulmatcheap.data.db.FavoritEntity
import kr.co.mapo.project_seoulmatcheap.data.db.MenuEntity
import kr.co.mapo.project_seoulmatcheap.data.db.StoreEntity

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-24
 * @desc
 */

@Dao
interface StoreDAO {

    //INSERT --------------------
    //데이터삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(input: StoreEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(input: MenuEntity)

    //찜 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favoritEntity: FavoritEntity)

    //DELETE ----------------------
    //찜에서 제거
    @Query("DELETE FROM store_favorite WHERE id = :id")
    suspend fun deleteFavorite(id : Int)


    //SELECT -------------------
    //모든 식당목록 출력
    @Query("SELECT * FROM store_inform ORDER BY score ASC")
    fun getAllStore() : LiveData<List<StoreEntity>>
    @Query("SELECT * FROM store_inform")
    fun getTotalStore() : List<StoreEntity>
    //모든 메뉴 출력
    @Query("SELECT * FROM store_menu")
    fun getAllMenu() : LiveData<List<MenuEntity>>
    @Query("SELECT * FROM store_menu")
    fun getTotalMenu() : List<MenuEntity>

    //구 이름에 따라 식당목록 출력
    @Query("SELECT * FROM store_inform WHERE gu = :gu ORDER BY score DESC")
    fun getGuStore(gu: String) : LiveData<List<StoreEntity>>

    //업종에 따라 식당목록 출력
    @Query("SELECT * FROM store_inform WHERE sort = :sort ORDER BY score DESC")
    fun getSortStore(sort: Int) : LiveData<List<StoreEntity>>

    //업소 상세정보 요청
    @Query("SELECT * FROM store_inform WHERE id = :id")
    fun getStoreDetail(id: Int) : List<StoreEntity>

    //업소 상세정보 요청
    @Query("SELECT * FROM store_inform WHERE name = :name")
    fun getStoreDetailName(name: String) : List<StoreEntity>

    //자동완성목록요청
    @Query("SELECT name FROM store_inform")
    fun getAutoComplete() : LiveData<List<String>>

    //검색요청
    @Query("SELECT * FROM store_inform WHERE name LIKE :word ")
    fun searchStore(word : String) : List<StoreEntity>

    //식당의 메뉴 목록 출력
    @Query("SELECT * FROM store_menu WHERE id = :id")
    fun getMenu(id: Int) : LiveData<List<MenuEntity>>

    //찜여부 판단 요청
    @Query("SELECT id FROM store_favorite WHERE id = :id")
    fun isFavorite(id: Int) : LiveData<List<Int>>

    //찜목록 요청
    @Query("SELECT * FROM store_favorite")
    fun getFavorite() : LiveData<List<FavoritEntity>>

    @Query("SELECT id FROM store_favorite")
    fun getFavoriteIdList() : LiveData<List<Int>>


}