# Project Manager API

> A RESTful backend service for managing projects, tasks, and team workflows, built with Spring Boot and Java 21.

---

## Why I built this

Most project management tools are either too complex or too simple. This is a backend exercise in designing a clean REST API that handles real concerns: resource ownership, state transitions, and structured error responses. The goal was not to ship a product, it was to get Spring Boot, JPA, and REST design patterns into muscle memory.

---

## What it does

- Create and manage projects with status lifecycle (`PLANNED → IN_PROGRESS → COMPLETED`)
- Assign tasks to projects with priority levels (`LOW`, `MEDIUM`, `HIGH`) and due dates
- JWT-based authentication — every endpoint is protected, register and login to get a token
- Structured error responses with meaningful HTTP status codes throughout
- Input validation with clear feedback on malformed requests
- Auto-generated API documentation via Swagger UI at `/swagger-ui/index.html`

---

## Tech stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Build | Maven |
| Persistence | Spring Data JPA + PostgreSQL |
| Auth | Spring Security + JWT (jjwt) |
| Validation | Jakarta Bean Validation |
| Testing | JUnit 5, Mockito |
| Containerisation | Docker + docker-compose |
| CI | GitHub Actions |

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
│   ├── security/       # JWT filter, auth config
│   └── exception/      # Global error handling
└── test/
    └── service/        # Unit tests (ProjectService)
```

---

## API overview

### Auth (public)
```
POST   /auth/register             Register a new user, returns JWT
POST   /auth/login                Login, returns JWT
```

### Projects (requires Bearer token)
```
POST   /projects                  Create a new project
GET    /projects                  List all projects
GET    /projects/{id}             Get a project by ID
PUT    /projects/{id}             Update a project
DELETE /projects/{id}             Delete a project
```

### Tasks (requires Bearer token)
```
POST   /projects/{id}/tasks       Add a task to a project
GET    /projects/{id}/tasks       List tasks for a project
GET    /projects/{id}/tasks/{tid} Get a specific task
PUT    /projects/{id}/tasks/{tid} Update a task
DELETE /projects/{id}/tasks/{tid} Remove a task
```

---

## Run locally

### Option 1 - Docker (recommended)

**Prerequisites:** Docker Desktop

```bash
git clone https://github.com/Lancelcode/project-manager-api.git
cd project-manager-api
docker-compose up --build
```

The API starts on `http://localhost:8080`

### Option 2 - Maven

**Prerequisites:** Java 21, Maven

```bash
./mvnw spring-boot:run
```

Runs with H2 in-memory database. Data resets on restart.

---

## Example usage

**Register:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "you@example.com", "password": "secret123"}'
```

**Create a project (use your token):**
```bash
curl -X POST http://localhost:8080/projects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name": "Portfolio Rebuild",
    "description": "Redesign and redeploy personal projects",
    "status": "PLANNED"
  }'
```

**Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

---

## Run tests

```bash
./mvnw test
```

Tests run automatically on every push to `main` via GitHub Actions.

---

## What I learned

- How Spring's dependency injection wiring actually works under the hood
- The difference between `@RestController`, `@Service`, and `@Repository` layers and why the separation matters
- How to write a global exception handler with `@ControllerAdvice` instead of scattering try/catch blocks
- Why DTOs exist and why you should not expose entity objects directly from endpoints
- How JPA's `@Transactional` interacts with lazy loading — and how to get burned by it once and never again
- How JWT authentication works end to end — filter chain, token signing, stateless sessions
- Why circular dependencies happen in Spring and how to break them cleanly
- The difference between `depends_on` and a proper healthcheck in Docker Compose

---

## What I would do differently next time

- Write tests before the implementation, not after
- Use Flyway or Liquibase for schema migrations instead of `ddl-auto: update`
- Add pagination from the start — retrofitting `Pageable` is messier than building it in

---

## Status

`Complete` — authentication, persistence, containerisation, and CI all in place.

---

## Part of a larger learning arc

This project sits within a broader series of backend builds. See my [profile](https://github.com/Lancelcode) for the full picture — including PulseDB (database engine from scratch) and protocol-level tool rebuilds.
