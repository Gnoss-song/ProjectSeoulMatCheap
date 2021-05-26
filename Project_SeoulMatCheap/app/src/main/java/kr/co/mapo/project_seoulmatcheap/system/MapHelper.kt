package kr.co.mapo.project_seoulmatcheap.system

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import com.naver.maps.map.overlay.OverlayImage
import kr.co.mapo.project_seoulmatcheap.R

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-14
 * @desc 지도 오버레이 컴포넌트 객체 헬퍼 오브젝트
 */

object MapHelper {

    //color
    val blackColor = Color.BLACK
    val whiteColor = Color.WHITE
    val unClickedColor = ColorStateList.valueOf(Color.WHITE)
    val seoulColor = ColorStateList.valueOf(Color.parseColor("#0d62d1"))
    val matColor = ColorStateList.valueOf(Color.parseColor("#ff0000"))
    val likeColor = ColorStateList.valueOf(Color.parseColor("#F25D3D"))
    val circleColor = Color.parseColor("#1AF25D3D")
    val mainColor = Color.parseColor("#F25D3D")

    //Map
    val map_marker = OverlayImage.fromResource(R.drawable.map_marker)
    val map_gps_marker = OverlayImage.fromResource(R.drawable.map_gps)
    val icon_hansik = OverlayImage.fromResource(R.drawable.icon_hansik)
    val icon_china = OverlayImage.fromResource(R.drawable.icon_china)
    val icon_japan = OverlayImage.fromResource(R.drawable.icon_japan)
    val icon_food = OverlayImage.fromResource(R.drawable.icon_food)
    val icon_beauty = OverlayImage.fromResource(R.drawable.icon_beauty)
    val icon_wash = OverlayImage.fromResource(R.drawable.icon_wash)
    val icon_hotel = OverlayImage.fromResource(R.drawable.icon_hotel)
    val icon_store = OverlayImage.fromResource(R.drawable.icon_store)

    val bold = Typeface.DEFAULT_BOLD

    fun getLat(gu : String?) : Double = when(gu) {
        "종로구" ->	37.5727906
        "노원구" ->	37.6542584
        "동작구" ->	37.512434
        "영등포구" ->   37.526344
        "서대문구" ->   37.579177
        "광진구" ->	37.5385375
        "용산구" ->	37.5325938
        "중구" ->	37.5146471
        "송파구" ->	37.5144161
        "중랑구" ->	37.6060713
        "구로구" ->	37.4954745
        "마포구" ->	37.6485276
        "강북구" ->	37.6482758
        "강남구" ->	37.4969117
        "양천구" ->	37.516955
        "금천구" ->	37.4567709
        "도봉구" ->	37.6662953
        "성북구" ->	37.5893702
        "동대문구" ->	  37.5744197
        "은평구" ->	37.6175735
        "관악구" ->	37.4781327
        "강서구" ->	37.5673518
        "서초구" ->	37.4835782
        "강동구" ->	37.5302997
        "성동구" ->	37.5509095
        else -> SeoulMatCheap.getInstance().x
    }

    fun getLng(gu : String?) : Double = when(gu) {
        "종로구" ->	126.9769891
        "노원구" ->	127.0419498
        "동작구" ->	126.937611
        "영등포구" ->   126.8612371
        "서대문구" ->   126.9345928
        "광진구" ->	127.0801885
        "용산구" ->	126.9878542
        "중구" ->	127.0360278
        "송파구" ->	127.0360279
        "중랑구" ->	127.0916155
        "구로구" ->	126.8854504
        "마포구" ->	126.8763234
        "강북구" ->	126.9813874
        "강남구" ->	127.03292
        "양천구" ->	126.8643757
        "금천구" ->	126.8932118
        "도봉구" ->	126.9948531
        "성북구" ->	127.0145543
        "동대문구" ->	 127.037554
        "은평구" ->	126.8805005
        "관악구" ->	126.9493137
        "강서구" ->	126.7883912
        "서초구" ->	127.0304723
        "강동구" ->	127.1210551
        "성동구" ->	127.0233856
        else -> SeoulMatCheap.getInstance().y
    }

}