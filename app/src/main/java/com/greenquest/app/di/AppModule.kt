package com.greenquest.app.di

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import androidx.room.Room
import com.greenquest.app.data.local.AppDatabase
import com.greenquest.app.data.local.DatabaseInitializer
import com.greenquest.app.data.local.dao.ActionDao
import com.greenquest.app.data.prefs.ProfileDataStore
import com.greenquest.app.data.repo.ActionRepository
import com.greenquest.app.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "greenquest.db"
        ).build()

    @Provides
    @Singleton
    fun provideActionDao(db: AppDatabase): ActionDao = db.actionDao()

    @Provides
    @Singleton
    fun provideActionRepository(dao: ActionDao): ActionRepository {
        return ActionRepository(dao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: com.google.firebase.auth.FirebaseAuth,
        database: FirebaseDatabase
    ): UserRepository {
        return UserRepository(auth, database)
    }

    @Provides
    @Singleton
    fun provideProfileDataStore(@ApplicationContext context: Context): ProfileDataStore {
        return ProfileDataStore(context)
    }

    @Provides
    @Singleton
    fun provideDatabaseInitializer(database: FirebaseDatabase): DatabaseInitializer {
        return DatabaseInitializer(database)
    }
}
