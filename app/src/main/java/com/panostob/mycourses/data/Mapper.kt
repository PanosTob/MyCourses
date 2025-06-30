package com.panostob.mycourses.data

import com.panostob.mycourses.BuildConfig

interface Mapper {
    fun mapRemoteImageUrl(imgUrl: String?) = "${BuildConfig.API_MY_COURSES_IMAGES_URL}${imgUrl?.removePrefix("/") ?: ""}"
}