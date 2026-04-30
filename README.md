---

## Run locally

### Option 1 — Docker (recommended)

**Prerequisites:** Docker Desktop

```bash
git clone https://github.com/Lancelcode/project-manager-api.git
cd project-manager-api
docker-compose up --build
```

The API starts on `http://localhost:8080`

### Option 2 — Maven

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

`Complete` — authentication, persistence, containerisation, and CI all in place. Next project focuses on [your next project idea here].

---

## Part of a larger learning arc

This project sits within a broader series of backend builds. See my [profile](https://github.com/Lancelcode) for the full picture — including PulseDB (database engine from scratch) and protocol-level tool rebuilds.
