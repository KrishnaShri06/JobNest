@echo off
REM JobNest Build Script
REM Compiles Java files and creates WAR file for deployment

echo ====================================
echo JobNest Build Script
echo ====================================
echo.

REM Set variables
set PROJECT_DIR=%~dp0
set SRC_DIR=%PROJECT_DIR%src
set WEB_DIR=%PROJECT_DIR%web
set BUILD_DIR=%PROJECT_DIR%build
set CLASSES_DIR=%BUILD_DIR%\WEB-INF\classes
set LIB_DIR=%BUILD_DIR%\WEB-INF\lib
set DIST_DIR=%PROJECT_DIR%dist

REM Set Tomcat lib path
set TOMCAT_LIB=C:\Program Files\Apache Software Foundation\Tomcat 10.1\lib

REM Set Java bin path (to find jar.exe)
set JAVA_BIN=C:\Program Files\Java\jdk-25.0.2\bin

echo Step 1: Cleaning previous build...
if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
if exist "%DIST_DIR%" rmdir /s /q "%DIST_DIR%"
mkdir "%CLASSES_DIR%"
mkdir "%LIB_DIR%"
mkdir "%DIST_DIR%"
echo Done.
echo.

echo Step 2: Compiling Java files...
javac -d "%CLASSES_DIR%" -cp "%TOMCAT_LIB%\*" "%SRC_DIR%\com\jobnest\model\*.java" "%SRC_DIR%\com\jobnest\util\*.java" "%SRC_DIR%\com\jobnest\dao\*.java" "%SRC_DIR%\com\jobnest\servlets\*.java"

if errorlevel 1 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)
echo Compilation successful.
echo.

echo Step 3: Copying resources...
copy "%SRC_DIR%\db.properties" "%CLASSES_DIR%\"
xcopy "%WEB_DIR%\*" "%BUILD_DIR%\" /E /I /Y
echo Done.
echo.

echo Step 4: Automating MySQL Connector setup...
if exist "%WEB_DIR%\WEB-INF\lib\mysql-connector-j-9.6.0.jar" (
    copy "%WEB_DIR%\WEB-INF\lib\mysql-connector-j-9.6.0.jar" "%LIB_DIR%\"
    echo MySQL Connector copied from web/WEB-INF/lib.
) else (
    echo WARNING: MySQL Connector not found in web/WEB-INF/lib!
    echo Please ensure it is present to successfully run the application.
)
echo.

echo Step 5: Creating WAR file...
cd "%BUILD_DIR%"
"%JAVA_BIN%\jar.exe" -cvf "%DIST_DIR%\JobApp.war" *
cd "%PROJECT_DIR%"
echo WAR file created: %DIST_DIR%\JobApp.war
echo.

echo ====================================
echo Build Complete!
echo ====================================
echo.
echo Next steps:
echo 1. Copy %DIST_DIR%\JobApp.war to Tomcat's webapps folder
echo 2. Start Tomcat server
echo 3. Access application at: http://localhost:8080/JobApp
echo.
pause
