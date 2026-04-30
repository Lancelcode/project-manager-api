# Project Manager API

> A RESTful backend service for managing projects, tasks, and team workflows — built with Spring Boot and Java 21.

---

## Why I built this

Most project management tools are either too complex or too simple. This is a backend exercise in designing a clean REST API that handles real concerns: resource ownership, state transitions, and structured error responses. The goal was not to ship a product — it was to get Spring Boot, JPA, and REST design patterns into muscle memory.

---

## What it does

- Create and manage projects with status lifecycle (`DRAFT → ACTIVE → COMPLETED → ARCHIVED`)
- Assign tasks to projects with priority levels and due dates
- Filter and query projects by status, owner, or date range
- Structured error responses with meaningful HTTP status codes throughout
- Input validation with clear feedback on malformed requests

---

## Tech stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Build | Maven |
| Persistence | Spring Data JPA |
| Validation | Jakarta Bean Validation |
| Testing | JUnit 5, Mockito |

---

## Project structure

```
src/
├── main/java/
│   ├── controller/     # REST endpoints and request mapping
│   ├── service/        # Business logic layer
│   ├── repository/     # Data access layer (JPA)
│   ├── model/          # Entity definitions
│   ├── dto/            # Request/response objects
│   └── exception/      # Global error handling
└── test/
    ├── controller/     # Integration tests
    └── service/        # Unit tests
```

---

## API overview

```
POST   /api/projects              Create a new project
GET    /api/projects              List all projects (supports filtering)
GET    /api/projects/{id}         Get a project by ID
PUT    /api/projects/{id}         Update a project
DELETE /api/projects/{id}         Delete a project

POST   /api/projects/{id}/tasks   Add a task to a project
GET    /api/projects/{id}/tasks   List tasks for a project
PUT    /api/tasks/{id}            Update a task
DELETE /api/tasks/{id}            Remove a task
```

---

## Run locally

**Prerequisites:** Java 21, Maven

```bash
# Clone the repo
git clone https://github.com/Lancelcode/project-manager-api.git
cd project-manager-api

# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test
```

The API starts on `http://localhost:8080`

---

## Example request

```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Portfolio Rebuild",
    "description": "Redesign and redeploy personal projects",
    "status": "ACTIVE"
  }'
```

---

## What I learned

- How Spring's dependency injection wiring actually works under the hood
- The difference between `@RestController`, `@Service`, and `@Repository` layers and why the separation matters
- How to write a global exception handler with `@ControllerAdvice` instead of scattering try/catch blocks
- Why DTOs exist and why you should not expose entity objects directly from endpoints
- How JPA's `@Transactional` interacts with lazy loading — and how to get burned by it once and never again

---

## What I would do differently next time

- Add JWT authentication from the start rather than retrofitting it
- Write tests before the implementation, not after
- Use Flyway or Liquibase for schema migrations instead of `ddl-auto: update`

---

## Status

`In progress` — core CRUD complete, adding task priority logic and pagination next.

---

## Part of a larger learning arc

This project sits within a broader series of backend builds. See my [profile](https://github.com/Lancelcode) for the full picture — including PulseDB (database engine from scratch) and protocol-level tool rebuilds.
