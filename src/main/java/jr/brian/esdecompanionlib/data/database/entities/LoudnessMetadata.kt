package jr.brian.esdecompanionlib.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loudness_metadata")
data class LoudnessMetadata(
    @PrimaryKey
    val filePath: String,
    val lufs: Double,
    val peak: Double,
    val normalizedGain: Float,
    val timestamp: Long = System.currentTimeMillis()
)
