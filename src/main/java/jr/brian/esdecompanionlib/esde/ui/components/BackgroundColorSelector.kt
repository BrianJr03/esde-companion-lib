package jr.brian.esdecompanionlib.esde.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jr.brian.esdecompanionlib.R

@Composable
fun BackgroundColorSelector(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    primaryColor: Color = Color(0xFF6200EE),
    secondaryColor: Color = Color(0xFF03DAC6),
    cardColorLight: Color = Color(0xFF2A2A2A),
    cardColor: Color = Color(0xFF1A1A1A),
    focusScale: Float = 1.05f
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) focusScale else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "focusScale"
    )

    val colorOptions = remember {
        listOf(
            Color.Black,
            Color(0xFF1A1A2E),
            Color(0xFF16213E),
            Color(0xFF0F3460),
            Color(0xFF1F1F1F),
            Color(0xFF2C2C2C),
            Color(0xFF1E1E1E),
            Color(0xFF121212)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .background(
                brush = Brush.linearGradient(
                    colors = if (isFocused) {
                        listOf(
                            primaryColor.copy(alpha = 0.3f),
                            secondaryColor.copy(alpha = 0.2f)
                        )
                    } else {
                        listOf(cardColorLight, cardColor)
                    }
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = if (isFocused) 2.dp else 0.dp,
                color = if (isFocused) primaryColor.copy(alpha = 0.5f) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .focusable()
            .onFocusChanged { isFocused = it.isFocused }
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.esde_settings_background_color),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            colorOptions.forEach { color ->
                ColorSwatch(
                    color = color,
                    isSelected = color.toArgb() == selectedColor.toArgb(),
                    onClick = { onColorSelected(color) },
                    primaryColor = primaryColor,
                    focusScale = focusScale
                )
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    primaryColor: Color = Color(0xFF6200EE),
    focusScale: Float = 1.05f
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) focusScale else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "swatchFocusScale"
    )

    Box(
        modifier = Modifier
            .size(32.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(color)
            .border(
                width = when {
                    isSelected -> 3.dp
                    isFocused -> 2.dp
                    else -> 1.dp
                },
                color = when {
                    isSelected -> primaryColor
                    isFocused -> Color.White.copy(alpha = 0.5f)
                    else -> Color.White.copy(alpha = 0.2f)
                },
                shape = CircleShape
            )
            .clickable { onClick() }
            .focusable()
            .onFocusChanged { isFocused = it.isFocused },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
