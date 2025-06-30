package com.panostob.mycourses.domain.courses.entity

data class Course(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val shortDescription: String,
    val progressPercentage: Float?
)
