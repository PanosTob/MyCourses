package com.panostob.mycourses.data.courses.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class MyCourseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("image_url") val imageUrl: String,
    val title: String,
    val shortDescription: String,
    val progressPercentage: Float
)