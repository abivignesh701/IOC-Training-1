# IOC Training (Customized)

A Spring Boot training project demonstrating **Inversion of Control (IoC) / Dependency Injection** and an **in-memory Student CRUD REST API**.

> **Attribution & Origin Notice**
> This repository is a customized derivative of [Abishek-KS6/Ioc-Training](https://github.com/Abishek-KS6/Ioc-Training) (original author: `Abishek-KS6`).
> The original repository did not include a `LICENSE` file; original authorship belongs to `Abishek-KS6`, and the customizations documented below are maintained in this repository.

---

## Project Overview

The project lives under `Spring Boot/Spring boot day 1 & 2/Spring-Boot` and covers two core Spring Boot concepts:

1. **Spring IoC & Bean Qualification (`com.example.ioc`)**
   - `Writer` interface implemented by `Pen` and `Pencil` Spring `@Component` beans.
   - `Student` component injects `Writer` using `@Qualifier("pen")` and executes `writer.write()` via `exam()` on application startup.
   - `IocController` and `IocService` expose a simple greeting endpoint at `GET /hello`.

2. **In-Memory Student CRUD REST API (`Controller`, `Service`, `Model`, `Exception`)**
   - `StudentController` exposes CRUD endpoints under `/students`.
   - `StudentService` manages an in-memory list of `Student` records (`rno`, `name`, `tech`).
   - `GlobalExceptionHandler` translates validation, conflict, and missing-record errors into structured JSON error responses.

---

## Customizations Made

Compared to the original repository, the following meaningful improvements were implemented:

1. **Fixed Index-0 Data-Corruption Bugs & Centralized Lookup (`StudentService`)**
   - Replaced duplicated lookup loops (which previously defaulted to index `0` and silently returned, overwrote, or deleted the first student when a roll number was missing) with a shared `findIndexByRollNo` helper and `StudentNotFoundException`.
   - Added synchronization for safe concurrent in-memory updates and duplicate roll-number rejection (`DuplicateStudentException` -> HTTP `409 Conflict`).
2. **Added Input Validation & Proper HTTP Status Codes (`StudentController`, `GlobalExceptionHandler`)**
   - Validated positive roll numbers and non-blank `name`/`tech` fields, returning structured `400 Bad Request` responses on invalid payloads.
   - Returned `201 Created` for `POST /students` and `404 Not Found` for missing roll numbers while keeping existing endpoint routes and success response bodies compatible.
3. **Refactored to Constructor Injection & Improved Naming**
   - Switched field `@Autowired` injection to constructor injection (`private final` dependencies) in `StudentController`, `IocController`, and `Student` (retaining `@Qualifier("pen")`).
   - Standardized method and variable names (`updateStudent`, `studentService`, `iocService`, `writer`) and added Javadocs.
4. **Java 17 LTS Build Compatibility & Hygiene (`pom.xml`, `.gitignore`)**
   - Configured `<java.version>17</java.version>` and Maven project metadata in `pom.xml` so the project compiles cleanly on JDK 17 LTS as well as newer JDKs.
   - Added a root `.gitignore` for build outputs (`target/`, `out/`), `.env` files, and temporary OS/log files.
5. **Added Unit & Integration Tests (`src/test/java/com/example/ioc/`)**
   - Added `StudentServiceTest`, `StudentControllerTest` (MockMvc), and IoC bean-wiring assertions in `IocApplicationTests`.

---

## Tech Stack

- **Java 17+** (tested with JDK 17.0.12)
- **Spring Boot 4.2.0** (`spring-boot-starter`, `spring-boot-starter-webmvc`, `spring-boot-starter-test`)
- **Lombok**
- **Maven Wrapper** (`mvnw` / `mvnw.cmd`)
- **JUnit 5 & Spring MockMvc**

---

## Getting Started

### Prerequisites
- JDK 17 or newer installed and available on `PATH`

### Build and Run Tests

From the Maven project directory:

```bash
cd "Spring Boot/Spring boot day 1 & 2/Spring-Boot"
```

On Windows:
```powershell
.\mvnw.cmd test
```

On macOS / Linux:
```bash
./mvnw test
```

### Run the Application

The server runs on port `8081` (configured in `src/main/resources/application.properties`):

```powershell
.\mvnw.cmd spring-boot:run
```

---

## API Endpoints

Base URL: `http://localhost:8081`

| Method | Endpoint | Description | Success Status |
| :--- | :--- | :--- | :--- |
| `GET` | `/hello` | Returns `"Hello Service"` | `200 OK` |
| `GET` | `/students` | List all students | `200 OK` |
| `GET` | `/students/{rno}` | Get a student by roll number | `200 OK` / `404 Not Found` |
| `POST` | `/students` | Add a new student (`rno`, `name`, `tech`) | `201 Created` / `400` / `409` |
| `PUT` | `/students` | Update an existing student by `rno` | `200 OK` / `400` / `404` |
| `DELETE` | `/students/{rno}` | Delete a student by roll number | `200 OK` / `400` / `404` |
