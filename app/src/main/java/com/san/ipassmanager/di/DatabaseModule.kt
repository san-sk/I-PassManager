package com.san.ipassmanager.di

import android.content.Context
import androidx.room.Room
import com.san.ipassmanager.room.dao.AllCredentialDao
import com.san.ipassmanager.room.database.CredentialsDatabase
import com.san.ipassmanager.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    //database module

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext application: Context): CredentialsDatabase {

        return Room.databaseBuilder(
            application,
            CredentialsDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    fun provideCredentialDao(database: CredentialsDatabase): AllCredentialDao = database.credentialDao()


}