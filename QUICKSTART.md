# JobNest - Quick Start Guide

## 🚀 Quick Deployment Steps

### 1. Database Setup (5 minutes)

**Run SQL Script:**
```bash
# Option 1: Command Line
mysql -u root -p < sql/init.sql

# Option 2: MySQL Workbench
# Open sql/init.sql and execute all queries
```

**Verify Database:**
```sql
USE jobnest;
SHOW TABLES;
SELECT * FROM jobs;
```

**Update Password (if needed):**
- Edit `src/db.properties`
- Change `db.password=` to your MySQL password

---

### 2. Download MySQL Connector

1. Visit: https://dev.mysql.com/downloads/connector/j/
2. Click "Platform Independent" → Download ZIP
3. Extract the file
4. Find `mysql-connector-j-8.x.x.jar`
5. Keep it ready for Step 3

---

### 3. Build the Application

**Update Tomcat Path in build.bat:**
```batch
set TOMCAT_LIB=C:\Program Files\Apache Software Foundation\Tomcat 10.1\lib
```
(Change to your actual Tomcat installation path)

**Run Build Script:**
```bash
# Double-click build.bat or run in terminal:
build.bat
```

**When prompted:**
- Copy `mysql-connector-j-8.x.x.jar` to `build\WEB-INF\lib\`
- Press any key to continue

**Result:** 
- WAR file created at `dist\JobApp.war`

---

### 4. Deploy to Tomcat

**Copy WAR file:**
```bash
# Copy dist\JobApp.war to Tomcat's webapps folder
# Example:
copy dist\JobApp.war "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\"
```

---

### 5. Start Tomcat

**Windows:**
```bash
cd "C:\Program Files\Apache Software Foundation\Tomcat 10.1\bin"
startup.bat
```

**Linux/Mac:**
```bash
cd /path/to/tomcat/bin
./startup.sh
```

---

### 6. Access Application

Open browser and navigate to:
```
http://localhost:8080/JobApp
```

**Expected Result:**
- Job listings page with 6 sample jobs
- Modern purple gradient design
- "Apply Now" buttons on each job

---

## ✅ Testing Checklist

- [ ] Jobs display correctly
- [ ] Click "Apply Now" on any job
- [ ] Application form shows job details
- [ ] Fill form: Name, Email, Phone, Cover Letter
- [ ] Submit form
- [ ] See success message
- [ ] Verify in database:
  ```sql
  USE jobnest;
  SELECT * FROM applications;
  ```

---

## 🐛 Common Issues & Fixes

### Issue 1: Build fails
**Solution:** 
- Check Java version: `java -version` (should be 21)
- Update TOMCAT_LIB path in build.bat

### Issue 2: ClassNotFoundException
**Solution:**
- Ensure MySQL Connector JAR is in `build\WEB-INF\lib\`
- Rebuild and redeploy

### Issue 3: Cannot connect to database
**Solution:**
- Check MySQL is running
- Verify password in `src/db.properties`
- Ensure database `jobnest` exists

### Issue 4: 404 Error
**Solution:**
- Wait 10-20 seconds for Tomcat to deploy
- Check `webapps` folder for `JobApp` directory
- Check Tomcat logs in `logs/catalina.out`

---

## 📁 Important Files

| File | Purpose |
|------|---------|
| `sql/init.sql` | Database setup script |
| `src/db.properties` | Database credentials |
| `build.bat` | Build & compile script |
| `web/WEB-INF/web.xml` | Servlet configuration |
| `README.md` | Full documentation |

---

## 🎯 What This Project Teaches

✅ Java Servlet lifecycle  
✅ JDBC database operations  
✅ DAO design pattern  
✅ HTML form handling  
✅ WAR file deployment  
✅ Tomcat configuration  

---

## 🆘 Need Help?

1. Check `README.md` for detailed documentation
2. Review Tomcat logs: `logs/catalina.out`
3. Verify all steps completed in order
4. Ensure all prerequisites installed (Java 21, Tomcat 10.1, MySQL)

---

**Ready to deploy? Start with Step 1! 🚀**
