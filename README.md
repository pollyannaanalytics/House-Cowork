# House-Cowork

House-Cowork is an Android application for collaboration and housework management. This project demonstrates how to build an application with a well-designed architecture using modern Android development technologies.

## Project Overview

House-Cowork provides a platform for roommates to create and manage tasks and collaborate with other members to complete them. Users can create houses within the app, invite members to join, and assign tasks to specific members or groups.

## Design Patterns

This project utilizes several design patterns to ensure a clean and maintainable codebase:

- **Model-View-ViewModel (MVVM)**: Separates the UI from the business logic, making the code more modular and testable.
- **Repository Pattern**: Provides a clean API for data access to the rest of the application, abstracting the data sources.
- **Dependency Injection (DI)**: Achieved using Hilt, it helps in managing dependencies efficiently and supports testability.
- **Strategy Pattern**: In the authentication part, common business logic is abstracted into an interface, allowing each third-party login service to be implemented separately, providing flexibility and control over authentication strategies.
- **Observer Pattern**: Utilized through StateFlow to update the UI in response to data changes.

## Tech Stack
- **Kotlin**: Used as the primary development language.
- **Jetpack Compose**: Used for building modern Android UI.
- **Hilt**: Used for dependency injection.
- **Room**: Used for local data storage.
- **Retrofit**: Used for network communication.
- **Coroutines**: Used for handling asynchronous tasks.

## Features

- Create and manage houses
- Invite members to join houses
- Create and assign tasks
- View and update task status
- Support for public and private tasks

## Installation Guide

1. Clone this project locally:
   ```bash
   git clone https://github.com/yourusername/house-cowork.git
   ```

2. Create a Firebase project and put ```google-servcies.json``` in to app/

3. Ensure that Android SDK and the appropriate emulator or physical device are installed.

4. Click the "Run" button to build and run the application.

## Contribution Guide

Contributions to this project are welcome! If you have any suggestions for improvements or find any bugs, please submit an issue or pull request.

1. Fork this project.
2. Create your feature branch (`git checkout -b name/VersionNumber_AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git checkout -b name/VersionNumber_AmazingFeature`).
5. Open a Pull Request to ```develop```.

## Contact

If you have any questions or suggestions, feel free to contact us: 
* Pin-Yun Wu: pinyunwuu@gmail.com 
* Roland Lin: bellaesmia09@gmail.com