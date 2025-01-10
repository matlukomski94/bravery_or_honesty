package com.mb.braveryorhonesty.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.mb.braveryorhonesty.data.db.AppDatabase
import com.mb.braveryorhonesty.data.db.version.DatabaseVersionDao
import com.mb.braveryorhonesty.data.db.version.DatabaseVersionRepository
import com.mb.braveryorhonesty.data.network.FirebaseModule
import com.mb.braveryorhonesty.data.player.PlayerDataStore
import com.mb.braveryorhonesty.utils.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSettingsDataStore(
        @ApplicationContext context: Context,
    ): SettingsDataStore {
        return SettingsDataStore(context)
    }

    @Singleton
    @Provides
    fun providePlayerDataStore(@ApplicationContext context: Context): PlayerDataStore {
        return PlayerDataStore(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseModule(): FirebaseModule = FirebaseModule()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideDatabaseVersionDao(database: AppDatabase): DatabaseVersionDao {
        return database.databaseVersionDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseVersionRepository(
        dao: DatabaseVersionDao,
        firebaseModule: FirebaseModule
    ): DatabaseVersionRepository {
        return DatabaseVersionRepository(dao, firebaseModule)
    }
}