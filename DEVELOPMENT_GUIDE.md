# Pakupos Development Guide

This guide is for contributors working on the Pakupos JavaFX project.

## 1. Architecture
The project follows layered architecture:

- Presentation: JavaFX controllers + FXML views
- Service: business rules and orchestration
- DAO: persistence operations
- Database: JDBC connections + configuration

Main package:
- `com.aulkhami.mavenproject1`

## 2. Key paths

- Java source: `src/main/java`
- Resources: `src/main/resources`
- Tests: `src/test/java`
- Module config: `src/main/java/module-info.java`

Important packages:
- `config`: app/database constants and property readers
- `controllers`: base/controller logic
- `dao` and `dao.interfaces`: repository-like access layer
- `database`: connection management
- `models.entities`: domain models
- `services` and `services.interfaces`: business layer
- `views`: view controllers/components

## 3. Setup

Prerequisites:
- JDK 11+
- Maven

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

If Maven is missing on Windows, install it and add `mvn` to PATH.

## 4. Running the app

Run JavaFX app:

```bash
mvn clean javafx:run
```

Compile only:

```bash
mvn clean compile
```

Run tests:

```bash
mvn test
```

## 5. Configuration

Config files:
- `src/main/resources/config/application.properties`
- `src/main/resources/config/database.properties`

Config classes:
- `AppConfig`: app-level property access
- `DatabaseConfig`: DB profile and active DB values
- `Constants`: property keys and defaults

Current DB profile toggle:
- `active.database=mysql`
- `active.database=supabase`

## 6. Coding conventions

- Keep one class per file.
- Keep package names consistent under `com.aulkhami.mavenproject1`.
- Use prepared statements in DAO.
- Keep controllers light; move logic to services.
- Avoid hardcoding credentials in Java classes.

## 7. Feature implementation checklist

1. Define/update entity in `models/entities`
2. Add interface in `dao.interfaces`
3. Implement DAO in `dao`
4. Add interface in `services.interfaces`
5. Implement service in `services`
6. Add controller + FXML in resources
7. Add/update tests in `src/test/java`

## 8. Java Module notes

When adding packages that are used by FXML controllers, update `module-info.java`:
- add `opens <package> to javafx.fxml;`

If you use JDBC types, ensure:
- `requires java.sql;`

## 9. Troubleshooting

Issue: `package java.sql is not visible`
- Fix: add `requires java.sql;` in `module-info.java`

Issue: FXML controller cannot be created
- Fix: ensure package is opened to `javafx.fxml` in `module-info.java`

Issue: `mvn` not recognized
- Fix: install Maven and add binary folder to PATH

## 10. Contribution process

1. Create branch from `development`
2. Implement focused change
3. Verify compile/tests locally
4. Commit with clear message
5. Open PR against `development` or project target branch

## 11. Next recommended improvements

- Add JDBC dependencies for MySQL/PostgreSQL in `pom.xml`
- Add Product DAO and Product service implementation
- Add integration tests for DAO layer
- Add CI workflow for compile + test
