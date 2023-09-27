package com.example.teststori.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

interface Repository {
    suspend fun getRegistration(
        email: String, password: String
    ): Boolean

    suspend fun updateUserUri(
        uri: Uri?
    ): Boolean

    suspend fun updateUser(
        nameLastName: String
    ): Boolean

    suspend fun getLogin(email: String, password: String): String

    suspend fun getUser(): FirebaseUser?
}