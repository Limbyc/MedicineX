package com.valance.medicine.ui.bottomnavigation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


// Theme example of typography implementation
@Composable
internal fun getTypography(): Typography {

//    val monBold =
//        Font(
//            resource = Res.font.mon_bold,
//            weight = FontWeight.Bold,
//            style = FontStyle.Normal,
//        )

//    @Composable
//    fun monFamily() = FontFamily(
//        monThin,
//        monThinItalic,
//        monLight,
//        monLightItalic,
//        monReg,
//        monRegItalic,
//        monSb,
//        monSbItalic,
//        monBold,
//        monBoldItalic,
//    )

    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default, // instead use monFamily()
            fontWeight = FontWeight(510),
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
        ),
    )
}
