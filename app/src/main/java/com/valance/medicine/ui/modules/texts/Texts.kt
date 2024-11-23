package com.valance.medicine.ui.modules.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun HeaderText(
    modifier: Modifier,
    text: String,
    color: Color,
    size: TextUnit)
{
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = size,
            textAlign = TextAlign.Center
        ),
        modifier = modifier
    )
}
