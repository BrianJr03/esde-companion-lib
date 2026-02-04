package jr.brian.esdecompanionlib.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jr.brian.esdecompanionlib.data.database.dao.LaunchBoxDao
import jr.brian.esdecompanionlib.data.database.dao.LoudnessDao
import jr.brian.esdecompanionlib.data.database.dao.MediaOverrideDao
import jr.brian.esdecompanionlib.data.database.entities.LaunchBoxGame
import jr.brian.esdecompanionlib.data.database.entities.LaunchBoxImage
import jr.brian.esdecompanionlib.data.database.entities.LoudnessMetadata
import jr.brian.esdecompanionlib.data.database.entities.MediaOverride

@Database(
    entities = [
        LoudnessMetadata::class,
        MediaOverride::class,
        LaunchBoxGame::class,
        LaunchBoxImage::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loudnessDao(): LoudnessDao
    abstract fun mediaOverrideDao(): MediaOverrideDao
    abstract fun launchBoxDao(): LaunchBoxDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "esde_companion_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
