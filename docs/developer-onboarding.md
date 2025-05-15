# Developer Onboarding Guide

## Introduction

Welcome to the Clashtools Backend project! This guide will help you get started with development on this project. It covers the project structure, development workflow, coding standards, and other important information for new developers.

## Project Overview

Clashtools Backend is a Spring Boot application that provides a REST API for managing game-related data for Clash of Clans. The application uses:

- Java 17
- Spring Boot 3.x
- Spring Security with JWT authentication
- PostgreSQL database
- Maven for build management
- Swagger/OpenAPI for API documentation

## Getting Started

### Setting Up Your Development Environment

1. Install the required tools:
   - JDK 17 or higher
   - Maven 3.8.x or higher
   - PostgreSQL 14.x or higher
   - Git
   - Your preferred IDE (IntelliJ IDEA recommended)

2. Clone the repository:
   ```bash
   git clone https://github.com/absmaa21/clashtools-backend.git
   cd clashtools-backend
   ```

3. Import the project into your IDE:
   - For IntelliJ IDEA: File > Open > Select the project directory

4. Set up the database following the instructions in the [Setup and Installation Guide](setup-installation.md).

5. Run the application from your IDE or using Maven:
   ```bash
   mvn spring-boot:run
   ```

### Project Structure

The project follows a standard Spring Boot application structure:

```
src/
├── main/
│   ├── java/
│   │   └── at/htlkaindorf/clashtoolsbackend/
│   │       ├── config/         # Configuration classes
│   │       ├── constants/      # Constants and enums
│   │       ├── controller/     # REST controllers
│   │       ├── dto/            # Data Transfer Objects
│   │       ├── exceptions/     # Custom exceptions
│   │       ├── init/           # Initialization code
│   │       ├── mapper/         # Object mappers
│   │       ├── pojos/          # Entity classes
│   │       ├── repositories/   # Spring Data repositories
│   │       └── service/        # Business logic services
│   └── resources/
│       ├── application.properties  # Application configuration
│       └── openapi.yaml           # API documentation
└── test/
    └── java/
        └── at/htlkaindorf/clashtoolsbackend/
            ├── controller/     # Controller tests
            ├── repositories/   # Repository tests
            ├── security/       # Security tests
            └── service/        # Service tests
```

## Development Workflow

### Branching Strategy

We follow a simplified Git Flow workflow:

- `main`: Production-ready code
- `develop`: Integration branch for features
- `feature/*`: Feature branches
- `bugfix/*`: Bug fix branches
- `release/*`: Release preparation branches

### Development Process

1. Create a new branch from `develop` for your feature or bug fix:
   ```bash
   git checkout develop
   git pull
   git checkout -b feature/your-feature-name
   ```

2. Implement your changes, following the coding standards.

3. Write tests for your changes.

4. Run the tests to ensure everything works:
   ```bash
   mvn test
   ```

5. Commit your changes with meaningful commit messages:
   ```bash
   git add .
   git commit -m "Add feature: your feature description"
   ```

6. Push your branch to the remote repository:
   ```bash
   git push -u origin feature/your-feature-name
   ```

7. Create a pull request to merge your changes into the `develop` branch.

### Code Review Process

All code changes must go through a code review process:

1. Create a pull request on GitHub.
2. Assign reviewers to your pull request.
3. Address any feedback from the reviewers.
4. Once approved, your changes will be merged into the target branch.

## Coding Standards

### Java Code Style

We follow the Google Java Style Guide with some modifications:

- Use 4 spaces for indentation, not tabs.
- Maximum line length is 120 characters.
- Use camelCase for variable and method names.
- Use PascalCase for class names.
- Use UPPER_SNAKE_CASE for constants.

### API Design Guidelines

When designing REST APIs:

1. Use nouns, not verbs, for resource names.
2. Use plural nouns for collection resources.
3. Use HTTP methods appropriately:
   - GET for retrieving resources
   - POST for creating resources
   - PUT for updating resources
   - DELETE for removing resources
4. Use appropriate HTTP status codes.
5. Version your APIs.
6. Document your APIs using OpenAPI/Swagger.

### Documentation Guidelines

- Document all public classes and methods with JavaDoc.
- Keep the OpenAPI specification up-to-date when making API changes.
- Update the relevant documentation when making significant changes.

## Testing

### Testing Strategy

We use a combination of unit tests, integration tests, and security tests:

- Unit tests: Test individual components in isolation.
- Integration tests: Test the interaction between components.
- Security tests: Test authentication and authorization.

### Running Tests

Run all tests:
```bash
mvn test
```

Run a specific test class:
```bash
mvn test -Dtest=YourTestClassName
```

## Debugging

### Logging

The application uses SLF4J with Logback for logging. Use appropriate log levels:

- ERROR: For errors that need immediate attention.
- WARN: For potential issues that don't prevent the application from working.
- INFO: For informational messages about application progress.
- DEBUG: For detailed information useful for debugging.
- TRACE: For very detailed debugging information.

Example:
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YourClass {
    private static final Logger logger = LoggerFactory.getLogger(YourClass.class);
    
    public void yourMethod() {
        logger.debug("Detailed information for debugging");
        logger.info("Informational message");
        logger.warn("Warning message");
        logger.error("Error message", exception);
    }
}
```

### Remote Debugging

To enable remote debugging, run the application with:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

Then connect your IDE to port 5005.

## Common Tasks

### Adding a New Entity

1. Create a new entity class in the `pojos` package.
2. Create a repository interface in the `repositories` package.
3. Create DTOs in the `dto` package.
4. Create a mapper in the `mapper` package.
5. Create a service in the `service` package.
6. Create a controller in the `controller` package.
7. Update the OpenAPI specification.
8. Write tests for the new components.

### Adding a New Endpoint

1. Identify the appropriate controller or create a new one.
2. Create or update DTOs as needed.
3. Implement the endpoint in the controller.
4. Update the service layer as needed.
5. Update the OpenAPI specification.
6. Write tests for the new endpoint.

## Troubleshooting

### Common Issues

1. **Database connection issues**:
   - Check that PostgreSQL is running.
   - Verify the database credentials in `application.properties`.

2. **Build failures**:
   - Check for compilation errors.
   - Ensure that all dependencies are available.

3. **Test failures**:
   - Check the test logs for details.
   - Ensure that the test environment is properly set up.

### Getting Help

If you encounter issues that you can't resolve:

1. Check the project documentation.
2. Ask for help from other team members.
3. Create an issue on GitHub if it's a bug or feature request.

## Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Maven Documentation](https://maven.apache.org/guides/index.html)
- [OpenAPI Specification](https://swagger.io/specification/)
