@echo off
echo =========================================
echo  ApplyFlow - Complete Setup Script
echo =========================================
echo.

echo [1/5] Building Backend...
cd backend
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Backend build failed!
    exit /b 1
)
echo Backend build complete!
echo.

echo [2/5] Installing Frontend Dependencies...
cd ..\frontend
call npm install
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Frontend dependencies installation failed!
    exit /b 1
)
echo Frontend dependencies installed!
echo.

echo [3/5] Building Frontend...
call npm run build
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Frontend build failed!
    exit /b 1
)
echo Frontend build complete!
echo.

echo [4/5] Starting Backend Server...
cd ..\backend
start "ApplyFlow Backend" cmd /k "mvn spring-boot:run"
timeout /t 10
echo Backend server starting...
echo.

echo [5/5] Starting Frontend Server...
cd ..\frontend
start "ApplyFlow Frontend" cmd /k "ng serve"
echo Frontend server starting...
echo.

echo =========================================
echo  Setup Complete!
echo =========================================
echo.
echo Backend:  http://localhost:8080
echo Frontend: http://localhost:4200
echo Swagger:  http://localhost:8080/swagger-ui.html
echo.
echo Login with: admin / admin123
echo.
echo Press any key to exit...
pause >nul
