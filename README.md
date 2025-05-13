📅 ### Conference Schedule Organizer
📌 Project Overview

Welcome to Conference Schedule Organizer, a Java-based application I built to manage conference events with ease. This app allows you to:

    ✅ Perform CRUD operations (Create, Read, Update, Delete)

    💾 Persist data using PostgreSQL

    🖼️ Interact with a user-friendly JavaFX GUI

    📤 Export data to CSV and JSON

    📊 Generate event summary reports

    🔍 Validate inputs (e.g., time formats, future dates)

I designed the app with a modular structure, separating responsibilities across different classes for better readability, maintainability, and extensibility.
🔧 Technologies Used

    Java 17 – Programming language

    JavaFX – GUI (TableView, forms, alerts)

    PostgreSQL – Persistent data storage

    JDBC – Java Database Connectivity

    org.json – For exporting to JSON

    java.io – For exporting to CSV

📚 Dependencies
Dependency	Purpose
postgresql-42.7.3.jar	JDBC driver for PostgreSQL
json-20230227.jar	Exporting event data to JSON
JavaFX SDK	GUI framework (if not bundled with JDK)

📁 Place .jar files into the lib/ folder and include them in your classpath.
🛠️ Database Setup

    Install PostgreSQL and pgAdmin4

    Create database: Name it postgres

    Create table:

CREATE TABLE events (
id SERIAL PRIMARY KEY,
title VARCHAR(100) NOT NULL,
event_date DATE NOT NULL,
start_time VARCHAR(5) NOT NULL,
end_time VARCHAR(5) NOT NULL,
location VARCHAR(100) NOT NULL,
description TEXT
);

    Configure DB in DatabaseConnection.java:

private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
private static final String USER = "postgres";
private static final String PASSWORD = "vn9282g++2";

🧩 Module Breakdown
🔹 Main.java

    Entry point – launches EventApp.

🔹 Event.java

    Model class for event info (title, date, time, etc.)

    Includes input validation logic:

        ✅ Valid time format

        📅 Future date check

🔹 DatabaseConnection.java

    Connects to PostgreSQL

    Handles all CRUD operations

    Resets ID sequence via resetSequence()

🔹 EventManager.java

    Business logic

    Stores events in-memory (eventLog)

    Syncs DB changes with UI

    Generates summary reports

🔹 FileExporter.java

    Exports event data:

        exportToCSV()

        exportToJSON()

🔹 EventApp.java

    GUI using JavaFX

    Includes:

        TableView (ID, Title, Date, Time)

        Form (Title, DatePicker, Times, Location, Description)

        Buttons: Add, Update, Delete, Report, Export

    Handles input validation and alerts

🖱️ GUI Features

    📋 Left panel – Table of events

    📝 Right panel – Editable form

    ⚙️ Buttons:

        ➕ Add Event: Validates and saves to DB

        ✏️ Update Event: Saves changes to selected event

        ❌ Delete Event: Removes event from DB

        📊 Generate Report: Shows summary dialog

        📁 Export Data: Saves to events.csv and events.json

▶️ Usage Example

    Add Event

        Enter details like "Team Meeting", date > today, 14:00-15:00

        Click Add Event

        ✅ Event appears with ID in the table

    Update Event

        Select it, change title to "Updated Meeting"

        Click Update Event

    Delete Event

        Select and click Delete Event

    Generate Report

        Click to view a quick summary popup

    Export Data

        Click to save current data to .csv and .json
