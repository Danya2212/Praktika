$root = Split-Path -Parent $MyInvocation.MyCommand.Path

Write-Host "Starting Eureka server..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$root\eureka'; .\mvnw.cmd spring-boot:run" -WindowStyle Minimized

Start-Sleep -Seconds 25

Write-Host "Starting location instance 1 (port 8081)..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$root\location'; .\mvnw.cmd spring-boot:run" -WindowStyle Minimized

Write-Host "Starting location instance 2 (port 8083, profile instance2)..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$root\location'; .\mvnw.cmd spring-boot:run '-Dspring-boot.run.profiles=instance2'" -WindowStyle Minimized

Start-Sleep -Seconds 20

Write-Host "Starting person instance 1 (port 8080)..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$root\person'; .\mvnw.cmd spring-boot:run" -WindowStyle Minimized

Write-Host "Starting person instance 2 (port 8084, profile instance2)..."
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$root\person'; .\mvnw.cmd spring-boot:run '-Dspring-boot.run.profiles=instance2'" -WindowStyle Minimized

Write-Host ""
Write-Host "All services are starting."
Write-Host "Open Eureka dashboard: http://localhost:8761"
Write-Host "Expected instances: LOCATION (8081, 8083), PERSON (8080, 8084)"
