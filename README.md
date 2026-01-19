# ApplyFlow - Event-Driven Job Application Tracking Platform

A full-stack web application for tracking job applications with event-driven architecture, built with Spring Boot and Angular.

## 🎯 Features

- **Job Application Lifecycle Management** - Track applications through states: Applied → Interview → Offer → Hired/Rejected
- **Event-Driven Architecture** - Domain events trigger audit logs, notifications, and analytics
- **Real-time Notifications** - Get notified of status changes
- **Analytics Dashboard** - View application statistics and success rates
- **Timeline View** - See complete history of each application
- **REST API** - Fully documented with Swagger/OpenAPI

## 🏗️ Architecture

```
Frontend (Angular 17+)           Backend (Spring Boot 3.2)
├── Dashboard                    ├── REST API Controllers
├── Applications List             ├── Service Layer
├── Application Detail            ├── Event-Driven System
├── Analytics                     │   ├── Domain Events
├── Notifications                 │   ├── Event Listeners
└── Timeline View                 │   └── Transactional Publishing
                                 ├── JPA Repositories
                                 └── PostgreSQL Database
```

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Node.js 18+
- Maven 3.6+
- Docker & Docker Compose (optional)

### Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

- API Documentation: http://localhost:8080/swagger-ui.html
- H2 Console (dev): http://localhost:8080/h2-console

**Default Credentials:**
- Username: `admin`
- Password: `admin123`

### Frontend Setup

```bash
cd frontend
npm install
ng serve
```

The frontend will start on `http://localhost:4200`

### Docker Deployment

```bash
docker-compose up --build
```

This starts:
- Backend on port 8080
- Frontend on port 4200
- PostgreSQL on port 5432

## 📁 Project Structure

```
applyflow/
├── backend/
│   ├── src/main/java/com/applyflow/
│   │   ├── api/              # REST Controllers
│   │   ├── domain/           # Entities & Enums
│   │   ├── event/            # Domain Events & Listeners
│   │   ├── repository/       # JPA Repositories
│   │   ├── service/          # Business Logic
│   │   └── config/           # Configuration & Security
│   └── src/main/resources/
│       ├── application.yml    # Configuration
│       └── db/migration/      # Flyway Migrations
└── frontend/
    └── src/app/
        ├── core/             # Services & Models
        ├── features/         # Feature Components
        └── shared/           # Shared Components
```

## 🔌 API Endpoints

### Applications
```
POST   /api/applications              Create application
GET    /api/applications              List all applications
GET    /api/applications/{id}         Get application details
POST   /api/applications/{id}/transition   Change status
GET    /api/applications/{id}/timeline     Get status history
DELETE /api/applications/{id}         Delete application
```

### Analytics
```
GET    /api/analytics/summary         Get statistics
```

### Notifications
```
GET    /api/notifications             List all notifications
GET    /api/notifications/unread      Get unread notifications
PATCH  /api/notifications/{id}/read   Mark as read
POST   /api/notifications/mark-all-read   Mark all as read
```

## 🎨 Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Security (Basic Auth)
- Spring Events (Event-Driven)
- PostgreSQL / H2
- Flyway
- Swagger/OpenAPI
- Lombok
- Maven

### Frontend
- Angular 17+
- Angular Material
- RxJS
- TypeScript
- SCSS
- Responsive Design

### DevOps
- Docker
- Docker Compose
- Git

## 🔐 Security

- Basic Authentication (username/password)
- CORS configured for localhost development
- Session management: Stateless
- Password encoding: BCrypt

**Demo Users:**
- admin/admin123 (ADMIN role)
- demo/demo (USER role)

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## 📊 Database Schema

### job_applications
- Stores application data with current status
- Indexed on status, company, and applied_date

### application_events
- Immutable audit log of status transitions
- Foreign key to job_applications

### notifications
- System-generated notifications
- Linked to applications

## 🔄 Event Flow

```
1. User transitions application status
2. Service validates and saves changes
3. Domain event published (after transaction commit)
4. Event listeners execute:
   - AuditEventListener → Logs to console
   - NotificationEventListener → Creates notification
   - AnalyticsEventListener → Updates metrics
```

## 🌐 Environment Configuration

### Development
- Backend: H2 in-memory database
- Frontend: Proxy to localhost:8080
- Demo data auto-loaded

### Production
- Backend: PostgreSQL database
- Environment variables for DB connection
- Demo data disabled

## 📝 Sample Data

The development profile includes sample data:
- 10+ job applications
- Various statuses (Applied, Interview, Offer, Hired, Rejected)
- Historical state transitions
- Realistic company and position data

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Backend
lsof -ti:8080 | xargs kill -9

# Frontend
lsof -ti:4200 | xargs kill -9
```

### Database Issues
```bash
# Reset H2 database
rm -rf ~/applyflow.mv.db

# Reset Docker volumes
docker-compose down -v
```

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Angular Documentation](https://angular.dev)
- [Docker Documentation](https://docs.docker.com)

## 👥 Authors

ApplyFlow Team

## 📄 License

This project is for educational and demonstration purposes.

---

**Happy Tracking! 🎉**
