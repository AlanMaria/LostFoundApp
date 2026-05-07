package com.example.lostfoundapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// This class controls the SQLite database.
// It creates the database table and also handles saving, reading, filtering, and deleting adverts.
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "LostFoundDB", null, 1) {

    // This function runs automatically the first time the database is created.
    override fun onCreate(database: SQLiteDatabase) {

        // This SQL command creates a table called "adverts".
        // The table stores all the details of lost and found posts.
        val createTableQuery = """
            CREATE TABLE adverts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                postType TEXT,
                name TEXT,
                phone TEXT,
                description TEXT,
                category TEXT,
                dateTime TEXT,
                location TEXT,
                imageUri TEXT
            )
        """

        // This runs the SQL command above.
        database.execSQL(createTableQuery)
    }

    // This function runs if the database version changes.
    // For this simple app, it deletes the old table and creates a fresh one.
    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS adverts")
        onCreate(database)
    }

    // This function saves a new advert into the SQLite database.
    fun insertAdvert(advert: Advert): Boolean {

        // writableDatabase opens the database so data can be inserted.
        val database = writableDatabase

        // ContentValues works like a container that holds the values before inserting.
        val advertValues = ContentValues()

        // Each value is matched with the correct database column name.
        advertValues.put("postType", advert.postType)
        advertValues.put("name", advert.name)
        advertValues.put("phone", advert.phone)
        advertValues.put("description", advert.description)
        advertValues.put("category", advert.category)
        advertValues.put("dateTime", advert.dateTime)
        advertValues.put("location", advert.location)
        advertValues.put("imageUri", advert.imageUri)

        // This inserts the advert into the adverts table.
        val insertResult = database.insert("adverts", null, advertValues)

        // If insertResult is not -1, it means the advert was saved successfully.
        return insertResult != -1L
    }

    // This function gets all adverts from the database.
    fun getAllAdverts(): ArrayList<Advert> {

        val advertList = ArrayList<Advert>()


        val database = readableDatabase

        // This query gets every advert and displays the newest advert first.
        val advertCursor = database.rawQuery(
            "SELECT * FROM adverts ORDER BY id DESC",
            null
        )

        // moveToFirst checks if the database has at least one row.
        if (advertCursor.moveToFirst()) {

            // This loop reads every row from the database.
            do {
                // Each database row is converted into an Advert object.
                val advert = Advert(
                    id = advertCursor.getInt(0),
                    postType = advertCursor.getString(1),
                    name = advertCursor.getString(2),
                    phone = advertCursor.getString(3),
                    description = advertCursor.getString(4),
                    category = advertCursor.getString(5),
                    dateTime = advertCursor.getString(6),
                    location = advertCursor.getString(7),
                    imageUri = advertCursor.getString(8)
                )

                // The advert object is added into the list.
                advertList.add(advert)

            } while (advertCursor.moveToNext())
        }

        // The cursor must be closed after reading data to avoid memory issues.
        advertCursor.close()

        // This list is returned to the RecyclerView screen.
        return advertList
    }

    // This function gets adverts only from the selected category.
    fun getAdvertsByCategory(selectedCategory: String): ArrayList<Advert> {

        val advertList = ArrayList<Advert>()
        val database = readableDatabase

        // The question mark is replaced by the selected category safely.
        val advertCursor = database.rawQuery(
            "SELECT * FROM adverts WHERE category = ? ORDER BY id DESC",
            arrayOf(selectedCategory)
        )

        if (advertCursor.moveToFirst()) {
            do {
                val advert = Advert(
                    id = advertCursor.getInt(0),
                    postType = advertCursor.getString(1),
                    name = advertCursor.getString(2),
                    phone = advertCursor.getString(3),
                    description = advertCursor.getString(4),
                    category = advertCursor.getString(5),
                    dateTime = advertCursor.getString(6),
                    location = advertCursor.getString(7),
                    imageUri = advertCursor.getString(8)
                )

                advertList.add(advert)

            } while (advertCursor.moveToNext())
        }

        advertCursor.close()
        return advertList
    }

    // This function gets one advert using its ID.
    // It is used when the user opens the advert detail page.
    fun getAdvertById(advertId: Int): Advert? {

        val database = readableDatabase

        val advertCursor = database.rawQuery(
            "SELECT * FROM adverts WHERE id = ?",
            arrayOf(advertId.toString())
        )

        var selectedAdvert: Advert? = null

        if (advertCursor.moveToFirst()) {
            selectedAdvert = Advert(
                id = advertCursor.getInt(0),
                postType = advertCursor.getString(1),
                name = advertCursor.getString(2),
                phone = advertCursor.getString(3),
                description = advertCursor.getString(4),
                category = advertCursor.getString(5),
                dateTime = advertCursor.getString(6),
                location = advertCursor.getString(7),
                imageUri = advertCursor.getString(8)
            )
        }

        advertCursor.close()
        return selectedAdvert
    }

    // This function deletes an advert from the database.
    // It is used when the item has been found or returned.
    fun deleteAdvert(advertId: Int): Boolean {

        val database = writableDatabase

        val deleteResult = database.delete(
            "adverts",
            "id = ?",
            arrayOf(advertId.toString())
        )

        // If deleteResult is greater than 0, deletion was successful.
        return deleteResult > 0
    }
}