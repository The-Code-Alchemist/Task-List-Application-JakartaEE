# Task List Application

This is a **Jakarta EE Web Application** built using **Java 21**, **Maven**, and **Docker**. It demonstrates a clean, layered architecture implementing a simple Task Management system with persistent storage.

## Features

*   **Task Management:** Create, Read, Update (Status), and Delete tasks.
*   **User Management:** (Simplified) Auto-creation of a test user for demonstration purposes.
*   **Persistence:** Uses **Hibernate (JPA)** to store data in a **MySQL** database.
*   **Containerization:** Uses **Docker** to spin up the database instantly.

## Tech Stack

*   **Java:** JDK 21
*   **Framework:** Jakarta EE 10 (Servlets, JPA)
*   **Database:** MySQL 8.0 (via Docker)
*   **ORM:** Hibernate 6
*   **Build Tool:** Maven
*   **Tools:** IntelliJ IDEA, Docker Desktop

## Project Structure

The project follows a layered architecture to separate concerns:

*   `src/main/java`
    *   `controller`: **Servlets** (`TaskServlet`) handling HTTP requests and navigation.
    *   `service`: Business logic layer (`TaskService`) that bridges the controller and data layer.
    *   `repository`: Data Access Object (DAO) layer (`TaskRepository`, `UserRepository`) handling DB operations.
    *   `model`: JPA Entities (`Task`, `User`) representing database tables.
*   `src/main/webapp`: Frontend resources (`task-list.jsp`) and configuration.

## Prerequisites

1.  **Java JDK 21** installed.
2.  **Docker Desktop** installed and running.
3.  **Apache Tomcat 10.1+** (Core) installed (or similar Jakarta EE compatible server).

## How to Run

### 1. Start the Database
The project includes a `docker-compose.yaml` file to provision the database automatically.
Open a terminal in the project root and run:
*This starts a MySQL container on port 3306 with database `task_db`.*

### 2. Configure IntelliJ IDEA
1.  Open **Run/Debug Configurations**.
2.  Add a **Tomcat Server > Local** configuration.
3.  **Deployment Tab:** Add the artifact `tasklist-application:war exploded`.
    *   **Application Context:** Set this to `/` (root) or `/tasklist`.
4.  **Server Tab:** Set the **URL** to `http://localhost:8080/tasks`.

### 3. Run the Application
1.  Click the **Run** (Green Play) button in IntelliJ.
2.  The browser should automatically open the task list.
3.  A default user (`testuser`) will be automatically created in the database upon first access.

## Configuration Details
*   **Database Config:** Located in `src/main/resources/META-INF/persistence.xml`.
*   **Docker Config:** Located in `docker-compose.yaml`.