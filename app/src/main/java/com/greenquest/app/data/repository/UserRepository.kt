package com.greenquest.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.greenquest.app.data.model.CleanupEvent
import com.greenquest.app.data.model.IssueReport
import com.greenquest.app.data.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) {
    private val usersRef = database.getReference("users")
    private val currentFirebaseUser get() = auth.currentUser

    fun getCurrentUserId(): String? = currentFirebaseUser?.uid

    fun getCurrentUserData(): Flow<User?> = callbackFlow {
        val userId = getCurrentUserId() ?: run {
            trySend(null)
            return@callbackFlow
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.toUser(userId)
                trySend(user)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        usersRef.child(userId).addValueEventListener(listener)
        awaitClose { usersRef.child(userId).removeEventListener(listener) }
    }

    suspend fun observeUserData(userId: String): Flow<User?> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.toUser(userId))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        usersRef.child(userId).addValueEventListener(listener)
        awaitClose { usersRef.child(userId).removeEventListener(listener) }
    }

    suspend fun addPoints(points: Int): Boolean {
        val userId = getCurrentUserId() ?: return false
        return try {
            database.reference.runTransaction(object : Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    val currentUser = mutableData.child(userId).getValue(User::class.java) ?: User()
                    val currentPoints = currentUser.totalPoints
                    val newPoints = currentPoints + points
                    val newLevel = calculateLevel(newPoints)

                    val updatedUser = currentUser.copy(
                        totalPoints = newPoints,
                        level = newLevel
                    )

                    mutableData.child(userId).value = updatedUser
                    return Transaction.success(mutableData)
                }

                override fun onComplete(error: DatabaseError?, committed: Boolean, currentData: DataSnapshot?) {
                    // Transaction completed
                }
            })
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun calculateLevel(points: Int): Int {
        var level = 1
        var required = 100L
        while (points >= required) {
            level++
            required = (required * 1.5).toLong()
        }
        return level
    }

    suspend fun submitCleanupEvent(
        title: String,
        description: String,
        location: String,
        dateTime: Long,
        maxParticipants: Int,
        organizerPhone: String
    ): Boolean {
        return try {
            val userId = getCurrentUserId() ?: return false
            val userEmail = currentFirebaseUser?.email
            val eventRef = database.getReference("cleanupEvents").push()

            val event = CleanupEvent(
                id = eventRef.key ?: return false,
                organizerUid = userId,
                organizerEmail = userEmail,
                organizerPhone = organizerPhone,
                title = title,
                description = description,
                location = location,
                dateTime = dateTime,
                maxParticipants = maxParticipants,
                currentParticipants = listOf(userId)
            )

            eventRef.setValue(event).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun DataSnapshot.toUser(userId: String): User? {
        return try {
            getValue(User::class.java)?.copy(
                id = userId,
                email = child("email").getValue(String::class.java) ?: "",
                displayName = child("displayName").getValue(String::class.java) ?: "Eco Warrior",
                photoUrl = child("photoUrl").getValue(String::class.java),
                totalPoints = child("totalPoints").getValue(Int::class.java) ?: 0,
                level = child("level").getValue(Int::class.java) ?: 1
            )
        } catch (e: Exception) {
            null
        }
    }
}