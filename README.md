# XChange
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![SQL Server](https://img.shields.io/badge/SQL_Server-Express-CC2927?logo=microsoftsqlserver)
A JavaFX desktop application for user-based currency conversion and historical exchange-rate graph tracking, backed by Microsoft SQL Server. Developed as a team for CS102, Bilkent University, Fall 2023.

## Table of Contents
1. [Project Overview](#project-overview)
2. [Key Highlights](#key-highlights)
3. [Architecture & Structure](#architecture--structure)
4. [Tech Stack & Requirements](#tech-stack--requirements)
5. [Setup Guide](#setup-guide)
6. [Environment Variables](#environment-variables)
7. [Running the App](#running-the-app)

## Project Overview
XChange allows users to:
- Sign up, sign in, and manage account settings
- Convert currencies with latest rates
- Save and view historical currency graphs
- Store user and currency data in SQL Server

## Key Highlights
- Layered package architecture (`app`, `controller`, `service`, `repository`, `model`)
- Secure configuration using environment variables (no hardcoded API keys/DB credentials)
- Ready-to-run SQL schema script and clear setup instructions
- JavaFX multi-screen workflow with authentication, conversion, and graph history

## Architecture & Structure
The project now follows a layered package structure:

- `com.xchange.app`: application entry and app-level state
- `com.xchange.controller`: JavaFX controllers (UI interaction layer)
- `com.xchange.service`: business logic and external API calls
- `com.xchange.repository`: database access layer
- `com.xchange.model`: domain models

Directory layout:

```text
src/
  main/
    java/
      com/xchange/
        app/
        controller/
        model/
        repository/
        service/
    resources/
      *.fxml
      Icons/
      Flags/
      information.txt

libs/
  json-20231013.jar

db/
  CreateDatabase.sql
```

## Tech Stack & Requirements
- Java (JDK 17+ recommended, JDK 21 works)
- JavaFX SDK (21.x recommended)
- Microsoft SQL Server
- Microsoft SQL Server JDBC Driver (`mssql-jdbc`)
- JSON library (`org.json`)

## Setup Guide
1. Clone this repository.
2. Create the database by running:
- `db/CreateDatabase.sql`
3. Download dependencies locally:
- JavaFX SDK
- Microsoft SQL JDBC driver
4. Add required libraries to your IDE/project classpath:
- JavaFX jars
- JDBC driver jar
- `libs/json-20231013.jar`
5. Mark source/resource roots in your IDE:
- `src/main/java` as Sources Root
- `src/main/resources` as Resources Root
6. Set the environment variables listed below.
7. Optionally copy `.env.example` as a reference while setting your local environment variables.

## Environment Variables
Set these before running the application:

- `OPENEXCHANGE_APP_ID=YOUR_API_KEY_HERE`
- `XCHANGE_DB_URL=jdbc:sqlserver://localhost;database=XChange;encrypt=true;trustServerCertificate=true;`
- `XCHANGE_DB_USER=YOUR_DB_USERNAME_HERE`
- `XCHANGE_DB_PASSWORD=YOUR_DB_PASSWORD_HERE`

Notes:
- `OPENEXCHANGE_APP_ID` is required for live exchange-rate API calls.
- If DB credentials are missing, the app fails fast with a clear error.

## Running the App
1. Open the project in your Java IDE.
2. Ensure JavaFX and JDBC libraries are configured.
3. Run `com.xchange.app.Navigator` (main class).
