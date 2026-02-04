package jr.brian.esdecompanionlib.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jr.brian.esdecompanionlib.data.database.entities.MediaOverride
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaOverrideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(override: MediaOverride)

    @Query("SELECT * FROM media_overrides WHERE systemName = :systemName")
    fun getBySystemFlow(systemName: String): Flow<List<MediaOverride>>

    @Query("SELECT * FROM media_overrides WHERE systemName = :systemName AND gameFilename = :gameFilename")
    suspend fun getBySystemAndGame(systemName: String, gameFilename: String?): List<MediaOverride>

    @Query("SELECT * FROM media_overrides")
    fun getAllFlow(): Flow<List<MediaOverride>>

    @Delete
    suspend fun delete(override: MediaOverride)

    @Query("DELETE FROM media_overrides WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM media_overrides")
    suspend fun deleteAll()
}
