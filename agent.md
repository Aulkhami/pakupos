# AGENTS.md - JavaFX Development Guide

## Project Overview
This is a JavaFX application using XAMPP/MySQL or Supabase as the database backend, following MVC architecture with clean separation of concerns.

---

## 🏗️ Architecture Patterns

### MVC (Model-View-Controller)
- **Model**: Data classes in `models/` package
- **View**: FXML files in `resources/fxml/`
- **Controller**: Controller classes in `controllers/` package

### Layered Architecture
```
┌─────────────────────┐
│   Presentation      │ ← Controllers + FXML
├─────────────────────┤
│   Business Logic    │ ← Services
├─────────────────────┤
│   Data Access       │ ← DAO
├─────────────────────┤
│   Database          │ ← MySQL/Supabase
└─────────────────────┘
```

---

## 📦 Core Components

### 1. Main Application Entry Point

**File**: `Main.java`

```java
package com.yourcompany.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
            getClass().getResource("/fxml/main.fxml")
        );
        
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(
            getClass().getResource("/css/main.css").toExternalForm()
        );
        
        primaryStage.setTitle("Your Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

### 2. Base Controller

**File**: `controllers/BaseController.java`

```java
package com.yourcompany.app.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController {
    
    protected void switchScene(String fxmlPath, Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    protected void showAlert(String title, String message) {
        // Use AlertHelper utility
    }
    
    // Common initialization logic
    public abstract void initialize();
}
```

### 3. Database Connection (MySQL/XAMPP)

**File**: `database/MySQLConnection.java`

```java
package com.yourcompany.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL Driver not found", e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### 4. Database Connection (Supabase)

**File**: `database/SupabaseConnection.java`

```java
package com.yourcompany.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupabaseConnection {
    
    private static final String URL = "jdbc:postgresql://db.your-project.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your-password";
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("PostgreSQL Driver not found", e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### 5. Base DAO Interface

**File**: `dao/interfaces/IDAO.java`

```java
package com.yourcompany.app.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface IDAO<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    void update(T entity);
    void delete(ID id);
}
```

### 6. User DAO Implementation

**File**: `dao/UserDAO.java`

```java
package com.yourcompany.app.dao;

import com.yourcompany.app.dao.interfaces.IUserDAO;
import com.yourcompany.app.database.MySQLConnection;
import com.yourcompany.app.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IUserDAO {
    
    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = MySQLConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
    
    @Override
    public void update(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}
```

### 7. User Model

**File**: `models/User.java`

```java
package com.yourcompany.app.models;

import java.sql.Timestamp;

public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;
    
    // Constructors
    public User() {}
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

### 8. User Service

**File**: `services/UserService.java`

```java
package com.yourcompany.app.services;

import com.yourcompany.app.dao.UserDAO;
import com.yourcompany.app.models.User;
import com.yourcompany.app.utils.PasswordUtil;

import java.util.List;
import java.util.Optional;

public class UserService {
    
    private final UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    public User registerUser(String username, String email, String password) {
        // Hash password before saving
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        User user = new User(username, email, hashedPassword);
        return userDAO.save(user);
    }
    
    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtil.verifyPassword(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }
    
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
    
    public Optional<User> getUserById(Integer id) {
        return userDAO.findById(id);
    }
    
    public void updateUser(User user) {
        userDAO.update(user);
    }
    
    public void deleteUser(Integer id) {
        userDAO.delete(id);
    }
}
```

### 9. Login Controller

**File**: `controllers/LoginController.java`

```java
package com.yourcompany.app.controllers;

import com.yourcompany.app.models.User;
import com.yourcompany.app.services.UserService;
import com.yourcompany.app.utils.AlertHelper;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class LoginController extends BaseController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    private UserService userService;
    
    @Override
    public void initialize() {
        userService = new UserService();
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            AlertHelper.showError("Login Failed", "Please enter both username and password");
            return;
        }
        
        Optional<User> userOpt = userService.loginUser(username, password);
        
        if (userOpt.isPresent()) {
            AlertHelper.showSuccess("Login Successful", "Welcome " + username);
            
            // Switch to dashboard
            try {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                switchScene("/fxml/dashboard.fxml", stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AlertHelper.showError("Login Failed", "Invalid username or password");
        }
    }
    
    @FXML
    private void handleRegister() {
        // Switch to registration screen
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            switchScene("/fxml/register.fxml", stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 10. Utility Classes

#### Password Utility

**File**: `utils/PasswordUtil.java`

```java
package com.yourcompany.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public static boolean verifyPassword(String inputPassword, String storedHash) {
        String inputHash = hashPassword(inputPassword);
        return inputHash.equals(storedHash);
    }
    
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
```

#### Alert Helper

**File**: `utils/AlertHelper.java`

```java
package com.yourcompany.app.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHelper {
    
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
```

#### Validation Utility

**File**: `utils/ValidationUtil.java`

```java
package com.yourcompany.app.utils;

import java.util.regex.Pattern;

public class ValidationUtil {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern USERNAME_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }
    
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
    
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
```

### 11. Configuration Files

#### application.properties

**File**: `resources/config/application.properties`

```properties
# Application Settings
app.name=JavaFX Application
app.version=1.0.0

# Database Configuration (MySQL/XAMPP)
mysql.url=jdbc:mysql://localhost:3306/your_database
mysql.username=root
mysql.password=
mysql.driver=com.mysql.cj.jdbc.Driver

# Supabase Configuration
supabase.url=jdbc:postgresql://db.your-project.supabase.co:5432/postgres
supabase.username=postgres
supabase.password=your-password
supabase.driver=org.postgresql.Driver

# Active Database Profile (mysql or supabase)
active.database=mysql

# Connection Pool
connection.pool.size=10
connection.timeout=30000
```

#### Config Loader

**File**: `utils/ConfigLoader.java`

```java
package com.yourcompany.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    
    private static Properties properties;
    
    static {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class
                .getResourceAsStream("/config/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
```

---

## 📝 FXML Examples

### Login Screen

**File**: `resources/fxml/login.fxml`

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.text.Font?>

<VBox alignment="CENTER" spacing="20.0" xmlns="http://javafx.com/javafx/17" 
      xmlns:fx="http://javafx.com/fxml/1" 
      fx:controller="com.yourcompany.app.controllers.LoginController"
      styleClass="root">
    
    <padding>
        <Insets top="50" right="50" bottom="50" left="50"/>
    </padding>
    
    <Label text="Login" styleClass="title">
        <font>
            <Font name="System Bold" size="36.0"/>
        </font>
    </Label>
    
    <TextField fx:id="usernameField" promptText="Username" styleClass="text-field"/>
    
    <PasswordField fx:id="passwordField" promptText="Password" styleClass="text-field"/>
    
    <HBox spacing="10.0" alignment="CENTER">
        <Button text="Login" onAction="#handleLogin" styleClass="primary-button"/>
        <Button text="Register" onAction="#handleRegister" styleClass="secondary-button"/>
    </HBox>
    
</VBox>
```

---

## 🎨 CSS Styling

**File**: `resources/css/main.css`

```css
.root {
    -fx-background-color: #f5f5f5;
    -fx-font-family: "Segoe UI", Arial, sans-serif;
}

.title {
    -fx-text-fill: #2c3e50;
    -fx-font-weight: bold;
}

.text-field {
    -fx-pref-width: 300px;
    -fx-pref-height: 40px;
    -fx-font-size: 14px;
    -fx-background-radius: 5px;
    -fx-border-radius: 5px;
}

.primary-button {
    -fx-background-color: #3498db;
    -fx-text-fill: white;
    -fx-pref-width: 120px;
    -fx-pref-height: 40px;
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-background-radius: 5px;
    -fx-cursor: hand;
}

.primary-button:hover {
    -fx-background-color: #2980b9;
}

.secondary-button {
    -fx-background-color: #95a5a6;
    -fx-text-fill: white;
    -fx-pref-width: 120px;
    -fx-pref-height: 40px;
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-background-radius: 5px;
    -fx-cursor: hand;
}

.secondary-button:hover {
    -fx-background-color: #7f8c8d;
}
```

---

## 🗄️ Database Setup

### MySQL/XAMPP Schema

```sql
CREATE DATABASE IF NOT EXISTS your_database;
USE your_database;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Supabase Schema (PostgreSQL)

```sql
-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    stock INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Enable Row Level Security (RLS)
ALTER TABLE users ENABLE ROW LEVEL SECURITY;
ALTER TABLE products ENABLE ROW LEVEL SECURITY;
```

---

## 🔧 Maven Configuration (pom.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.yourcompany</groupId>
    <artifactId>javafx-app</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <javafx.version>17.0.2</javafx.version>
    </properties>

    <dependencies>
        <!-- JavaFX -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>

        <!-- PostgreSQL (for Supabase) -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version>
        </dependency>

        <!-- Apache Commons -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-dbcp2</artifactId>
            <version>2.9.0</version>
        </dependency>

        <!-- Logging -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.20.0</version>
        </dependency>

        <!-- JUnit for Testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.9.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>com.yourcompany.app.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 🚀 Running the Application

### Using Maven

```bash
# Compile
mvn clean compile

# Run
mvn javafx:run

# Package
mvn package
```

### Using IDE
1. Import as Maven project
2. Run `Main.java`

---

## 📋 Best Practices

### 1. **Separation of Concerns**
- Keep UI logic in controllers
- Business logic in services
- Database operations in DAOs

### 2. **Error Handling**
- Use try-catch blocks in DAOs
- Create custom exceptions
- Show user-friendly error messages

### 3. **Security**
- Never store plain-text passwords
- Use prepared statements to prevent SQL injection
- Validate all user inputs

### 4. **Code Organization**
- One class per file
- Follow naming conventions
- Use meaningful variable names

### 5. **Testing**
- Write unit tests for services
- Test DAO methods with test database
- Use JUnit 5 for testing

### 6. **Configuration Management**
- Use properties files for configuration
- Don't hardcode credentials
- Support multiple environments (dev, prod)

---

## 🔐 Security Checklist

- [ ] Hash all passwords before storing
- [ ] Use prepared statements for SQL queries
- [ ] Validate and sanitize all user inputs
- [ ] Implement proper session management
- [ ] Use HTTPS for Supabase connections
- [ ] Implement role-based access control
- [ ] Log security events
- [ ] Handle exceptions gracefully

---

## 📚 Additional Resources

### JavaFX Documentation
- [JavaFX Official Docs](https://openjfx.io/)
- [JavaFX CSS Reference](https://openjfx.io/javadoc/17/javafx.graphics/javafx/scene/doc-files/cssref.html)

### Database Resources
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Supabase Documentation](https://supabase.com/docs)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)

### Design Patterns
- MVC Pattern
- DAO Pattern
- Singleton Pattern
- Factory Pattern

---

## 🐛 Troubleshooting

### Common Issues

**Issue**: JavaFX runtime components are missing
**Solution**: Add JavaFX SDK to module path

**Issue**: Database connection fails
**Solution**: Check XAMPP is running, verify credentials

**Issue**: FXML not loading
**Solution**: Verify file path starts with `/fxml/`

**Issue**: Supabase connection timeout
**Solution**: Check internet connection and Supabase project status

---

## 📝 Development Workflow

1. **Design Database Schema** → Create tables in MySQL/Supabase
2. **Create Models** → Java classes matching database tables
3. **Build DAOs** → Database access layer
4. **Implement Services** → Business logic layer
5. **Design FXML** → UI layouts
6. **Create Controllers** → Connect UI to logic
7. **Style with CSS** → Apply themes
8. **Test** → Write and run tests
9. **Deploy** → Package and distribute

---

## 🎯 Quick Start Checklist

- [ ] Install JDK 17+
- [ ] Install JavaFX SDK
- [ ] Setup XAMPP or Supabase account
- [ ] Create database and tables
- [ ] Clone project structure
- [ ] Configure database credentials
- [ ] Build with Maven
- [ ] Run Main.java
- [ ] Test login functionality

---

**Remember**: This is a living document. Update it as your project evolves!