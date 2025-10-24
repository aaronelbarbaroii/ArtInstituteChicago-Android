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
}