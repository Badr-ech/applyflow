# ApplyFlow

A full-stack job application tracking platform with event-driven architecture. Built with Spring Boot 3.2 and Angular 17.

## Features

- **Application Management** - Track job applications through their lifecycle (Applied, Interview, Offer, Hired, Rejected)
- **Event-Driven Architecture** - Domain events for audit logging, notifications, and analytics
- **Real-time Notifications** - Status change alerts and application updates
- **Analytics Dashboard** - Application statistics and success rate tracking
- **Timeline View** - Complete history of each application's status changes

## Tech Stack

**Backend:** Java 21, Spring Boot 3.2, Spring Data JPA, Spring Security, PostgreSQL, Flyway, Maven

**Frontend:** Angular 17, Angular Material, RxJS, TypeScript, SCSS

**Infrastructure:** Docker, Docker Compose

## Getting Started

### Prerequisites

- Java 21+
- Node.js 18+
- Maven 3.6+
- Docker (optional)

### Running Locally

**Backend:**
```bash
cd backend
mvn spring-boot:run
```
API available at `http://localhost:8080`

**Frontend:**
```bash
cd frontend
npm install
ng serve
```
UI available at `http://localhost:4200`

**Docker:**
```bash
docker-compose up --build
```

### Default Credentials

- Username: `admin`
- Password: `admin123`

## Project Structure

```
applyflow/
├── backend/
│   └── src/main/java/com/applyflow/
│       ├── api/           # REST controllers
│       ├── domain/        # Entities and enums
│       ├── event/         # Domain events and listeners
│       ├── repository/    # Data access layer
│       ├── service/       # Business logic
│       └── config/        # Configuration
└── frontend/
    └── src/app/
        ├── core/          # Services and models
        └── features/      # Feature modules
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/applications` | List all applications |
| POST | `/api/applications` | Create new application |
| GET | `/api/applications/{id}` | Get application details |
| POST | `/api/applications/{id}/transition` | Change application status |
| GET | `/api/applications/{id}/timeline` | Get status history |
| GET | `/api/analytics/summary` | Get analytics data |
| GET | `/api/notifications` | List notifications |

## Architecture

The backend implements an event-driven architecture using Spring's `ApplicationEventPublisher`. When an application status changes:

1. Service layer validates and persists the change
2. Domain event is published after transaction commit
3. Event listeners handle audit logging, notifications, and analytics

## License

MIT
