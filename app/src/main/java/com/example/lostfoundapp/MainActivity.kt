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

        // This connects the Kotlin file with XML
        setContentView(R.layout.activity_main)


        createAdvertButton = findViewById(R.id.btnCreateAdvert)
        showItemsButton = findViewById(R.id.btnShowItems)

        // Onclick of this button, the app opens the create advert screen.
        createAdvertButton.setOnClickListener {
            startActivity(Intent(this, AddAdvertActivity::class.java))
        }

        // Onclick of this button, the app opens the list of lost and found items.
        showItemsButton.setOnClickListener {
            startActivity(Intent(this, ViewAdvertsActivity::class.java))
        }
    }
}