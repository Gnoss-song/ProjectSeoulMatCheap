package kr.co.mapo.project_seoulmatcheap.system

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import com.naver.maps.map.overlay.OverlayImage
import kr.co.mapo.project_seoulmatcheap.R

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-14
 * @desc 지도 오버레이 컴포넌트 객체 헬퍼 오브젝트
 */

object MapHelper {

    val map_marker = OverlayImage.fromResource(R.drawable.map_marker)

    val blackColor = Color.BLACK
    val whiteColor = Color.WHITE
    val clickedColor = ColorStateList.valueOf(Color.BLACK)
    val unClickedColor = ColorStateList.valueOf(Color.WHITE)
    val seoulColor = ColorStateList.valueOf(Color.parseColor("#0d62d1"))
    val matColor = ColorStateList.valueOf(Color.parseColor("#ff0000"))
    val likeColor = ColorStateList.valueOf(Color.parseColor("#FFE600"))
    val circleColor = Color.parseColor("#1AF25D3D")

    val icon_hansik = OverlayImage.fromResource(R.drawable.icon_hansik)
    val icon_china = OverlayImage.fromResource(R.drawable.icon_china)
    val icon_japan = OverlayImage.fromResource(R.drawable.icon_japan)
    val icon_food = OverlayImage.fromResource(R.drawable.icon_food)
    val icon_beauty = OverlayImage.fromResource(R.drawable.icon_beauty)
    val icon_wash = OverlayImage.fromResource(R.drawable.icon_wash)
    val icon_hotel = OverlayImage.fromResource(R.drawable.icon_hotel)
    val icon_store = OverlayImage.fromResource(R.drawable.icon_store)

}