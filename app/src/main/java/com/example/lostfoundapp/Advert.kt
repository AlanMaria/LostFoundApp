package com.example.lostfoundapp


data class Advert(
    val id: Int,                 // Unique advert ID created by SQLite
    val postType: String,        // Shows if the item is Lost or Found
    val name: String,
    val phone: String,
    val description: String,
    val category: String,
    val dateTime: String,
    val location: String,
    val imageUri: String         // Stores the selected image path as text
)