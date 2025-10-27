package com.example.artinstitutechicago_android.utils

import android.content.Context

class SessionManager(context: Context) {
    val sharedPreferences = context.getSharedPreferences("picture.session", Context.MODE_PRIVATE)

    fun setFavorite(claveId: String, valueId: String) {
        val editor = sharedPreferences.edit()
//        editor.putString("FAVORITE_HOROSCOPE_ID", pictureId)
        editor.putString("$claveId", valueId)
        editor.apply()
    }

    fun getFavorite(claveId: String): String {
        return sharedPreferences.getString("$claveId", "")!!
    }

    fun isFavorite(calveId: String): Boolean {
        return calveId == getFavorite(calveId)
    }

    fun setPage(page: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("PAGE", page)
        editor.apply()
    }

    fun getPage(): Int {
        return sharedPreferences.getInt("PAGE", 1)
    }

    fun setLimit(limit: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("LIMIT", limit)
        editor.apply()
    }

    fun getLimit(): Int {
        return sharedPreferences.getInt("LIMIT", 10)
    }
}