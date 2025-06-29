package com.panostob.mycourses.util.ext

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import timber.log.Timber

inline fun <reified T> T.toJson(moshi: Moshi): String {
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
    return try {
        jsonAdapter.toJson(this)
    } catch (e: Exception) {
        ""
    }
}

fun <T> String.fromJson(moshi: Moshi, modelClass: Class<T>): T? {
    val jsonAdapter: JsonAdapter<T>? = moshi.adapter(modelClass)

    return try {
        jsonAdapter?.fromJson(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}