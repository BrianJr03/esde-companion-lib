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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jr.brian.esdecompanionlib.R
import jr.brian.esdecompanionlib.data.model.AnimationStyle

@Composable
fun AnimationStyleSelector(
    selectedStyle: AnimationStyle,
    onStyleSelected: (AnimationStyle) -> Unit,
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
            text = stringResource(R.string.esde_settings_animation_style),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AnimationStyle.entries.forEach { style ->
                AnimationStyleChip(
                    style = style,
                    isSelected = style == selectedStyle,
                    onClick = { onStyleSelected(style) },
                    modifier = Modifier.weight(1f),
                    primaryColor = primaryColor,
                    focusScale = focusScale
                )
            }
        }
    }
}

@Composable
private fun AnimationStyleChip(
    style: AnimationStyle,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF6200EE),
    focusScale: Float = 1.05f
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) focusScale else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "chipFocusScale"
    )

    val styleName = when (style) {
        AnimationStyle.None -> stringResource(R.string.esde_settings_animation_none)
        AnimationStyle.Fade -> stringResource(R.string.esde_settings_animation_fade)
        AnimationStyle.ScaleFade -> stringResource(R.string.esde_settings_animation_scale_fade)
        AnimationStyle.Custom -> stringResource(R.string.esde_settings_animation_custom)
    }

    Box(
        modifier = modifier
            .height(40.dp)
            .scale(scale)
            .background(
                color = when {
                    isSelected -> primaryColor.copy(alpha = 0.7f)
                    isFocused -> primaryColor.copy(alpha = 0.3f)
                    else -> Color.White.copy(alpha = 0.1f)
                },
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = if (isSelected || isFocused) 1.dp else 0.dp,
                color = if (isSelected) primaryColor else Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .focusable()
            .onFocusChanged { isFocused = it.isFocused },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = styleName,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
