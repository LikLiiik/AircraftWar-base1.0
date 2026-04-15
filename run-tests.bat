@echo off
echo ========================================
echo Running JUnit5 Tests for AircraftWar
echo ========================================
echo.

cd /d %~dp0

echo Compiling test classes...
javac -d target/classes -cp "target/classes;lib/junit-platform-console-standalone-1.10.0.jar" test/edu/hitsz/aircraft/*.java

echo.
echo Running tests...
java -cp "target/classes;lib/junit-platform-console-standalone-1.10.0.jar" org.junit.platform.console.ConsoleLauncher --scan-class-path target/classes

echo.
echo ========================================
echo Test execution completed
echo ========================================
pause
