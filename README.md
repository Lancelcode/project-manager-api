<div align="center">

# Project Manager API

**A backend API for managing projects and tasks, built with Java 21 and Spring Boot**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![CI](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)](https://github.com/Lancelcode/project-manager-api/actions)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Tests](https://img.shields.io/badge/Tests-6_passing-198754?style=for-the-badge)](#testing)

*JWT authentication, PostgreSQL persistence, Docker containerisation, and a CI pipeline running on every push. Built from scratch as a deliberate exercise in production thinking.*

[Quick Start](#quick-start) · [Authentication](#authentication) · [Endpoints](#api-endpoints) · [Tests](#testing) · [Documentation](#api-documentation)

</div>

---

## About

This project started as a way to build real backend muscle memory, not just follow a tutorial. Every layer was written by hand: the security filter chain, the JWT implementation, the exception handling, the Docker setup, and the CI pipeline.

The goal was to understand how a production Spring Boot application actually fits together, and to make decisions that would hold up beyond a learning context.

---

## What it does

| Feature | Description |
|---|---|
| 🔐 Authentication | JWT based registration and login. Every endpoint is protected by default |
| 🗂️ Project Management | Full CRUD with a clear status lifecycle — `PLANNED → IN_PROGRESS → COMPLETED` |
| ✅ Task Management | Tasks are scoped to projects, with priority levels and due dates |
| 🛡️ Error Handling | All errors return structured JSON with appropriate HTTP status codes |
| ✔️ Validation | Jakarta Bean Validation on all request bodies, with readable error messages |
| 📖 API Docs | Swagger UI auto-generated from the codebase, always in sync with the code |
| 🐳 Docker | One command brings up the full stack, app and database included |
| ⚙️ CI | Tests run automatically on every push via GitHub Actions |

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Build | Maven |
| Persistence | Spring Data JPA, PostgreSQL 16 |
| Authentication | Spring Security, jjwt 0.12.6 |
| Validation | Jakarta Bean Validation |
| Testing | JUnit 5, Mockito |
| Infrastructure | Docker, docker-compose |
| CI | GitHub Actions |
| Documentation | SpringDoc OpenAPI, Swagger UI |

---

## Project Structure

```
src/
├── main/java/
│   ├── controller/     # Request mapping and HTTP layer
│   ├── service/        # Business logic
│   ├── repository/     # Data access via Spring Data JPA
│   ├── model/          # JPA entities
│   ├── dto/            # Request and response objects, decoupled from entities
│   ├── security/       # JWT filter, UserDetailsService, security configuration
│   └── exception/      # Global exception handler and custom exception types
└── test/
    └── service/        # Unit tests for ProjectService
```

---

## API Endpoints

### Auth (no token required)

```
POST   /auth/register       Create an account and receive a JWT token
POST   /auth/login          Log in and receive a JWT token
```

### Projects (token required)

```
POST   /projects            Create a project
GET    /projects            List all projects
GET    /projects/{id}       Get a single project
PUT    /projects/{id}       Update a project
DELETE /projects/{id}       Delete a project
```

### Tasks (token required)

```
POST   /projects/{id}/tasks             Add a task to a project
GET    /projects/{id}/tasks             List all tasks on a project
GET    /projects/{id}/tasks/{taskId}    Get a single task
PUT    /projects/{id}/tasks/{taskId}    Update a task
DELETE /projects/{id}/tasks/{taskId}    Delete a task
```

---

## Authentication

Every endpoint except `/auth/register` and `/auth/login` requires a valid JWT in the `Authorization` header.

**Register:**
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

**Authenticated request:**
```bash
curl http://localhost:8080/projects \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

## Quick Start

### Option 1: Docker

Requires Docker Desktop. Brings up the application and a PostgreSQL database with a single command.

```bash
git clone https://github.com/Lancelcode/project-manager-api.git
cd project-manager-api
docker-compose up --build
```

The API is available at `http://localhost:8080`.

```bash
docker-compose down      # stop
docker-compose down -v   # stop and clear the database
```

### Option 2: Maven

Requires Java 21. Runs against an H2 in memory database. Data is lost on restart.

```bash
./mvnw spring-boot:run
```

---

## Testing

Six unit tests covering the core service layer, all passing, executed automatically on every push to `main`.

| Test | What it verifies |
|---|---|
| `getAllProjects_returnsAllProjects` | All projects are retrieved from the repository |
| `getProjectById_returnsProject_whenFound` | The correct project is returned for a given ID |
| `getProjectById_throwsException_whenNotFound` | A `ProjectNotFoundException` is thrown when the ID does not exist |
| `createProject_savesAndReturnsProject` | The project is persisted and the response is correctly mapped |
| `deleteProject_deletesProject_whenFound` | The repository delete method is called for a valid project |
| `updateProject_updatesAndReturnsProject` | Updated fields are saved and returned correctly |

```bash
./mvnw test
```

---

## API Documentation

Swagger UI is served at `http://localhost:8080/swagger-ui/index.html` when the application is running. It reflects the current state of the API at all times and includes a built-in interface for making requests.

---

## What I learned

Building this end to end taught me more than any tutorial because the mistakes were mine to fix.

- Spring's dependency injection makes sense once you understand the container. Before that it feels like magic, and not the good kind.
- DTOs are not optional. Exposing entities directly from endpoints creates tight coupling that compounds over time.
- A global exception handler with `@ControllerAdvice` is one of those things that seems like overhead until the first time you need it.
- JWT authentication is straightforward once you understand the filter chain. The tricky part is avoiding circular dependencies in the security configuration.
- Docker Compose `depends_on` does not mean "wait until the database is ready." A healthcheck is required. I learned this the hard way.
- Writing tests after the fact is harder than writing them first. The code tends not to be structured for testability.

---

## What comes next

- Flyway for database migrations instead of `ddl-auto: update`
- Pagination on list endpoints
- Tests written before the implementation, not after

---

## Author

**Djiby Sow Rebollo**

[![GitHub](https://img.shields.io/badge/GitHub-@Lancelcode-181717?style=for-the-badge&logo=github)](https://github.com/Lancelcode)

---

## Status

Complete. Authentication, persistence, containerisation, and CI are all in place.

---

## Part of a larger learning arc

This project sits within a broader series of backend builds. See my [profile](https://github.com/Lancelcode) for the full picture, including PulseDB (database engine from scratch) and protocol level tool rebuilds.
