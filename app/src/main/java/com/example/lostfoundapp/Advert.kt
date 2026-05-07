package com.example.lostfoundapp


data class Advert(
    val id: Int,                 // Unique advert ID created by SQLite
    val postType: String,        // Shows whether the item is Lost or Found
    val name: String,            // Name of the item
    val phone: String,           // Contact phone number
    val description: String,     // Extra details about the item
    val category: String,        // Item category such as Electronics, Pets, Wallets, etc.
    val dateTime: String,        // Date and time when the advert was posted
    val location: String,        // Place where the item was lost or found
    val imageUri: String         // Stores the selected image path as text
)