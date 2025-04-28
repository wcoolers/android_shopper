package com.st991719830.assignment2_adegokeakanbi.data

import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun getItemsRef(): com.google.firebase.firestore.CollectionReference? {
        val userId = auth.currentUser?.uid ?: return null
        return db.collection("users").document(userId).collection("shopping_items")
    }

//    suspend fun addItem(item: ShoppingItem) {
//        val itemsRef = getItemsRef() ?: return
//        val doc = itemsRef.document()
//        val newItem = item.copy(id = doc.id)
//        doc.set(newItem).await()
//    }

    suspend fun deleteItem(itemId: String) {
        val itemsRef = getItemsRef() ?: return
        itemsRef.document(itemId).delete().await()
    }

    suspend fun getAllItems(): List<ShoppingItem> {
        val itemsRef = getItemsRef() ?: return emptyList()
        val snapshot = itemsRef.get().await()
        return snapshot.toObjects(ShoppingItem::class.java)
    }

    suspend fun uploadProfileImage(uri: android.net.Uri?, userId: String): String {
        val storageRef = storage.reference.child("profile_pictures/$userId.jpg")
        if (uri != null) {
            storageRef.putFile(uri).await()
        }
        return storageRef.downloadUrl.await().toString()
    }

    suspend fun getProfileImageUrl(userId: String): String? {
        return try {
            val storageRef = storage.reference.child("profile_pictures/$userId.jpg")
            storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addShoppingItem(item: ShoppingItem) {
        val userId = auth.currentUser?.uid ?: return
        val docRef = db.collection("users")
            .document(userId)
            .collection("shopping_items")
            .document()

        val itemWithId = item.copy(id = docRef.id)
        docRef.set(itemWithId).await()
    }

//    fun getCurrentUser(): FirebaseUser? {
//        return auth.currentUser
//    }

    fun getShoppingItemsFlow(): Flow<List<ShoppingItem>> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            close(Exception("User not logged in"))
            return@callbackFlow
        }

        val collection = db.collection("users")
            .document(userId)
            .collection("shopping_items")

        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                close(error ?: Exception("Snapshot is null"))
                return@addSnapshotListener
            }

            val items = snapshot.toObjects(ShoppingItem::class.java)
            trySend(items)
        }

        awaitClose { listener.remove() }
    }
}
