📅 Conference Schedule Organizer
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

🧪 Troubles🎯 Conference Schedule Organizer
📌 Project Overview

Hi! I'm excited to share my Conference Schedule Organizer, a Java-based desktop app I built to manage conference events. With it, I can easily create, read, update, and delete (CRUD) events, all stored in a PostgreSQL database.

I designed a user-friendly JavaFX GUI that displays events in a table on the left and includes a form on the right for editing. I also added:

    📤 Export to CSV and JSON

    📊 Report generation

    ✅ Input validation (e.g., time format, future dates)

I kept the code modular, using separate classes for different functionalities, which makes it clean and extendable.
📦 Dependencies I Used

To enhance the functionality, I added a few key libraries:

    PostgreSQL JDBC Driver postgresql-42.7.3.jar
    🔗 Download

    JSON Library json-20230227.jar
    🔗 Download

    JavaFX SDK
    🔗 Download

I placed the .jar files in a lib/ folder and made sure they were included in the classpath.
🛠 Technologies I Used

    Java 17 – Core programming language

    JavaFX – GUI framework

    PostgreSQL – Persistent database

    JDBC – For database communication

    org.json – JSON export

    java.io – CSV export

🗄 Database Setup

    Install PostgreSQL and pgAdmin4.

    Create a new database (I named mine postgres).

    Create the events table by running this SQL:

CREATE TABLE events (
id SERIAL PRIMARY KEY,
title VARCHAR(100) NOT NULL,
event_date DATE NOT NULL,
start_time VARCHAR(5) NOT NULL,
end_time VARCHAR(5) NOT NULL,
location VARCHAR(100) NOT NULL,
description TEXT
);

    Update connection info in DatabaseConnection.java:

private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
private static final String USER = "postgres";
private static final String PASSWORD = "vn9282g++2";

🧱 My Project Structure
▶️ Main.java

    Launches the JavaFX application via EventApp.launch().

📅 Event.java

    Represents a single event with fields: id, title, eventDate, startTime, endTime, location, description.

    Includes validation:

        Checks date isn’t in the past

        Ensures time format is HH:MM

        Confirms end time is after start time

🛢 DatabaseConnection.java

    Manages JDBC connection and CRUD operations.

    Features:

        Adds, updates, deletes events

        Retrieves all events

        Resets ID sequence to start from 1

🧠 EventManager.java

    Acts as the business logic layer.

    Handles all in-memory operations and syncs them with the database.

    Can also generate a summary report with:

        Total number of events

        Most recent event

        Activity log

📁 FileExporter.java

    Exports events to:

        CSV via exportToCSV()

        JSON via exportToJSON() with pretty print

🎨 EventApp.java

    JavaFX GUI setup.

    Features:

        TableView (left): Lists events

        Form (right): Edit/add event details

        Buttons for Add, Update, Delete, Export, and Generate Report

🧪 What the GUI Can Do

    Add Event ➕
    → Validates input and saves to database

    Update Event ✏️
    → Edits selected event and saves changes

    Delete Event 🗑
    → Removes selected event from database

    Generate Report 📄
    → Shows total count, latest event, and summary

    Export Data 📤
    → Saves events to events.csv and events.json

💡 Example Usage

    I enter "Team Meeting", pick a future date, set time as "14:00–15:00", then click "Add Event".

    It appears in the table with ID 1.

    I can select it, update the title to "Updated Meeting", and click "Update Event".

    I can also delete it by clicking "Delete Event".

    To export, I click "Export Data", and two files (CSV, JSON) are saved.

    Clicking "Generate Report" opens a dialog with a summary.

🛠 Troubleshooting

    Database not connecting?
    → Check your username/password and PostgreSQL is running.

    JavaFX not loading?
    → Make sure the SDK path is set in VM options.

    JSON errors?
    → Confirm json-20230227.jar is added to your classpath.

    Table not refreshing?
    → Call refreshEventTable() after every operation.

🧾 Summary

This project taught me a lot about combining Java, JavaFX, and databases. I loved implementing a real-world tool from scratch, and I made sure it’s clean, well-documented, and fun to use 💪.

Feel free to try it, customize it, or reach out if you want to collaborate! 🚀
hooting
Problem	Solution
❌ DB connection error	Check USER, PASSWORD, URL in DatabaseConnection.java
⚠️ JavaFX not loading	Set correct JavaFX --module-path in VM options
🛑 JSON export error	Ensure json-20230227.jar is in lib/ and on classpath
🔄 Table not updating	Confirm refreshEventTable() is called after each action
✅ Summary

This project demonstrates how to:

    Integrate JavaFX and PostgreSQL for real-world apps

    Build a clean and responsive GUI

    Apply data validation and error handling

    Work with external libraries and export formats
