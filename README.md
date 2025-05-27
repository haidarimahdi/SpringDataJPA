# SpringDataJPA Time Tracking Application

## Overview

SpringDataJPA is a comprehensive time-tracking web application developed as a course project for the Advanced Programming Features course. It allows users to manage people, projects, and the time slots they dedicate to various tasks. The application features both a user-friendly HTML interface and a RESTful JSON API for programmatic access.

This project demonstrates key concepts in modern web application development using the Spring Boot framework.

## Key Features

* **Person Management:**
    * Create, view, update, and delete individuals (users/employees).
    * View detailed information for each person, including their logged time slots and a summary of time spent per project.
* **Project Management:**
    * Create, view, update, and delete projects.
    * Assign and unassign people to projects.
    * View detailed project information, including assigned personnel, their total hours booked on the project, and a breakdown of their individual time slots for that project.
    * Track scheduled effort vs. actual hours booked, with visual cues for overbooked projects.
* **Time Slot Management:**
    * Record time slots with details like date, start time, end time, description, associated person, and project.
    * View paginated lists of all time slots and time slots specific to a project.
    * Delete time slots.
* **Dual Interface:**
    * **Web UI:** A user-friendly HTML interface built with Spring MVC and Thymeleaf for easy interaction through a web browser.
    * **RESTful JSON API:** Endpoints for managing persons, projects, and time slots programmatically. Includes pagination for relevant listings.
* **Data Persistence:** Utilizes Spring Data JPA with Hibernate to interact with an H2 database (file-based for persistence).
* **Reporting/Summaries:**
    * Project overview displaying total booked hours vs. scheduled effort.
    * Person details page showing a summary of time spent across different projects.
    * Project details page showing time contribution per assigned person.

## Technologies Used

* **Backend:**
    * Java [17]
    * Spring Boot [3.0.7]
    * Spring MVC (for Web UI and REST Controllers)
    * Spring Data JPA (with Hibernate for ORM)
    * Spring Security (If you add it later)
* **Frontend (Server-Side Rendered):**
    * Thymeleaf
    * HTML5, CSS3 (basic styling)
* **Database:**
    * H2 Database (File-based)
* **Build & Dependency Management:**
    * Apache Maven
* **API Documentation (if integrated):**
    * SpringDoc OpenAPI (Swagger UI)
* **Testing:**
    * JUnit 5
    * Mockito
    * Spring Boot Test, @DataJpaTest

## Setup and Running the Application

1.  **Prerequisites:**
    * Java JDK [17] or later installed.
    * Apache Maven installed.
    * Git installed.

2.  **Clone the repository:**
    ```bash
    git clone https://github.com/haidarimahdi/SpringDataJPA.git
    cd SpringDataJPA
    ```

3.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```
    (This will also run the tests. To skip tests during build: `mvn clean install -DskipTests`)

4.  **Run the application:**
    * You can run the application using Maven:
        ```bash
        mvn spring-boot:run
        ```
    * Alternatively, after building, you can run the JAR file from the `target` directory:
        ```bash
        java -jar target/SpringDataJPA-0.0.1-SNAPSHOT.jar 
        ```
      (Replace `SpringDataJPA-0.0.1-SNAPSHOT.jar` with the actual JAR file name if different).

5.  **Accessing the Application:**
    * **Web UI:** Open your web browser and go to `http://localhost:8080/`
    * **H2 Database Console:** Accessible at `http://localhost:8080/h2-console`.
        * **JDBC URL:** `jdbc:h2:file:./data/myDB` (or `jdbc:h2:mem:testdb` if you switch to in-memory for running)
        * **User Name:** `admin`
        * **Password:** `password`
    * **API Endpoints (Examples):**
        * `GET /person` (JSON)
        * `GET /project` (JSON)
        * `GET /timeSlot` (JSON, paginated)
        * (If SpringDoc is configured) Swagger UI: `http://localhost:8080/swagger-ui.html`

## Project Structure Highlights

* `src/main/java`: Contains the main application code.
    * `com.example.SpringDataJPA`: Root package.
        * `controller`: Contains Spring MVC controllers (for both HTML and JSON).
            * `html`: Controllers serving Thymeleaf views.
            * `json`: REST controllers serving JSON responses.
        * `dto`: Data Transfer Objects used for communication between layers and for API responses.
        * `model`: JPA entity classes representing the data structure (Person, Project, TimeSlot).
        * `repositories`: Spring Data JPA repository interfaces.
        * `service`: Service layer containing business logic.
* `src/main/resources`:
    * `application.properties`: Spring Boot configuration file.
    * `templates`: Thymeleaf HTML templates.
* `src/test/java`: Contains unit and integration tests.
* `pom.xml`: Maven project configuration file.

## Future Enhancements

* Implement Spring Security for authentication and authorization.
* More sophisticated UI/UX with a frontend framework (e.g., React, Angular, Vue).
* Advanced reporting features.
* More comprehensive validation and error handling.

## Contribution

Contributions are welcome! Please feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

