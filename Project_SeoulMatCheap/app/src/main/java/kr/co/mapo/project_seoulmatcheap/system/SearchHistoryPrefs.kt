package kr.co.mapo.project_seoulmatcheap.system

import android.app.Application
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-20
 * @desc 검색어히스토리 sharedPrefs 관리 object
 */

object SearchHistoryPrefs {

    private val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA)

    fun saveSearchWord(context: Context, word : String) {
        val currentDateTime = Calendar.getInstance().time
        context.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
            .edit()
            .putString(word, dateFormat.format(currentDateTime))
            .apply()
    }

    fun removeSearchWord(context: Context, word: String) {
        context.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
            .edit()
            .remove(word)
            .apply()
    }

    fun getSearchHistory(context: Context) : MutableList<String> {
        val map = context.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE).all
        val sorted = map.toList().sortedWith(compareByDescending { it.second as Comparable<*>? }).toMap()
        return sorted.keys.toMutableList()
    }
}