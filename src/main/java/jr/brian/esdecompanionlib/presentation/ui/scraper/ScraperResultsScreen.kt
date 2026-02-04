package jr.brian.esdecompanionlib.presentation.ui.scraper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jr.brian.esdecompanionlib.data.model.ScraperGame
import jr.brian.esdecompanionlib.data.model.ScraperImage

@Composable
fun ScraperResultsScreen(
    games: List<ScraperGame>,
    images: List<ScraperImage>,
    onBack: () -> Unit,
    onDownloadImage: (ScraperImage) -> Unit,
    onDownloadAll: () -> Unit,
    modifier: Modifier = Modifier,
    isDownloading: Boolean = false,
    primaryColor: Color = Color(0xFF6200EE),
    backgroundColor: Color = Color(0xFF121212)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Search Results",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onDownloadAll,
                enabled = !isDownloading && images.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    disabledContainerColor = primaryColor.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Download All")
            }
        }

        if (games.isEmpty() && images.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No results found",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (games.isNotEmpty()) {
                    item {
                        Text(
                            text = "Games (${games.size})",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(games) { game ->
                        GameResultItem(
                            game = game,
                            primaryColor = primaryColor
                        )
                    }
                }

                if (images.isNotEmpty()) {
                    item {
                        Text(
                            text = "Images (${images.size})",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }

                    items(images) { image ->
                        ImageResultItem(
                            image = image,
                            onDownload = { onDownloadImage(image) },
                            isDownloading = isDownloading,
                            primaryColor = primaryColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GameResultItem(
    game: ScraperGame,
    primaryColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF1A1A1A))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (game.coverUrl != null) {
            AsyncImage(
                model = game.coverUrl,
                contentDescription = game.title,
                modifier = Modifier
                    .size(60.dp, 80.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = game.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (game.releaseDate != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = game.releaseDate,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            if (game.developer != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = game.developer,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun ImageResultItem(
    image: ScraperImage,
    onDownload: () -> Unit,
    isDownloading: Boolean,
    primaryColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF1A1A1A))
            .clickable(enabled = !isDownloading, onClick = onDownload)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = image.thumbnailUrl ?: image.url,
            contentDescription = image.imageType.name,
            modifier = Modifier
                .size(80.dp, 60.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = image.imageType.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            if (image.width != null && image.height != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${image.width} × ${image.height}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        if (isDownloading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = primaryColor,
                strokeWidth = 2.dp
            )
        } else {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
