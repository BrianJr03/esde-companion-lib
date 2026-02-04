package jr.brian.esdecompanionlib.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import jr.brian.esdecompanionlib.data.database.entities.LaunchBoxGame
import jr.brian.esdecompanionlib.data.database.entities.LaunchBoxImage
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchBoxDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: LaunchBoxGame)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<LaunchBoxGame>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: LaunchBoxImage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<LaunchBoxImage>)

    @Query("SELECT * FROM launchbox_games WHERE platform = :platform")
    fun getGamesByPlatformFlow(platform: String): Flow<List<LaunchBoxGame>>

    @Query("SELECT * FROM launchbox_games WHERE id = :gameId")
    suspend fun getGameById(gameId: String): LaunchBoxGame?

    @Query("SELECT * FROM launchbox_images WHERE gameId = :gameId")
    suspend fun getImagesByGameId(gameId: String): List<LaunchBoxImage>

    @Query("SELECT * FROM launchbox_images WHERE gameId = :gameId AND imageType = :imageType")
    suspend fun getImagesByGameIdAndType(gameId: String, imageType: String): List<LaunchBoxImage>

    @Transaction
    @Query("SELECT * FROM launchbox_games")
    fun getAllGamesFlow(): Flow<List<LaunchBoxGame>>

    @Query("DELETE FROM launchbox_games WHERE id = :gameId")
    suspend fun deleteGame(gameId: String)

    @Query("DELETE FROM launchbox_games")
    suspend fun deleteAllGames()

    @Query("DELETE FROM launchbox_images")
    suspend fun deleteAllImages()
}
