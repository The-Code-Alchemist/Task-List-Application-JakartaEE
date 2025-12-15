# Task List Application

A **Jakarta EE** web application built with **Java 21**, **Maven**, and **Docker**. It demonstrates a clean, layered architecture for managing tasks with persistent storage and now includes **end-to-end tests** describing behavior in Gherkin.

## Features
*   **Task Management:** Create, Read, Update (Status), and Delete tasks.
*   **User Management:** (Simplified) Auto-creation of a test user for demonstration purposes.
*   **Persistence:** Uses **Hibernate (JPA)** to store data in a **MySQL** database.
*   **Containerization:** `docker-compose.yaml` for bringing up MySQL locally.
- **Automated E2E Tests:** Cucumber-based tests (`src/test/java`) + feature specs (`src/test/resources`).
- **Build/Run Tooling:** Maven Wrapper (`mvnw`, `.mvn/`) for consistent builds.

## Prerequisites
1. **Java 21**
2. **Docker Desktop** (running)
3. **Tomcat 11** (or equivalent Jakarta EE 11 server)
4. (Optional) **IntelliJ IDEA** for easiest local run/debug setup

## Tech Stack
- **Java SE:** 21
- **Jakarta EE:** 11 (Servlets, JPA)
- **Database:** MySQL 9 (Docker)
*   **ORM:** Hibernate 6
*   **Build Tool:** Maven
*   **Tools:** IntelliJ IDEA, Docker Desktop

## Project Structure (High-Level)

- `src/main/...` — application code (web + business + persistence layers)
- `src/test/java/...` — automated tests (including E2E/Cucumber runner and step definitions)
- `src/test/resources/...` — Gherkin feature files describing task management behavior
- `docker-compose.yaml` — local MySQL container definition
- `logs/app.log` — application log output (if enabled/produced by your runtime)

## Run Locally
### 1) Start the Database (Docker)
From the project root:
- `docker compose up -d`
This provisions a MySQL container (typically on port `3306`) and creates the application database as defined in `docker-compose.yaml`.

### 2) Configure and Run the Web App (Tomcat)
1. Create a **Tomcat Local** run configuration in IntelliJ (or deploy the WAR manually).
2. Deploy the application artifact (WAR / exploded WAR).
3. Start Tomcat and open the application in your browser.
   - **Note:** The application is reachable at `http://localhost:8080/tasks/`

> If your server uses a non-root context path, adjust URLs accordingly.

## Running Tests
### Unit / Integration / E2E (via Maven)
Run the full test suite bash or PowerShell:
`./mvnw test`

### Cucumber E2E
Feature files live under:
- `src/test/resources/.../*.feature`

Test runner and step definitions live under:
- `src/test/java/...`

> Ensure the database is running (Docker) and the application is reachable if your E2E setup expects a running server.

## Configuration
- **JPA configuration:** `src/main/resources/META-INF/persistence.xml`
- **Docker DB config:** `docker-compose.yaml`

## Future Improvements
- Add CI workflow (build and test on pull requests)
- Document environment variables / profiles (test vs local vs prod)
- Add API contract documentation (endpoints, request/response, status codes)
- Expand test coverage (negative cases, validation, edge cases)