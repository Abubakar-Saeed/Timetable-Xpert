<img width="388" height="226" alt="4" src="https://github.com/user-attachments/assets/78c6a80a-a252-4fa6-ac68-9171e4f7867a" /># TimetableXpert

TimetableXpert is a JavaFX-based desktop application for generating and managing academic timetables. It integrates GUI controls, reporting, PDF export, and database connectivity to help organize courses, rooms, teachers, and schedules.

## Project Overview

- **Name:** TimetableXpert
- **Package:** `com.example.gui`
- **Main modules:**
  - `MainApplication.java` - JavaFX application entry point
  - `HomeController.java`, `LoginController.java`, `RegisterController.java`, `PrintController.java` - UI controllers
  - `TimeTableGenerator.java`, `GenerateTimeTable.java`, `AllocateCourse.java` - timetable logic
  - `DataBaseLayer.java` - database integration
  - `Program.java`, `Teacher.java`, `Room.java`, `Lab.java`, `Semester.java`, `Session.java`, `Course.java` - domain model classes
- **User interface:** built using JavaFX and FXML files under `src/main/resources/com/example/gui`
- **Reports:** JasperReports templates (`.jrxml`) for printing room, lab, semester, teacher and other reports
- **Web version repository:** https://github.com/Abubakar-Saeed/University-Timetable-Automation-GCUF

## Technologies Used

- Java (modular application using `module-info.java`)
- JavaFX (controls, FXML, Web, Media, Swing integration)
- Maven build system
- JasperReports for report generation
- Apache PDFBox for PDF handling
- Apache POI for Excel/document processing
- MySQL Connector/J for database connectivity
- ControlsFX, FormsFX, ValidatorFX, Ikonli, BootstrapFX, Medusa, TilesFX, FXGL for enhanced UI and widgets
- Log4j for logging

## Prerequisites

- Java JDK 17 or compatible Java 17+ runtime
- Maven (optional, uses bundled Maven wrapper)
- MySQL database (if using the database-backed features)

## Setup and Build

1. Clone or open the project in your IDE.
2. Ensure your `JAVA_HOME` points to a Java 17 JDK.
3. Use the Maven wrapper from the project root:

```powershell
.\\mvnw.cmd clean package
```

4. To run the project with JavaFX support:

```powershell
.\\mvnw.cmd javafx:run
```

If you prefer a normal Maven install and have Maven on your PATH:

```powershell
mvn clean package
mvn javafx:run
```

## Running in an IDE

- Import the project as a Maven project.
- Make sure the IDE uses JDK 17.
- Configure the run configuration to execute the `com.example.gui.MainApplication` main class if necessary.
- Make sure `src/main/resources` is on the classpath so FXML and report templates are accessible.

## Database Configuration

This project includes a MySQL connector dependency. Configure your database connection details in the application code or configuration file before running the database-backed features.

> There is also a backup SQL file named `time_table_automation_backup.sql` available in the project root.

## Project Structure

- `src/main/java/com/example/gui/` - application source code
- `src/main/resources/com/example/gui/` - FXML views, styles, and JasperReports templates
- `pom.xml` - Maven build file with dependencies and repositories
- `mvnw`, `mvnw.cmd` - Maven wrapper scripts


<img width="863" height="656" alt="1" src="https://github.com/user-attachments/assets/f07f8592-1374-422d-9b2f-4f5d16639548" />

<img width="1587" height="947" alt="2" src="https://github.com/user-attachments/assets/d1845461-2eb6-4fae-a038-2c02accd5da2" />

<img width="1586" height="938" alt="3" src="https://github.com/user-attachments/assets/6e6db6dd-ebae-4978-a1fa-f12777ae5f30" />


<img width="388" height="226" alt="4" src="https://github.com/user-attachments/assets/96a1ff1f-6f84-4a02-b1fc-3a55a0aa8c67" />







## Notes

- The application is modular and uses `module-info.java` to declare JavaFX and other dependencies.
- The `pom.xml` includes external repositories for JasperReports and other third-party artifacts.
- If you update JavaFX versions, ensure the module declarations and Maven dependencies remain compatible.

## Helpful Commands

```powershell
.\\mvnw.cmd clean package
.\\mvnw.cmd test
.\\mvnw.cmd javafx:run
```

