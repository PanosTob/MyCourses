package com.panostob.mycourses.ui.base.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.panostob.mycourses.R

val openSansFontFamily = FontFamily(
    Font(R.font.open_sans_regular, FontWeight.Normal),
    Font(R.font.open_sans_light, FontWeight.Light),
    Font(R.font.open_sans_medium, FontWeight.Medium),
    Font(R.font.open_sans_semi_bold, FontWeight.SemiBold),
    Font(R.font.open_sans_bold, FontWeight.Bold),
    Font(R.font.open_sans_extra_bold, FontWeight.ExtraBold)
)

val myCoursesTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 32.sp
    ),
    displayMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    labelMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)