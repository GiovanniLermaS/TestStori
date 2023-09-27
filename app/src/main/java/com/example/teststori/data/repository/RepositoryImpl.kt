package com.example.teststori.data.repository

import android.net.Uri
import com.example.teststori.common.BANK_INFO
import com.example.teststori.common.DETAIL_MOVEMENT
import com.example.teststori.common.ID_MOVEMENT
import com.example.teststori.common.MOVEMENTS
import com.example.teststori.common.USER_ID
import com.example.teststori.domain.model.Balance
import com.example.teststori.domain.model.Detail
import com.example.teststori.domain.model.Movement
import com.example.teststori.domain.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, private val db: FirebaseFirestore
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

    override suspend fun updateUserUri(uri: Uri?): String? =
        suspendCoroutine { continuation ->
            firebaseAuth.currentUser?.let { user ->
                val profileUpdates =
                    UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri)
                        .build()
                user.updateProfile(profileUpdates).addOnSuccessListener {
                    continuation.resume("")
                }.addOnFailureListener {
                    continuation.resume(it.message)
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

    override suspend fun getUser(): FirebaseUser? = firebaseAuth.currentUser

    override suspend fun getBalance(): Balance? = suspendCoroutine { continuation ->
        db.collection(BANK_INFO).document(firebaseAuth.currentUser?.uid ?: "").get()
            .addOnSuccessListener {
                continuation.resume(it.toObject(Balance::class.java) as Balance)
            }.addOnFailureListener {
                continuation.resume(null)
            }
    }

    override suspend fun getMovements(): List<Movement?>? = suspendCoroutine { continuation ->
        db.collection(MOVEMENTS).whereEqualTo(USER_ID, firebaseAuth.currentUser?.uid).get()
            .addOnSuccessListener {
                val movements = ArrayList<Movement?>()
                it.forEach { query ->
                    val movement = query.toObject(Movement::class.java)
                    movement.id = query.id
                    movements.add(movement)
                }
                continuation.resume(movements as List<Movement?>?)
            }.addOnFailureListener {
                continuation.resume(null)
            }
    }

    override suspend fun getDetail(id: String): List<Detail?>? = suspendCoroutine { continuation ->
        db.collection(DETAIL_MOVEMENT).whereEqualTo(ID_MOVEMENT, id).get()
            .addOnSuccessListener {
                val movements = ArrayList<Detail?>()
                it.forEach { query ->
                    val movement = query.toObject(Detail::class.java)
                    movements.add(movement)
                }
                continuation.resume(movements as List<Detail?>?)
            }.addOnFailureListener {
                continuation.resume(null)
            }
    }
}