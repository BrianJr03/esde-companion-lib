package jr.brian.esdecompanionlib.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jr.brian.esdecompanionlib.data.database.entities.LoudnessMetadata
import kotlinx.coroutines.flow.Flow

@Dao
interface LoudnessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(metadata: LoudnessMetadata)

    @Query("SELECT * FROM loudness_metadata WHERE filePath = :filePath")
    suspend fun getByFilePath(filePath: String): LoudnessMetadata?

    @Query("SELECT * FROM loudness_metadata")
    fun getAllFlow(): Flow<List<LoudnessMetadata>>

    @Query("DELETE FROM loudness_metadata WHERE filePath = :filePath")
    suspend fun deleteByFilePath(filePath: String)

    @Query("DELETE FROM loudness_metadata")
    suspend fun deleteAll()
}
