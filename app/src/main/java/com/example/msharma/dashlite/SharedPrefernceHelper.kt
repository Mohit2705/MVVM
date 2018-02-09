package com.example.msharma.dashlite

import android.content.Context


const val FAV_LIST = "fav_list"

class SharedPrefernceHelper(private val context: Context) {


    fun getFavRestaurents(): Set<String> {
        val ss = context.getSharedPreferences(
                context.resources.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        return ss.getStringSet(FAV_LIST, HashSet<String>())

    }

    fun favoriteRestaurent(id: String) {
        val ss = context.getSharedPreferences(
                context.resources.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val list = getFavRestaurents() as HashSet<String>
        list.add(id)
        val edit = ss.edit()
        edit.clear()
        edit.putStringSet(FAV_LIST, list)
        edit.apply()
    }

    fun unFavRestaurant(id: String) {
        val ss = context.getSharedPreferences(
                context.resources.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val list = getFavRestaurents() as HashSet<String>
        list.remove(id)
        val edit = ss.edit()
        edit.clear()
        edit.putStringSet(FAV_LIST, list)
        edit.apply()
    }
}