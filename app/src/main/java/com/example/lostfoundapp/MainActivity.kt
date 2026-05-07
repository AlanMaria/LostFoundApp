package com.example.lostfoundapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// This is the home screen of the app.(COMMENTS ARE MOSTLY FOR THE VIDEO PRESENTATION)

class MainActivity : AppCompatActivity() {

    private lateinit var createAdvertButton: Button
    private lateinit var showItemsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This connects the Kotlin file with the home screen XML layout.
        setContentView(R.layout.activity_main)

        // These lines connect the buttons from XML to Kotlin.
        createAdvertButton = findViewById(R.id.btnCreateAdvert)
        showItemsButton = findViewById(R.id.btnShowItems)

        // When the user clicks this button, the app opens the create advert screen.
        createAdvertButton.setOnClickListener {
            startActivity(Intent(this, AddAdvertActivity::class.java))
        }

        // When the user clicks this button, the app opens the list of lost and found items.
        showItemsButton.setOnClickListener {
            startActivity(Intent(this, ViewAdvertsActivity::class.java))
        }
    }
}