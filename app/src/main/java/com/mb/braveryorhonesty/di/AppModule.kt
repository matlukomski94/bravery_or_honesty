package com.mb.braveryorhonesty.di

import android.content.Context
import com.mb.braveryorhonesty.data.PlayerDataStore
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
}