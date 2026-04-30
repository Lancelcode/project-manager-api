<div align="center">

# 🗂️ Project Manager API

**Production-ready REST API for project and task management**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![CI](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)](https://github.com/Lancelcode/project-manager-api/actions)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Tests](https://img.shields.io/badge/Tests-6_passing-198754?style=for-the-badge)](#testing)

*A fully containerised backend API with JWT authentication, real database persistence, automated CI, and Swagger documentation, built from scratch.*

[🚀 Quick Start](#quick-start) · [🔐 Auth](#authentication) · [📡 Endpoints](#api-endpoints) · [🧪 Tests](#testing) · [📖 Docs](#api-documentation)

</div>

---

## 📌 About the Project

**Project Manager API** is a RESTful backend service built with Java 21 and Spring Boot 4. It handles the full lifecycle of projects and tasks, from creation to completion, with JWT secured endpoints, a real PostgreSQL database, Docker containerisation, and a CI pipeline that runs on every push.

This was built as a learning project to get Spring Boot, JPA, and REST design patterns into muscle memory, but every decision was made with production in mind.

---

## ✨ What it does

| Feature | Description |
|---|---|
| 🔐 JWT Authentication | Register and login to get a signed token — every endpoint is protected |
| 🗂️ Project Management | Full CRUD with status lifecycle (`PLANNED → IN_PROGRESS → COMPLETED`) |
| ✅ Task Management | Assign tasks to projects with priority levels (`LOW`, `MEDIUM`, `HIGH`) and due dates |
| 🛡️ Global Error Handling | Structured JSON error responses with meaningful HTTP status codes |
| ✔️ Input Validation | Jakarta Bean Validation — clear feedback on every malformed request |
| 📖 Swagger UI | Auto-generated API documentation at `/swagger-ui/index.html` |
| 🐳 Docker | Single command to spin up the full stack — app + PostgreSQL |
| ⚙️ CI Pipeline | GitHub Actions runs all tests on every push to `main` |

---

## 🧩 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Build | Maven |
| Persistence | Spring Data JPA + PostgreSQL 16 |
| Auth | Spring Security + JWT (jjwt 0.12.6) |
| Validation | Jakarta Bean Validation |
| Testing | JUnit 5 + Mockito |
| Containerisation | Docker + docker-compose |
| CI | GitHub Actions |
| Docs | SpringDoc OpenAPI / Swagger UI |

---

## 📁 Project Structure

```
src/
├── main/java/
│   ├── controller/     # REST endpoints and request mapping
│   ├── service/        # Business logic layer
│   ├── repository/     # Data access layer (Spring Data JPA)
│   ├── model/          # JPA entity definitions
│   ├── dto/            # Request/response objects (decoupled from entities)
│   ├── security/       # JWT filter, UserDetailsService, SecurityConfig
│   └── exception/      # Global exception handler, custom exceptions
└── test/
    └── service/        # Unit tests ProjectService (JUnit 5 + Mockito)
```

---

## 📡 API Endpoints

### 🔓 Auth (public)

```
POST   /auth/register       Register a new user, returns JWT token
POST   /auth/login          Login with email and password, returns JWT token
```

### 🔒 Projects (Bearer token required)

```
POST   /projects            Create a new project
GET    /projects            List all projects
GET    /projects/{id}       Get a project by ID
PUT    /projects/{id}       Update a project
DELETE /projects/{id}       Delete a project
```

### 🔒 Tasks (Bearer token required)

```
POST   /projects/{id}/tasks             Add a task to a project
GET    /projects/{id}/tasks             List all tasks for a project
GET    /projects/{id}/tasks/{taskId}    Get a specific task
PUT    /projects/{id}/tasks/{taskId}    Update a task
DELETE /projects/{id}/tasks/{taskId}    Remove a task
```

---

## 🔐 Authentication

Every protected endpoint requires a `Bearer` token in the `Authorization` header.

**1. Register:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "you@example.com", "password": "secret123"}'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "you@example.com"
}
```

**2. Use the token:**
```bash
curl http://localhost:8080/projects \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

## 🚀 Quick Start

### Option 1: Docker (recommended)

**Prerequisites:** Docker Desktop

```bash
git clone https://github.com/Lancelcode/project-manager-api.git
cd project-manager-api
docker-compose up --build
```

App starts at `http://localhost:8080` backed by a real PostgreSQL database.

```bash
# Stop containers
docker-compose down

# Stop and wipe the database
docker-compose down -v
```

### Option 2: Maven

**Prerequisites:** Java 21, Maven

```bash
./mvnw spring-boot:run
```

Runs with H2 in memory database. Data resets on restart.

---

## 🧪 Testing

**6 unit tests, all passing, run automatically on every push**

| Test | What it covers |
|---|---|
| `getAllProjects_returnsAllProjects` | Service returns full project list from repository |
| `getProjectById_returnsProject_whenFound` | Correct project returned for valid ID |
| `getProjectById_throwsException_whenNotFound` | `ProjectNotFoundException` thrown with correct message |
| `createProject_savesAndReturnsProject` | Repository `save()` called, response mapped correctly |
| `deleteProject_deletesProject_whenFound` | Repository `delete()` called for existing project |
| `updateProject_updatesAndReturnsProject` | Fields updated and persisted correctly |

```bash
# Run locally
./mvnw test
```

CI runs automatically via GitHub Actions on every push to `main`.

---

## 📖 API Documentation

Swagger UI is available when the app is running:

```
http://localhost:8080/swagger-ui/index.html
```

Every endpoint is listed with request/response schemas, validation rules, and a live "Try it out" button. No Postman needed.

---

## 🎓 What I learned

- How Spring's dependency injection wiring actually works under the hood
- The difference between `@RestController`, `@Service`, and `@Repository` layers and why the separation matters
- How to write a global exception handler with `@ControllerAdvice` instead of scattering try/catch blocks
- Why DTOs exist and why you should not expose entity objects directly from endpoints
- How JPA's `@Transactional` interacts with lazy loading, and how to get burned by it once and never again
- How JWT authentication works end to end, filter chain, token signing, stateless sessions
- Why circular dependencies happen in Spring and how to break them cleanly
- The difference between `depends_on` and a proper healthcheck in Docker Compose

---

## 🔁 What I would do differently next time

- Write tests before implementation, not after
- Use Flyway or Liquibase for schema migrations instead of `ddl-auto: update`
- Add pagination from the start, retrofitting `Pageable` is messier than building it in

---

## 👤 Author

**Djiby Sow Rebollo**

[![GitHub](https://img.shields.io/badge/GitHub-@Lancelcode-181717?style=for-the-badge&logo=github)](https://github.com/Lancelcode)

---

## 📄 Status

`Complete`, authentication, persistence, containerisation, and CI all in place.

---

## 🌱 Part of a larger learning arc

This project sits within a broader series of backend builds. See my [profile](https://github.com/Lancelcode) for the full picture, including PulseDB (database engine from scratch) and protocol level tool rebuilds.
