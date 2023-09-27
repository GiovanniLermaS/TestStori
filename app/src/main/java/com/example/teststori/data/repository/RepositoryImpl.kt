package com.example.teststori.data.repository

import android.net.Uri
import com.example.teststori.domain.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : Repository {

    override suspend fun getRegistration(
        email: String,
        password: String
    ): Boolean = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            continuation.resume(true)
        }.addOnFailureListener {
            continuation.resume(false)
        }
    }

    override suspend fun updateUser(nameLastName: String): Boolean =
        suspendCoroutine { continuation ->
            firebaseAuth.currentUser?.let { user ->
                val profileUpdates =
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(nameLastName)
                        .build()
                user.updateProfile(profileUpdates).addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener {
                    continuation.resume(false)
                }
            }
        }

    override suspend fun updateUserUri(uri: Uri?): Boolean =
        suspendCoroutine { continuation ->
            firebaseAuth.currentUser?.let { user ->
                val profileUpdates =
                    UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri)
                        .build()
                user.updateProfile(profileUpdates).addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener {
                    continuation.resume(false)
                }
            }
        }

    override suspend fun getLogin(email: String, password: String): String =
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                continuation.resume("")
            }.addOnFailureListener {
                continuation.resume(it.message ?: "")
            }
        }

    override suspend fun getUser(): FirebaseUser? =
        firebaseAuth.currentUser
}