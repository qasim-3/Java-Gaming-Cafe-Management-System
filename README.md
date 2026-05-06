## 1. CEP Project: Gaming Café Management System

**Objective:**

The objective of this project was to design and implement a **Gaming Café
Management System** using Object-Oriented Programming (OOP) principles and
additional concepts. This system has streamlined operations in a gaming café by
automating key tasks such as managing user sessions, tracking payment, along
with additional functions.

**Software Used:**

- IntelliJ IDE
- Apache NetBeans IDE 23
- Java Programming language
- MySQL 8.

**Problem Statement:**

Managing a gaming café efficiently involves several challenges, including tracking
player sessions, calculating session costs, and ensuring smooth transitions
between players. Many cafés still rely on manual methods, which are time-
consuming and error-prone. This project aims to provide a comprehensive
solution that automates these processes, improving accuracy and customer
experience.

## 2. Project Workflow:

![LOGIN](https://github.com/user-attachments/assets/93fa886d-7143-4655-88b1-e8de31781a37)

## Purpose: (LoginAccountPage.java)

The Login Page allows users to authenticate themselves before accessing the
system.

### Functionalities:

**Email and Password Input:** Users can enter their email and password to log
    in.
    
**Validation:** The system validates the input fields to ensure they match the
    required format (e.g., email pattern).
    
**Login Action:** Upon clicking the login button, the system checks the
    credentials against the database. If valid, the user is redirected to the Home
    Page; if invalid, an error message is displayed.
    
**Registration Link:** Users can navigate to the Create Account Page if they do
    not have an account

![CREATE](https://github.com/user-attachments/assets/279a331d-36b0-4a97-b770-014254137e48)

## Purpose: (CreateAccountPage.java)

This page allows new users to register for an account in the system.

### Functionalities

**Input Fields:** Users can enter their name, email, password, and phone number.

**Validation:** The system checks that all fields are filled out correctly and that the email format is valid.

**Account Creation:** When the user clicks the "Create Account" button, the system saves the user’s information to the database and displays a success message.

**Clear Fields:** After account creation, the input fields are cleared for convenience.

![TIMER](https://github.com/user-attachments/assets/e5effb00-e356-47a4-9863-579e2fc3b7b9)

## Purpose: (Home.java)

The Home Page serves as the main dashboard for users after logging in.

### Functionalities

**Device Management:** Users can view and manage various devices (e.g., PCs, consoles) available in the café.

**Timer Display:** The page shows the remaining time for each device, allowing users to monitor their usage.

**Add Time:** Users can add time to their session by clicking buttons that deduct the appropriate amount from their balance.

**Terminate Session:** Users can terminate their session for a selected device, stopping the timer and resetting the remaining time.

**Payment Processing:** Users can deposit money into their account balance for future use.

**Refreshment Ordering:** Users can select and order refreshments, with the cost deducted from their balance.

![GAMES](https://github.com/user-attachments/assets/ef7ccd6f-541f-4682-8d55-60e34107a106)

## Purpose: (GamesPage.java)

The Game Page allows users to select and launch games while updating the database with their gaming activity.

### Functionalities

**Game Selection:** Users can browse and select games to play via buttons or icons.

**Launch Game:** Clicking a game button initiates the game and records the user's activity in the database, including the game name and timestamp.

**Session Management:** The system tracks the duration of each gaming session.

**Return to Home:** Users can easily navigate back to the Home Page.

![MANAGE](https://github.com/user-attachments/assets/05378d18-546d-4b02-b59d-7a7e6dcb8eb8)

## Purpose: (ManageUsers.java)

This page is intended for administrators to manage user accounts.

### Functionalities

**User List Display:** A table displays all registered users, showing their details such as ID, name, email, phone, and status.

**User Verification:** Administrators can verify or change the status of users by selecting a user and confirming the action.

**Search and Filter:** Administrators can search for specific users based on their email or other criteria.

**Help Dialog:** A help button provides information about the functionalities of the page.

## UML Class Diagram:

![GCMS_UML](https://github.com/user-attachments/assets/504066e8-c455-43c0-a0ec-41991accc842)


