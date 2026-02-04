package jr.brian.esdecompanionlib.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WidgetPagination(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    onPageChange: (Int) -> Unit,
    primaryColor: Color = Color(0xFF6200EE),
    inactiveColor: Color = Color.White.copy(alpha = 0.3f)
) {
    if (totalPages <= 1) return

    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous button
        IconButton(
            onClick = { 
                if (currentPage > 0) {
                    onPageChange(currentPage - 1)
                }
            },
            enabled = currentPage > 0
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Previous",
                tint = if (currentPage > 0) Color.White else inactiveColor
            )
        }

        // Page indicators
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(totalPages) { index ->
                PageIndicator(
                    isActive = index == currentPage,
                    onClick = { onPageChange(index) },
                    activeColor = primaryColor,
                    inactiveColor = inactiveColor
                )
            }
        }

        // Next button
        IconButton(
            onClick = { 
                if (currentPage < totalPages - 1) {
                    onPageChange(currentPage + 1)
                }
            },
            enabled = currentPage < totalPages - 1
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Next",
                tint = if (currentPage < totalPages - 1) Color.White else inactiveColor
            )
        }
    }
}

@Composable
private fun PageIndicator(
    isActive: Boolean,
    onClick: () -> Unit,
    activeColor: Color,
    inactiveColor: Color
) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(if (isActive) activeColor else inactiveColor)
            .clickable(onClick = onClick)
    )
}
