# NotificationService

A feature-rich Android application demonstrating the use of Android's **NotificationListenerService** to monitor and display notifications within the app interface. This project showcases various core Android components and serves as a learning experience in Android development.

## Overview

The **NotificationService** project utilizes Android’s `NotificationListenerService` to capture and display system notifications in a RecyclerView list. The project demonstrates effective handling of notifications using custom adapters, view manipulation, and Android’s broadcast/receiver mechanisms.

### Key Features

- **Notification Tracking**: Reads notifications directly from the status bar and dynamically displays them in a RecyclerView list.
- **Custom List Adapter**: Implements a `CustomListAdapter` for RecyclerView, allowing customized views for each list item.
- **Swipe-to-Dismiss**: Supports user interaction through `ItemTouchHelper`, enabling swipe-to-dismiss functionality on individual notifications.
- **Expandable Views**: Allows users to expand or collapse notification details for an optimized viewing experience.
- **Real-Time Updates**: Utilizes `onNotificationPosted` in conjunction with a `BroadcastReceiver` to immediately reflect newly posted notifications in the app.
- **File Read/Write**: Includes functionality for reading from and writing to files.

## Components Explored

This project dives into a variety of Android components and patterns, including:
- **RecyclerView**: For efficient and scalable display of a dynamic list of notifications.
- **CustomListAdapter**: Created to provide customized control over the display of each RecyclerView item.
- **ItemTouchHelper**: Implements swipe gestures for individual list items.
- **NotificationListenerService**: Accesses and reads system notifications.
- **BroadcastReceiver**: Listens for and reacts to new notifications in real-time.
- **Dynamic View Addition**: Adds and customizes views programmatically based on runtime requirements.

## Getting Started

### Prerequisites

- Android Studio (version Koala 2024.1.1 or higher recommended)
- Gradle Version: 8.7
- Minimum Android SDK: 27
- Target Android SDK: 34

### Permissions Required

This app requires two permissions to function correctly:

1. **Read Notifications** – Allows the app to access and read notifications from the status bar.
2. **All Files Access** – Provides access to manage files for reading and writing data.

If permissions were not granted, you can enable them manually:

1. **Enable Read Notifications Permission**:
   - Go to **Settings > Apps > Special app access > Device & app notifications > NotificationService** and toggle **Allow notification access**.

2. **Enable All Files Access Permission**:
   - Go to **Settings > Apps > Special app access > All files access > NotificationService** and toggle **Allow access to manage all files**.

### Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/gunjanvinzuda/NotificationService.git
   cd NotificationService

2. **Open in Android Studio:**

- Launch Android Studio and open the project directory.
- Sync Gradle to ensure dependencies are installed.

### Running the Project
- **Start the App:** Run the project on an Android emulator or a physical device.
- **Configure Permissions:** Once installed, enable notification access and all files access in device settings if needed.
- **View Notifications:** The app will capture and display notifications in real-time as they appear in the status bar.
