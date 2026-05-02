# Pakupos

JavaFX + Maven desktop application boilerplate with layered structure (view, service, DAO, database).

## Current status
- JavaFX app entrypoint is `com.aulkhami.mavenproject1.App`
- FXML views are under `src/main/resources/com/aulkhami/mavenproject1`
- Core boilerplate exists for:
  - config (`AppConfig`, `DatabaseConfig`, `Constants`)
  - utils (`AlertHelper`, `ValidationUtil`, `PasswordUtil`, `ConfigLoader`)
  - DAO (`IDAO`, `IUserDAO`, `UserDAO`)
  - models/entities (`User`, `Product`)
  - services (`UserService`, service interfaces)
  - sample views/components under `views`

## Project structure

```text
javafx-project/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── yourcompany/
│   │   │           └── app/
│   │   │               ├── Main.java
│   │   │               │
│   │   │               ├── controllers/
│   │   │               │   ├── BaseController.java
│   │   │               │   ├── MainController.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── DashboardController.java
│   │   │               │   └── SettingsController.java
│   │   │               │
│   │   │               ├── models/
│   │   │               │   ├── User.java
│   │   │               │   ├── Product.java
│   │   │               │   └── entities/
│   │   │               │       └── BaseEntity.java
│   │   │               │
│   │   │               ├── services/
│   │   │               │   ├── interfaces/
│   │   │               │   │   ├── IUserService.java
│   │   │               │   │   └── IProductService.java
│   │   │               │   ├── UserService.java
│   │   │               │   ├── ProductService.java
│   │   │               │   └── AuthService.java
│   │   │               │
│   │   │               ├── dao/
│   │   │               │   ├── interfaces/
│   │   │               │   │   ├── IUserDAO.java
│   │   │               │   │   └── IProductDAO.java
│   │   │               │   ├── UserDAO.java
│   │   │               │   ├── ProductDAO.java
│   │   │               │   └── BaseDAO.java
│   │   │               │
│   │   │               ├── database/
│   │   │               │   ├── DatabaseConnection.java
│   │   │               │   ├── MySQLConnection.java
│   │   │               │   ├── SupabaseConnection.java
│   │   │               │   ├── ConnectionPool.java
│   │   │               │   └── migrations/
│   │   │               │       ├── V1__Initial_Schema.sql
│   │   │               │       └── V2__Add_Users_Table.sql
│   │   │               │
│   │   │               ├── utils/
│   │   │               │   ├── ValidationUtil.java
│   │   │               │   ├── DateUtil.java
│   │   │               │   ├── PasswordUtil.java
│   │   │               │   ├── ConfigLoader.java
│   │   │               │   └── AlertHelper.java
│   │   │               │
│   │   │               ├── config/
│   │   │               │   ├── AppConfig.java
│   │   │               │   ├── DatabaseConfig.java
│   │   │               │   └── Constants.java
│   │   │               │
│   │   │               ├── views/
│   │   │               │   └── components/
│   │   │               │       ├── CustomButton.java
│   │   │               │       ├── CustomTextField.java
│   │   │               │       └── LoadingSpinner.java
│   │   │               │
│   │   │               ├── 
/
│   │   │               │   ├── DatabaseException.java
│   │   │               │   ├── ValidationException.java
│   │   │               │   └── AuthenticationException.java
│   │   │               │
│   │   │               └── enums/
│   │   │                   ├── UserRole.java
│   │   │                   └── Status.java
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── main.fxml
│   │       │   ├── login.fxml
│   │       │   ├── dashboard.fxml
│   │       │   └── settings.fxml
│   │       │
│   │       ├── css/
│   │       │   ├── main.css
│   │       │   ├── theme-light.css
│   │       │   ├── theme-dark.css
│   │       │   └── components.css
│   │       │
│   │       ├── images/
│   │       │   ├── logo.png
│   │       │   ├── icons/
│   │       │   │   ├── user.png
│   │       │   │   └── settings.png
│   │       │   └── backgrounds/
│   │       │
│   │       ├── fonts/
│   │       │   └── custom-font.ttf
│   │       │
│   │       └── config/
│   │           ├── application.properties
│   │           ├── database.properties
│   │           └── log4j2.xml
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── yourcompany/
│                   └── app/
│                       ├── services/
│                       │   └── UserServiceTest.java
│                       ├── dao/
│                       │   └── UserDAOTest.java
│                       └── utils/
│                           └── ValidationUtilTest.java
│
├── lib/
│   └── (external JAR files if not using Maven/Gradle)
│
├── docs/
│   ├── API_DOCUMENTATION.md
│   ├── DATABASE_SCHEMA.md
│   ├── USER_GUIDE.md
│   └── AGENTS.md
│
├── scripts/
│   ├── build.sh
│   ├── run.sh
│   └── deploy.sh
│
├── .gitignore
├── pom.xml (Maven) or build.gradle (Gradle)
├── README.md
└── LICENSE
```

## Requirements
- JDK 11+ (current `pom.xml` uses release 11)
- Maven installed and available in PATH

## Run locally

```bash
mvn clean javafx:run
```

Alternative:

```bash
mvn clean compile
```

## Database config
Set values in:
- `src/main/resources/config/application.properties`
- `src/main/resources/config/database.properties`

Active profile is controlled by:
- `active.database=mysql` or `active.database=supabase`

## Development workflow
1. Add/update entity in `models/entities`
2. Add DAO interface + implementation
3. Add service interface + implementation
4. Add controller + FXML view
5. Add test in `src/test/java`

## Notes
- This project currently has app scaffolding and basic feature wiring.
- You may need to add JDBC dependencies in `pom.xml` if you want database runtime connectivity (MySQL/Supabase drivers).

## Documentation
- Contributor/developer details: `DEVELOPMENT_GUIDE.md`
