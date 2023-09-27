package com.example.teststori.di

import android.content.Context
import com.example.teststori.data.repository.RepositoryImpl
import com.example.teststori.domain.repository.Repository
import com.example.teststori.domain.use_case.save_photo.SavePhotoToGalleryUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideSaveGalleryUseCase(@ApplicationContext context: Context): SavePhotoToGalleryUseCase {
        return SavePhotoToGalleryUseCase(context)
    }

    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return RepositoryImpl(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    }
}