package com.example.lostfoundapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//class controls the SQL

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "LostFoundDB", null, 1) {


    override fun onCreate(database: SQLiteDatabase) {


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

        // runs the SQL command above.
        database.execSQL(createTableQuery)
    }


    // it deletes the old table and creates a fresh one.
    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS adverts")
        onCreate(database)
    }

    // This function saves a new advert into the SQLite database.
    fun insertAdvert(advert: Advert): Boolean {


        val database = writableDatabase


        val advertValues = ContentValues()

        // Each value is matched with the correct database column.
        advertValues.put("postType", advert.postType)
        advertValues.put("name", advert.name)
        advertValues.put("phone", advert.phone)
        advertValues.put("description", advert.description)
        advertValues.put("category", advert.category)
        advertValues.put("dateTime", advert.dateTime)
        advertValues.put("location", advert.location)
        advertValues.put("imageUri", advert.imageUri)


        val insertResult = database.insert("adverts", null, advertValues)

        // If insertResult is not -1, then it means that the advert was saved successfully.
        return insertResult != -1L
    }


    fun getAllAdverts(): ArrayList<Advert> {

        val advertList = ArrayList<Advert>()


        val database = readableDatabase


        val advertCursor = database.rawQuery(
            "SELECT * FROM adverts ORDER BY id DESC",
            null
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


    fun getAdvertsByCategory(selectedCategory: String): ArrayList<Advert> {

        val advertList = ArrayList<Advert>()
        val database = readableDatabase

        .
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