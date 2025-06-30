package com.panostob.mycourses.data.courses.model

data class RemoteCourse (
    val id: Long,
    val imageUrl: String?,
    val title: String?,
    val shortDescription: String?,
    val progressPercentage: Float?
)
