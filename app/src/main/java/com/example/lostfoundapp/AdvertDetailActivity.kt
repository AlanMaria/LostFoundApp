package com.example.lostfoundapp

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// This screen shows the full details of one selected advert.
class AdvertDetailActivity : AppCompatActivity() {

    private lateinit var detailImageView: ImageView
    private lateinit var advertDetailsText: TextView
    private lateinit var removeAdvertButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    // This stores the advert ID received from the RecyclerView item click.
    private var selectedAdvertId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this activity to the advert detail XML layout.
        setContentView(R.layout.activity_advert_detail)

        // Connect XML views to Kotlin variables.
        detailImageView = findViewById(R.id.imgDetail)
        advertDetailsText = findViewById(R.id.tvDetails)
        removeAdvertButton = findViewById(R.id.btnRemove)

        databaseHelper = DatabaseHelper(this)

        // Get the advert ID passed from AdvertAdapter.
        selectedAdvertId = intent.getIntExtra("advertId", -1)

        // If the ID is valid, load the selected advert details.
        if (selectedAdvertId != -1) {
            loadSelectedAdvertDetails()
        }

        // Remove advert from database when the item is resolved.
        removeAdvertButton.setOnClickListener {
            val isDeleted = databaseHelper.deleteAdvert(selectedAdvertId)

            if (isDeleted) {
                Toast.makeText(this, "Advert removed", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to remove advert", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // This function loads one advert from SQLite and displays it on the screen.
    private fun loadSelectedAdvertDetails() {

        val selectedAdvert = databaseHelper.getAdvertById(selectedAdvertId)

        if (selectedAdvert != null) {

            // Try to display the uploaded image.
            try {
                detailImageView.setImageURI(Uri.parse(selectedAdvert.imageUri))
            } catch (error: Exception) {
                detailImageView.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            // Triple quotes allow multiline text.
            // trimIndent removes unwanted spaces from the beginning of each line.
            advertDetailsText.text = """
                ${selectedAdvert.postType}: ${selectedAdvert.name}
                
                Category: ${selectedAdvert.category}
                Posted: ${selectedAdvert.dateTime}
                Location: ${selectedAdvert.location}
                Phone: ${selectedAdvert.phone}
                
                Description:
                ${selectedAdvert.description}
            """.trimIndent()
        }
    }
}