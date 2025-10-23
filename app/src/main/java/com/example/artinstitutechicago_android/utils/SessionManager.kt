package com.example.artinstitutechicago_android.utils

import android.content.Context

class SessionManager(context: Context) {
    val sharedPreferences = context.getSharedPreferences("picture.session", Context.MODE_PRIVATE)

    fun setFavorite(claveId: String, pictureId: String) {
        val editor = sharedPreferences.edit()
//        editor.putString("FAVORITE_HOROSCOPE_ID", pictureId)
        editor.putString("$claveId", pictureId)
        editor.apply()
    }

    fun getFavorite(claveId: String): String {
        return sharedPreferences.getString("$claveId", "")!!
    }

    fun isFavorite(pictureId: String): Boolean {
        return pictureId == getFavorite(pictureId)
    }
}