package com.example.teststori.domain.repository

import android.net.Uri
import com.example.teststori.domain.model.Balance
import com.example.teststori.domain.model.Movement
import com.google.firebase.auth.FirebaseUser

interface Repository {
    suspend fun getRegistration(
        email: String, password: String
    ): Boolean

    suspend fun updateUserUri(
        uri: Uri?
    ): String?

    suspend fun updateUser(
        nameLastName: String
    ): Boolean

    suspend fun getLogin(email: String, password: String): String

    suspend fun getUser(): FirebaseUser?
    suspend fun getBalance(): Balance?

    suspend fun getMovements(): List<Movement?>?
}