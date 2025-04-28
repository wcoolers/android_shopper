package com.st991719830.assignment2_adegokeakanbi.data
import com.google.firebase.firestore.DocumentId

data class ShoppingItem(
    @DocumentId val id: String = "",
    val name: String = "",
    val brand: String = "",
    val price: Double = 0.0,
    val userId: String = ""
)
