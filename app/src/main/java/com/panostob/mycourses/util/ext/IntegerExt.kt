package com.panostob.mycourses.util.ext

import android.content.res.Resources

val Int.pxToDp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()