package com.example.lostfoundapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ViewAdvertsActivity : AppCompatActivity() {

    private lateinit var advertsRecyclerView: RecyclerView
    private lateinit var categoryFilterSpinner: Spinner
    private lateinit var filterButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var advertRecyclerAdapter: AdvertAdapter

    // Categories shown in the filter spinner.
    private val categoryList = arrayOf(
        "All",
        "Electronics",
        "Pets",
        "Wallets",
        "Keys",
        "Bags",
        "Documents",
        "Other"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_view_adverts)

        databaseHelper = DatabaseHelper(this)

        // Connect XML views to Kotlin variables.
        advertsRecyclerView = findViewById(R.id.recyclerView)
        categoryFilterSpinner = findViewById(R.id.spinnerFilter)
        filterButton = findViewById(R.id.btnFilter)


        advertsRecyclerView.layoutManager = LinearLayoutManager(this)

        // This adds category options into the filter spinner.
        categoryFilterSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categoryList
        )


        loadAllAdverts()


        filterButton.setOnClickListener {
            val selectedCategory = categoryFilterSpinner.selectedItem.toString()

            val advertList = if (selectedCategory == "All") {
                databaseHelper.getAllAdverts()
            } else {
                databaseHelper.getAdvertsByCategory(selectedCategory)
            }

            advertRecyclerAdapter = AdvertAdapter(this, advertList)
            advertsRecyclerView.adapter = advertRecyclerAdapter
        }
    }


    private fun loadAllAdverts() {
        val advertList = databaseHelper.getAllAdverts()

        advertRecyclerAdapter = AdvertAdapter(this, advertList)
        advertsRecyclerView.adapter = advertRecyclerAdapter
    }
}