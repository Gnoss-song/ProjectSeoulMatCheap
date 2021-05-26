package kr.co.mapo.project_seoulmatcheap.data.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-24
 * @desc
 */

//식당정보 entity
@Entity(tableName = "store_inform")
data class StoreEntity (

    @PrimaryKey
    val	id : Int,
    val	name : String,
    val	gu : String,
    val	address	: String,
    val sort : Int,
    val category : String,
    val	tel	: String,
    val	time : String,
    val	closed : String,
    val reserve	: String,
    val	parking	: String,
    val	rating_cnt : Int,
    val	photo : String,
    val	lng	: Double,
    val	lat	: Double,
    var liked : Boolean

) : Serializable

//식당 메뉴정보 Entity
@Entity(tableName = "store_menu")
data class MenuEntity (
    @PrimaryKey(autoGenerate = true)
    val seq : Int?,
    val id : Int?,
    val name : String,
    val menu : String,
    val price : Int?
)

//찜목록 정보 Entity
@Entity(tableName = "store_favorite")
data class FavoritEntity(
    @PrimaryKey(autoGenerate = true)
    val seq : Int?,
    val id	: Int,
    val	name : String,
    val	address	: String,
    val sort : Int,
    val category : String,
    val	photo : String,
    val	lng	: Double,
    val	lat	: Double,
    var checked : Boolean = false
) : Serializable
