package com.example.teststori.di

import android.content.Context
import com.example.teststori.domain.use_case.save_photo.SavePhotoToGalleryUseCase
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
    fun provideMainRepository(@ApplicationContext context: Context): SavePhotoToGalleryUseCase {
        return SavePhotoToGalleryUseCase(context)
    }
}