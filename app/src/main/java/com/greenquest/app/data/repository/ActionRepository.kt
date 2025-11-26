package com.greenquest.app.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.greenquest.app.data.model.Action
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class ActionRepository @Inject constructor(
    private val database: FirebaseDatabase
) {
    private val actionsRef = database.getReference("actions")

    suspend fun getActions(): List<Action> = suspendCancellableCoroutine { continuation ->
        val query = actionsRef.orderByChild("isActive").equalTo(true)
        
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val actions = snapshot.children.mapNotNull { 
                    it.getValue(Action::class.java)?.copy(id = it.key ?: "") 
                }
                continuation.resume(actions)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resume(emptyList())
            }
        })
    }

    suspend fun getActionById(actionId: String): Action? = suspendCancellableCoroutine { continuation ->
        actionsRef.child(actionId).get()
            .addOnSuccessListener { snapshot ->
                val action = snapshot.getValue(Action::class.java)?.copy(id = snapshot.key ?: "")
                continuation.resume(action)
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }

    suspend fun completeAction(userId: String, actionId: String, points: Int): Boolean {
        return try {
            val updates = hashMapOf<String, Any>(
                "users/$userId/completedActions/$actionId" to System.currentTimeMillis(),
                "users/$userId/totalPoints" to com.google.firebase.database.ServerValue.increment(points.toLong())
            )
            
            database.reference.updateChildren(updates).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
