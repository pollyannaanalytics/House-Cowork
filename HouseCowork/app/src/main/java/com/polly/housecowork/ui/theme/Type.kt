package com.polly.housecowork.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import com.polly.housecowork.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Nunito Sans")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)

// Set of Material typography styles to start with
val HCWTypo = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W900,
        fontSize = 40.sp,
        fontFamily = fontFamily
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        fontFamily = fontFamily
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        fontFamily = fontFamily
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        fontFamily = fontFamily
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        fontFamily = fontFamily
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        fontFamily = fontFamily
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        fontFamily = fontFamily
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        fontFamily = fontFamily
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        fontFamily = fontFamily
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = fontFamily
    )
)