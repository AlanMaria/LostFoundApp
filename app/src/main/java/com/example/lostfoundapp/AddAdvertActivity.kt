package com.example.lostfoundapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddAdvertActivity : AppCompatActivity() {

    private lateinit var postTypeRadioGroup: RadioGroup
    private lateinit var itemNameInput: EditText
    private lateinit var phoneNumberInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var previewImageView: ImageView
    private lateinit var uploadImageButton: Button
    private lateinit var saveAdvertButton: Button

    private lateinit var databaseHelper: DatabaseHelper


    private var selectedImageUri: Uri? = null


    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { imageUri ->

            if (imageUri != null) {
                selectedImageUri = imageUri

                // This keeps permission to read the image later after saving the URI in SQLite.
                contentResolver.takePersistableUriPermission(
                    imageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                // shows image in the preview box.
                previewImageView.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_add_advert)

        databaseHelper = DatabaseHelper(this)

        // Connect XML views to Kotlin variables.
        postTypeRadioGroup = findViewById(R.id.rgPostType)
        itemNameInput = findViewById(R.id.etName)
        phoneNumberInput = findViewById(R.id.etPhone)
        descriptionInput = findViewById(R.id.etDescription)
        locationInput = findViewById(R.id.etLocation)
        categorySpinner = findViewById(R.id.spinnerCategory)
        previewImageView = findViewById(R.id.imgPreview)
        uploadImageButton = findViewById(R.id.btnSelectImage)
        saveAdvertButton = findViewById(R.id.btnSave)

        // These are the categories for  spinner
        val categoryList = arrayOf(
            "Electronics",
            "Pets",
            "Wallets",
            "Keys",
            "Bags",
            "Documents",
            "Other"
        )


        categorySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categoryList
        )

        // This opens the gallery picker
        uploadImageButton.setOnClickListener {
            imagePicker.launch(arrayOf("image/*"))
        }


        saveAdvertButton.setOnClickListener {
            saveAdvert()
        }
    }


    private fun saveAdvert() {

        val selectedPostTypeId = postTypeRadioGroup.checkedRadioButtonId


        if (selectedPostTypeId == -1) {
            Toast.makeText(this, "Please select Lost or Found", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedPostType = findViewById<RadioButton>(selectedPostTypeId).text.toString()
        val itemName = itemNameInput.text.toString().trim()
        val phoneNumber = phoneNumberInput.text.toString().trim()
        val itemDescription = descriptionInput.text.toString().trim()
        val itemLocation = locationInput.text.toString().trim()
        val selectedCategory = categorySpinner.selectedItem.toString()


        if (
            itemName.isEmpty() ||
            phoneNumber.isEmpty() ||
            itemDescription.isEmpty() ||
            itemLocation.isEmpty()
        ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }


        if (selectedImageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show()
            return
        }


        val currentDateTime = SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date())


        val newAdvert = Advert(
            id = 0,
            postType = selectedPostType,
            name = itemName,
            phone = phoneNumber,
            description = itemDescription,
            category = selectedCategory,
            dateTime = currentDateTime,
            location = itemLocation,
            imageUri = selectedImageUri.toString()
        )


        val isInserted = databaseHelper.insertAdvert(newAdvert)

        if (isInserted) {
            Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show()


            finish()
        } else {
            Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show()
        }
    }
}