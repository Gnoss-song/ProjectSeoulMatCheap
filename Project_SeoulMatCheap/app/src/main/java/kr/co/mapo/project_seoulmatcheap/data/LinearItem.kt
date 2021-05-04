package kr.co.mapo.project_seoulmatcheap.data

data class LinearItem(val image : Int,
                      val name : String = "",
                      val address : String = "",
                      val kind : String = "",
                      val distance: Double,
                      val rate : Double)