package jr.brian.esdecompanionlib.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "launchbox_games")
data class LaunchBoxGame(
    @PrimaryKey
    val id: String,
    val title: String,
    val platform: String,
    val releaseDate: String?,
    val developer: String?,
    val publisher: String?,
    val genre: String?,
    val description: String?,
    val communityRating: Float?,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "launchbox_images",
    foreignKeys = [
        ForeignKey(
            entity = LaunchBoxGame::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LaunchBoxImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: String,
    val imageType: String,
    val fileName: String,
    val region: String?,
    val url: String
)
