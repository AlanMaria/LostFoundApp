Lost & Found Android Application

Overview

The Lost & Found Android Application was developed using Kotlin and SQLite in Android Studio. The purpose of this application is to help users report lost or found items and allow other users to view these adverts. The application demonstrates core Android development concepts such as SQLite database integration, RecyclerView implementation, image uploading, category filtering, timestamp management, and multi-activity navigation.


Features

The application includes the following features:

- Create Lost or Found adverts
- Upload images for each advert
- Store advert information using SQLite database
- Display adverts using RecyclerView
- Filter adverts by category
- Automatically generate timestamps
- View detailed advert information
- Remove adverts after items are resolved
- Custom modern UI using XML drawable resources



Technologies Used

The following technologies and tools were used during development:

- Kotlin
- Android Studio
- SQLite Database
- RecyclerView
- XML Layouts
- Drawable XML Resources
- Intents and Activity Navigation
- Android Image Picker API


Application Screens

1. Home Screen
The home screen contains:
- Create Advert button
- Show Lost & Found Items button

This screen acts as the main navigation point of the application.



2. Add Advert Screen

The Add Advert screen allows users to:
- Select Lost or Found
- Enter item details
- Select category
- Upload image
- Save advert into SQLite database

Validation checks were added to prevent empty fields and missing images.



3. RecyclerView Screen

The RecyclerView screen displays all saved adverts dynamically from SQLite.

Each advert card displays:
- Item image
- Advert title
- Category
- Location
- Timestamp

Category filtering was also implemented using a Spinner.



4. Advert Detail Screen

The detail screen displays:
- Full advert information
- Uploaded image
- Phone number
- Description
- Remove advert button

Users can delete adverts after the item has been found or returned.

Database Implementation

SQLite was used as the local database solution.

The DatabaseHelper.kt class handles:
- Database creation
- Table creation
- Insert operations
- Data retrieval
- Category filtering
- Delete functionality

The database table stores:
- Advert ID
- Post Type
- Item Name
- Phone Number
- Description
- Category
- Timestamp
- Location
- Image URI



RecyclerView Implementation

RecyclerView was implemented to efficiently display all adverts.

The AdvertAdapter.kt class:
- Creates advert card layouts
- Binds SQLite data to RecyclerView
- Displays images and timestamps
- Handles advert click events


Image Upload Functionality

Users can upload images using the Android image picker.

Uploaded image URIs are stored in SQLite and displayed later inside RecyclerView and the advert detail screen.

URI permission handling was implemented to prevent image access crashes.


UI Design

The user interface was designed using:
- XML layouts
- Custom drawable XML files
- Rounded cards
- Styled buttons
- Custom input backgrounds



How to Run the Application

1. Open the project in Android Studio
2. Sync Gradle files
3. Connect an Android device or emulator
4. Run the application


