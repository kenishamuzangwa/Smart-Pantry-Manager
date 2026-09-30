# Smart Pantry Manager

## App Description

Smart Pantry Manager is an Android application developed in Java using Android Studio. The application helps users manage the ingredients they have available in their pantry and reduce food waste.

Users can add, view, edit, and delete pantry items. The application also contains a collection of pre-loaded recipes and uses strict ingredient matching to suggest only recipes that can be prepared using the ingredients currently available in the user's pantry.

The application includes:

* Pantry item management
* Add, edit, and delete functionality
* Persistent local database storage
* Pre-loaded recipe collection
* Strict recipe matching
* Suggested Recipes screen
* Recipe details and preparation methods
* Settings & Profile screen
* Notification preference
* Clear Pantry functionality
* Bottom navigation

## Database

The application uses **Room Persistence Library**, which is built on top of SQLite.

Room was chosen because it provides a structured way to store data locally on the Android device while reducing the amount of database code required. It also provides convenient DAO methods for performing Create, Read, Update, and Delete (CRUD) operations.

The database stores pantry items and recipes and allows the application data to persist when the application is closed and reopened.

## Technologies Used

* Java
* Android Studio
* Android SDK
* Room Persistence Library
* SQLite
* XML layouts
* Git and GitHub

## Requirements

To run the application, you need:

* Android Studio
* Android SDK
* Java Development Kit (JDK)
* An Android emulator or physical Android device

## Setup and Run Instructions

1. Clone or download this repository from GitHub.
2. Open the project in Android Studio.
3. Allow Android Studio to sync the Gradle files.
4. Connect an Android device or start an Android emulator.
5. Click the **Run** button in Android Studio.
6. Select the connected device or emulator.
7. The Smart Pantry Manager application will install and launch.

## Main Application Flow

The main Pantry screen allows the user to add and manage pantry ingredients.

The bottom navigation provides access to:

* **Pantry** — manage pantry ingredients
* **Recipes** — view recipes that can currently be prepared
* **Settings** — manage profile and application preferences

Recipe suggestions follow the application's strict-matching rule. A recipe is only
