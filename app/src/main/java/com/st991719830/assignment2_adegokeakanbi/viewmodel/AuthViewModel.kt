package com.st991719830.assignment2_adegokeakanbi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.st991719830.assignment2_adegokeakanbi.data.FirebaseRepository
import com.st991719830.assignment2_adegokeakanbi.data.ShoppingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val repo = FirebaseRepository()

    val user: FirebaseUser? get() = auth.currentUser
    val currentUser: FirebaseUser? get() = auth.currentUser

    private val _shoppingItems = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val shoppingItems: StateFlow<List<ShoppingItem>> = _shoppingItems

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Login failed") }
    }

    fun signup(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Signup failed") }
    }

    fun logout() {
        auth.signOut()
        _shoppingItems.value = emptyList() // Clear list after logout
    }

    fun addShoppingItem(name: String, brand: String, price: Double) {
        val userId = currentUser?.uid ?: return
        val item = ShoppingItem(
            name = name,
            brand = brand,
            price = price,
            userId = userId
        )

        viewModelScope.launch {
            try {
                repo.addShoppingItem(item)
            } catch (e: Exception) {
                Log.e("FeedScreen", "Error adding item", e)
            }
        }
    }

    fun loadShoppingItems() {
        viewModelScope.launch {
            try {
                repo.getShoppingItemsFlow().collect {
                    _shoppingItems.value = it
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error loading items", e)
            }
        }
    }

    fun deleteShoppingItem(item: ShoppingItem) {
        viewModelScope.launch {
            try {
                repo.deleteItem(item.id)
                loadShoppingItems()
            } catch (e: Exception) {
                Log.e("ViewModel", "Error deleting item", e)
            }
        }
    }
}
