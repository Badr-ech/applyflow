# 🚀 ApplyFlow - Quick Start Guide

## Prerequisites Check

Before starting, ensure you have:
- ✅ Java 17 or higher: `java -version`
- ✅ Maven 3.6+: `mvn -version` (or use included `./mvnw`)
- ✅ Node.js 18+: `node --version`
- ✅ npm: `npm --version`

## 🎯 Fastest Way to Run (2 Steps)

### Step 1: Start Backend
```powershell
cd backend
./mvnw spring-boot:run
```

Wait for: `Started ApplyFlowApplication in X seconds`

### Step 2: Start Frontend (in new terminal)
```powershell
cd frontend
npm install
ng serve
```

Wait for: `Compiled successfully`

## 🌐 Access the Application

Open your browser to:
- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html

**Login**: `admin` / `admin123`

## 📊 What You'll See

### Swagger UI (Backend API Testing)
1. Visit http://localhost:8080/swagger-ui.html
2. Click "Authorize" button (top right)
3. Enter: Username `admin`, Password `admin123`
4. Click "Authorize" then "Close"
5. Try these endpoints:
   - **GET /api/applications** - See 10+ sample applications
   - **GET /api/analytics/summary** - View statistics
   - **GET /api/notifications** - See auto-generated notifications

### Frontend (Currently)
The frontend structure is ready with:
- Navigation toolbar
- Routing setup
- API service configured
- Material design theme

Components need implementation - see IMPLEMENTATION.md for details.

## 🧪 Quick API Test

### Using curl (Windows PowerShell):
```powershell
# Get all applications
$headers = @{ Authorization = "Basic " + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("admin:admin123")) }
Invoke-RestMethod -Uri "http://localhost:8080/api/applications" -Headers $headers | ConvertTo-Json
```

### Using Swagger:
1. Go to http://localhost:8080/swagger-ui.html
2. Authorize with admin/admin123
3. Expand "GET /api/applications"
4. Click "Try it out"
5. Click "Execute"

## 🎨 What's Working Now

### ✅ Backend (100% Functional)
- All REST API endpoints
- Event-driven state transitions
- Audit logging
- Notifications
- Analytics
- Demo data (10+ applications)
- Swagger documentation
- Database migrations
- Spring Security

### 🔨 Frontend (Structure Ready)
- Project scaffold
- Routing
- API service
- Material UI
- Authentication headers
- Models and interfaces

**Components need implementation** - see instructions below.

## 📝 Sample Operations

### Create a New Application
POST to `/api/applications`:
```json
{
  "company": "Apple",
  "position": "iOS Engineer",
  "location": "Cupertino, CA",
  "salaryExpectation": 170000,
  "notes": "Dream job!"
}
```

### Transition Application Status
POST to `/api/applications/1/transition`:
```json
{
  "newStatus": "INTERVIEW",
  "comment": "Phone screen scheduled"
}
```

This will:
1. Update the application
2. Create an audit event
3. Generate a notification
4. Update analytics

### View Timeline
GET `/api/applications/1/timeline`

Shows complete history of status changes.

## 🔍 Verify Event System

1. Transition an application (via Swagger)
2. Check backend console logs for:
   - `AUDIT: Application X transitioned...`
   - `Notification created for application X`
3. Check `/api/notifications` for new notification
4. Check `/api/analytics/summary` for updated counts

## ⚙️ Configuration

### Backend Profiles

**Development (default)**:
- H2 in-memory database
- Demo data enabled
- H2 console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:applyflow`
  - Username: `sa`
  - Password: (empty)

**Production**:
- PostgreSQL database
- Set environment variables:
  ```
  DATABASE_URL=jdbc:postgresql://localhost:5432/applyflow
  DATABASE_USER=applyflow
  DATABASE_PASSWORD=your_password
  ```

### Frontend Environment

Edit `frontend/src/environments/environment.development.ts`:
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  auth: {
    username: 'admin',
    password: 'admin123'
  }
};
```

## 🐳 Docker Option

Want everything in containers?
```bash
docker-compose up --build
```

This starts:
- PostgreSQL on :5432
- Backend on :8080
- Frontend on :4200

## ❓ Troubleshooting

### Port 8080 already in use
```powershell
# Find process
netstat -ano | findstr :8080
# Kill it (replace PID)
taskkill /F /PID <PID>
```

### Maven not found
Use included wrapper:
- Windows: `mvnw.cmd`
- Linux/Mac: `./mvnw`

### Angular CLI not found
```bash
npm install -g @angular/cli
```

### Can't connect to API
1. Check backend is running: http://localhost:8080/actuator/health
2. Check CORS settings in SecurityConfig.java
3. Verify credentials in environment.ts

### No sample data
Check `application.yml`:
```yaml
applyflow:
  demo-data:
    enabled: true  # Should be true in dev profile
```

## 📚 Next Steps

1. **Explore the API** via Swagger UI
2. **Check the backend logs** to see events in action
3. **Implement Angular components** following IMPLEMENTATION.md
4. **Customize** the business logic for your needs

## 🎯 Key URLs Reference

| Service | URL | Description |
|---------|-----|-------------|
| Frontend | http://localhost:4200 | Angular UI |
| Backend | http://localhost:8080 | REST API |
| Swagger | http://localhost:8080/swagger-ui.html | API Docs |
| H2 Console | http://localhost:8080/h2-console | Database (dev) |
| Health Check | http://localhost:8080/actuator/health | Status |

## 💡 Pro Tips

1. Keep Swagger UI open while developing - it's your API playground
2. Watch the backend console logs - they show event flow
3. Use browser DevTools Network tab to debug API calls
4. The demo data resets every time you restart in dev mode

## 🆘 Need Help?

1. Check PROJECT_SUMMARY.md for complete overview
2. Read IMPLEMENTATION.md for detailed guides
3. Review README.md for architecture details
4. Examine backend code - it has natural comments explaining logic

---

**You're all set! The backend is fully functional. Start exploring via Swagger UI! 🎉**
