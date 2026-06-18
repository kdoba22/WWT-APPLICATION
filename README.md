# WWT Incident Tracker

A full-stack incident tracking application for recording, viewing, and managing incidents from open through resolution. The app includes a Spring Boot REST API, an H2 in-memory database with seed data, and an Angular frontend with a reactive form and incident table.

## Features

- **Incident management** — Create and view incidents with name, date, owner, severity, details, and notes
- **Opened-by tracking** — Record who opened each incident separately from the incident owner
- **Severity levels** — Dropdown with Critical, High, Medium, and Low
- **Incident table** — Homepage loads all incidents in a sortable table view
- **Person records** — Backend `persons` table linked to incidents via foreign key
- **Seed data** — Sample incidents and persons loaded automatically on startup
- **CORS support** — Backend configured for local Angular development
- **Test coverage** — Jest unit tests (frontend) and JUnit tests (backend)

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | Angular 20, Reactive Forms, RxJS, TypeScript |
| Backend | Spring Boot 4.1, Spring Data JPA, Spring Web MVC |
| Database | H2 (in-memory) |
| Build (backend) | Maven, Java 21 |
| Build (frontend) | Angular CLI, npm |
| Frontend tests | Jest, jest-preset-angular |
| Backend tests | JUnit 5, Mockito, Spring Boot Test |

## Prerequisites

- **Java 21+**
- **Node.js 20+** and **npm**
- **Git**

## Project Structure

```
wwt-application/
├── backend/                          # Spring Boot REST API
│   └── src/main/java/com/example/demo/
│       ├── config/
│       │   ├── CorsConfig.java       # CORS configuration
│       │   └── DataLoader.java       # Seed data on startup
│       ├── incident/
│       │   ├── Incident.java         # JPA entity
│       │   ├── IncidentController.java
│       │   ├── IncidentRepository.java
│       │   └── CreateIncidentRequest.java
│       └── person/
│           ├── Person.java           # JPA entity (FK to Incident)
│           └── PersonRepository.java
├── frontend/                         # Angular SPA
│   └── src/app/
│       ├── app.ts / app.html         # Main component (table + form)
│       └── incident/
│           ├── incident.service.ts   # HTTP client
│           └── incident.model.ts     # TypeScript interfaces
└── README.md
```

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd wwt-application
```

### 2. Start the backend

```bash
cd backend
./mvnw spring-boot:run
```

The API starts at **http://localhost:8080**.

### 3. Start the frontend

In a new terminal:

```bash
cd frontend
npm install
npm start
```

The app opens at **http://localhost:4200**.

> **Note:** The frontend calls `http://localhost:8080/api` directly in development (see `frontend/src/environments/environment.development.ts`). Ensure the backend is running before opening the app.

## API Reference

Base URL: `http://localhost:8080/api`

### GET `/incidents`

Returns all incidents.

**Response example:**

```json
[
  {
    "id": 1,
    "incidentName": "Server outage",
    "incidentOwner": "Jane Smith",
    "incidentOpenedBy": "John Doe",
    "incidentSeverity": "High",
    "incidentDate": "2026-06-15",
    "incidentDetails": "Primary database unavailable for 45 minutes",
    "incidentNotes": "Failover completed and services restored"
  }
]
```

### POST `/incidents`

Creates a new incident. Returns `201 Created` with the saved record (including generated `id`).

**Request body:**

```json
{
  "incidentName": "Server outage",
  "incidentOwner": "Jane Smith",
  "incidentOpenedBy": "John Doe",
  "incidentSeverity": "High",
  "incidentDate": "2026-06-18",
  "incidentDetails": "Primary database unavailable",
  "incidentNotes": "Escalated to on-call engineer"
}
```

**Example curl:**

```bash
curl http://localhost:8080/api/incidents

curl -X POST http://localhost:8080/api/incidents \
  -H "Content-Type: application/json" \
  -d '{
    "incidentName": "Server outage",
    "incidentOwner": "Jane Smith",
    "incidentOpenedBy": "John Doe",
    "incidentSeverity": "High",
    "incidentDate": "2026-06-18",
    "incidentDetails": "Primary database unavailable",
    "incidentNotes": "Escalated to on-call engineer"
  }'
```

## Data Model

### Incidents (`incidents` table)

| Field | Type | Required | Description |
|---|---|---|---|
| `id` | long | Auto | Primary key |
| `incidentName` | string | Yes | Short title of the incident |
| `incidentOwner` | string | Yes | Person responsible for the incident |
| `incidentOpenedBy` | string | Yes | Person who opened/reported the incident |
| `incidentSeverity` | string | Yes | Critical, High, Medium, or Low |
| `incidentDate` | date | Yes | Date the incident occurred (ISO `YYYY-MM-DD`) |
| `incidentDetails` | string | Yes | Description of what happened |
| `incidentNotes` | string | No | Additional notes or follow-up |

### Persons (`persons` table)

| Field | Type | Required | Description |
|---|---|---|---|
| `id` | long | Auto | Primary key |
| `userName` | string | Yes | Person's name |
| `incident_id` | long | Yes | Foreign key to `incidents.id` |

## Seed Data

On first startup, `DataLoader` populates the database with **4 sample incidents** and **7 linked persons** if the database is empty:

| Incident | Severity | Owner |
|---|---|---|
| Server outage | High | Jane Smith |
| Login failure spike | Medium | Michael Chen |
| Payment processing delay | Critical | Aisha Patel |
| Email notification delay | Low | Emily Davis |

Restart the backend to reset the in-memory H2 database and reload seed data.

## Frontend

The Angular app opens with an **Incidents** table showing all records, followed by a **Create Incident** form.

### Form fields

- Incident Name
- Incident Date
- Incident Owner
- Opened By
- Incident Severity (dropdown: Critical → High → Medium → Low)
- Incident Details
- Incident Notes

Submitting a valid form POSTs to the API and refreshes the table.

### Environment configuration

| Environment | API URL |
|---|---|
| Development | `http://localhost:8080/api` |
| Production | `/api` |

### Dev proxy (optional)

`frontend/proxy.conf.json` proxies `/api` to `http://localhost:8080` when using `ng serve`. Restart the dev server after changing proxy settings.

## CORS

The backend allows browser requests from:

- `http://localhost:4200`
- `http://127.0.0.1:4200`

Configured in `backend/src/main/resources/application.properties` and `CorsConfig.java`. Add production frontend origins to `app.cors.allowed-origins` as needed.

## H2 Console

When the backend is running, the H2 web console is available at:

**http://localhost:8080/h2-console**

| Setting | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(empty)* |

## Testing

### Backend (JUnit)

```bash
cd backend
./mvnw test
```

**14 tests** covering controllers, repositories, entities, CORS, data seeding, and application context.

| Test class | Coverage |
|---|---|
| `IncidentControllerTest` | Integration GET/POST |
| `IncidentControllerUnitTest` | Controller with mocked repository |
| `IncidentRepositoryTest` | JPA persistence |
| `IncidentTest` | Entity fields |
| `CreateIncidentRequestTest` | Request DTO |
| `PersonRepositoryTest` | Person–incident relationship |
| `PersonTest` | Entity fields |
| `DataLoaderTest` | Startup seed data |
| `DataLoaderUnitTest` | Seed/skip logic |
| `CorsConfigTest` | CORS preflight |
| `DemoApplicationTests` | Spring context |

### Frontend (Jest)

```bash
cd frontend
npm test
```

**17 tests** across 5 suites covering the main component, HTTP service, app config, and environments.

| Test file | Coverage |
|---|---|
| `app.spec.ts` | Table load, form validation, submit, error states |
| `incident.service.spec.ts` | GET/POST HTTP calls |
| `app.config.spec.ts` | Application providers |
| `environment.spec.ts` | Production config |
| `environment.development.spec.ts` | Development config |

To run tests in watch mode:

```bash
cd frontend && npm run test:watch
```

The legacy Karma runner is still available:

```bash
cd frontend && npm run test:karma
```

## Building for Production

### Backend

```bash
cd backend
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Frontend

```bash
cd frontend
npm run build
```

Output is written to `frontend/dist/wwt-app/`.

## Configuration

### Backend (`application.properties`)

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
app.cors.allowed-origins=http://localhost:4200,http://127.0.0.1:4200
```

### Frontend ports

| Service | Port |
|---|---|
| Angular dev server | 4200 |
| Spring Boot API | 8080 |

## Troubleshooting

| Issue | Solution |
|---|---|
| "Failed to load incidents" | Ensure the backend is running on port 8080 |
| Port 8080 already in use | Stop the existing process or change the server port in `application.properties` |
| Port 4200 already in use | Stop the old dev server and run `npm start` again |
| Empty incident table | Restart the backend to reload H2 seed data |
| CORS errors | Verify `app.cors.allowed-origins` includes your frontend URL |

## License

This project was created as part of a WWT technical interview.
