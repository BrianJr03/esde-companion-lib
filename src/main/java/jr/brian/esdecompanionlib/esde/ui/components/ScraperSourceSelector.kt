package jr.brian.esdecompanionlib.esde.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScraperSourceSelector(
    selectedSource: String,
    onSourceSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF6200EE),
    secondaryColor: Color = Color(0xFF03DAC6),
    cardColor: Color = Color(0xFF1A1A1A)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(cardColor)
            .border(1.dp, primaryColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Preferred Scraper Source",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        
        ScraperSourceOption(
            source = "SteamGridDB",
            selectedSource = selectedSource,
            onSourceSelected = onSourceSelected,
            secondaryColor = secondaryColor
        )
        
        ScraperSourceOption(
            source = "IGDB",
            selectedSource = selectedSource,
            onSourceSelected = onSourceSelected,
            secondaryColor = secondaryColor
        )
    }
}

@Composable
private fun ScraperSourceOption(
    source: String,
    selectedSource: String,
    onSourceSelected: (String) -> Unit,
    secondaryColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSourceSelected(source) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedSource == source,
            onClick = { onSourceSelected(source) },
            colors = RadioButtonDefaults.colors(
                selectedColor = secondaryColor,
                unselectedColor = Color.White.copy(alpha = 0.6f)
            )
        )
        
        Text(
            text = source,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
