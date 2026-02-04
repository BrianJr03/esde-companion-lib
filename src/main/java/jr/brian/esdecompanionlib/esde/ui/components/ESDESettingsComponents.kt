package jr.brian.esdecompanionlib.esde.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(
    text: String,
    primaryColor: Color = Color(0xFF6200EE)
) {
    Text(
        text = text,
        color = primaryColor,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun SliderSetting(
    title: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    valueText: String,
    onValueChange: (Float) -> Unit,
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = valueText,
                color = primaryColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            colors = SliderDefaults.colors(
                thumbColor = primaryColor,
                activeTrackColor = primaryColor,
                inactiveTrackColor = Color.White.copy(alpha = 0.2f)
            )
        )
    }
}

@Composable
fun ToggleSetting(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
    showToggle: Boolean = true,
    onClick: (() -> Unit)? = null,
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

    Row(
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
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick?.invoke() ?: onCheckedChange(!checked) }
            .focusable()
            .onFocusChanged { isFocused = it.isFocused }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        if (showToggle) {
            Spacer(modifier = Modifier.width(12.dp))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = primaryColor,
                    checkedTrackColor = secondaryColor.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }
    }
}
