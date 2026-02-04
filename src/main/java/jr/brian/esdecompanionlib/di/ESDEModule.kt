package jr.brian.esdecompanionlib.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jr.brian.esdecompanionlib.data.database.AppDatabase
import jr.brian.esdecompanionlib.data.database.dao.LaunchBoxDao
import jr.brian.esdecompanionlib.data.database.dao.LoudnessDao
import jr.brian.esdecompanionlib.data.database.dao.MediaOverrideDao
import jr.brian.esdecompanionlib.data.repository.ESDEEventListener
import jr.brian.esdecompanionlib.data.repository.ESDEEventListenerImpl
import jr.brian.esdecompanionlib.data.repository.EventRepository
import jr.brian.esdecompanionlib.data.repository.PreferencesRepository
import javax.inject.Singleton

/**
 * Dagger Hilt module providing ESDE companion dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object ESDEModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(
        @ApplicationContext context: Context
    ): PreferencesRepository {
        return PreferencesRepository(context)
    }

    @Provides
    @Singleton
    fun provideESDEEventListenerImpl(): ESDEEventListenerImpl {
        return ESDEEventListenerImpl()
    }

    @Provides
    @Singleton
    fun provideESDEEventListener(
        impl: ESDEEventListenerImpl
    ): ESDEEventListener {
        return impl
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        eventListener: ESDEEventListener
    ): EventRepository {
        return EventRepository(eventListener)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideLoudnessDao(database: AppDatabase): LoudnessDao {
        return database.loudnessDao()
    }

    @Provides
    @Singleton
    fun provideMediaOverrideDao(database: AppDatabase): MediaOverrideDao {
        return database.mediaOverrideDao()
    }

    @Provides
    @Singleton
    fun provideLaunchBoxDao(database: AppDatabase): LaunchBoxDao {
        return database.launchBoxDao()
    }
}
