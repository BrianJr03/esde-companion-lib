package jr.brian.esdecompanionlib.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import jr.brian.esdecompanionlib.data.model.ContentType

@Entity(tableName = "media_overrides")
data class MediaOverride(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val systemName: String,
    val gameFilename: String?,
    val contentType: ContentType,
    val mediaSlot: Int,
    val customMediaPath: String,
    val timestamp: Long = System.currentTimeMillis()
)
