package com.panostob.mycourses.domain.courses.entity

sealed class UpdateCourseResult {
    class Success : UpdateCourseResult()
    class Error : UpdateCourseResult()
}