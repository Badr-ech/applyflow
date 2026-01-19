#!/bin/bash

echo "========================================="
echo " ApplyFlow - Complete Setup Script"
echo "========================================="
echo ""

echo "[1/5] Building Backend..."
cd backend
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "ERROR: Backend build failed!"
    exit 1
fi
echo "Backend build complete!"
echo ""

echo "[2/5] Installing Frontend Dependencies..."
cd ../frontend
npm install
if [ $? -ne 0 ]; then
    echo "ERROR: Frontend dependencies installation failed!"
    exit 1
fi
echo "Frontend dependencies installed!"
echo ""

echo "[3/5] Building Frontend..."
npm run build
if [ $? -ne 0 ]; then
    echo "ERROR: Frontend build failed!"
    exit 1
fi
echo "Frontend build complete!"
echo ""

echo "[4/5] Starting Backend Server..."
cd ../backend
mvn spring-boot:run &
BACKEND_PID=$!
sleep 10
echo "Backend server starting (PID: $BACKEND_PID)..."
echo ""

echo "[5/5] Starting Frontend Server..."
cd ../frontend
ng serve &
FRONTEND_PID=$!
echo "Frontend server starting (PID: $FRONTEND_PID)..."
echo ""

echo "========================================="
echo " Setup Complete!"
echo "========================================="
echo ""
echo "Backend:  http://localhost:8080"
echo "Frontend: http://localhost:4200"
echo "Swagger:  http://localhost:8080/swagger-ui.html"
echo ""
echo "Login with: admin / admin123"
echo ""
echo "Press Ctrl+C to stop all servers"
wait
