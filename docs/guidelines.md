# ClashTools Backend Development Guidelines

This document provides essential information for developers working on the ClashTools Backend project. It includes build/configuration instructions, testing guidelines, and development best practices.

## Build and Configuration Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

### Database Setup

1. Create a PostgreSQL database named `ClashTools`
2. Configure the database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ClashTools
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Build Commands

- **Clean and build the project**:
  ```bash
  mvn clean install
  ```

- **Run the application**:
  ```bash
  mvn spring-boot:run
  ```

- **Build without running tests**:
  ```bash
  mvn clean install -DskipTests
  ```

### Configuration Properties

The application uses the following key configuration properties in `application.properties`:

- `spring.jpa.hibernate.ddl-auto`: Controls database schema generation
    - Use `create` for development (recreates schema on startup)
    - Use `update` for testing (updates schema without data loss)
    - Use `validate` for production (validates schema without changes)

- `logging.level.org.hibernate.SQL`: Controls SQL query logging
    - Set to `DEBUG` for development
    - Set to `INFO` or `WARN` for production

- `springdoc.swagger-ui.path`: Path to access Swagger UI (default: `/swagger-ui.html`)

## Testing Information

### Test Structure

- Unit tests should be placed in the `src/test/java` directory
- Test classes should follow the naming convention `*Test.java`
- Use appropriate test annotations:
    - `@Test`: Marks a method as a test
    - `@BeforeEach`: Runs before each test method
    - `@AfterEach`: Runs after each test method
    - `@BeforeAll`: Runs once before all test methods
    - `@AfterAll`: Runs once after all test methods

### Running Tests

- **Run all tests**:
  ```bash
  mvn test
  ```

- **Run a specific test class**:
  ```bash
  mvn test -Dtest=JwtServiceTest
  ```

- **Run a specific test method**:
  ```bash
  mvn test -Dtest=JwtServiceTest#testGenerateAndValidateToken
  ```

### Writing Tests

#### Unit Tests

For simple unit tests without Spring context, create a test class like this:

```java
public class JwtServiceTest {
    private JwtService jwtService;
    
    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Initialize test data
    }
    
    @Test
    void testSomeFeature() {
        // Test implementation
        assertEquals(expected, actual);
    }
}
```

#### Integration Tests

For tests requiring Spring context, use the `@SpringBootTest` annotation:

```java
@SpringBootTest
public class AuthControllerTest {
    @Autowired
    private AuthController authController;
    
    @Test
    void testAuthentication() {
        // Test implementation
    }
}
```

#### Controller Tests

For testing REST controllers, use `@WebMvcTest` and `MockMvc`:

```java
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AuthService authService;
    
    @Test
    void testLogin() throws Exception {
        // Test implementation using mockMvc
    }
}
```

### Test Example

Here's a simple test for the JwtService that verifies token generation and validation:

```java
public class JwtServiceTest {
    private JwtService jwtService;
    private User testUser;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        
        Role userRole = Role.builder()
                .name("ROLE_USER")
                .build();

        testUser = User.builder()
                .username("testuser")
                .password("password")
                .mail("test@example.com")
                .roles(Set.of(userRole))
                .build();
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtService.generateToken(testUser);
        
        assertNotNull(token);
        assertTrue(jwtService.validateToken(token));
        assertEquals(testUser.getUsername(), jwtService.extractUsername(token));
    }
}
```

## Development Best Practices

### Code Style

- Follow Java naming conventions:
    - Classes: PascalCase (e.g., `JwtService`)
    - Methods and variables: camelCase (e.g., `generateToken`)
    - Constants: UPPER_SNAKE_CASE (e.g., `EXPIRATION_TIME`)
- Use meaningful names for classes, methods, and variables
- Keep methods short and focused on a single responsibility
- Add JavaDoc comments for all public methods and classes

### Architecture Guidelines

- Follow the layered architecture pattern:
    - Controllers: Handle HTTP requests and responses
    - Services: Implement business logic
    - Repositories: Handle data access
    - DTOs: Transfer data between layers
    - POJOs/Entities: Represent domain objects
- Use dependency injection to manage component dependencies
- Keep controllers thin and move business logic to services
- Use DTOs to transfer data between layers instead of exposing entities directly

### Security Best Practices

- Store sensitive configuration (database credentials, JWT secret) in environment variables
- Use proper CORS configuration with specific allowed origins
- Implement rate limiting for authentication endpoints
- Use generic error messages for authentication failures
- Implement token expiration and refresh mechanisms
- Validate all input data to prevent injection attacks
- Use HTTPS in production environments

### Database Best Practices

- Use JPA entities with proper relationships
- Define appropriate fetch types (LAZY vs EAGER)
- Use database indexes for frequently queried fields
- Implement pagination for endpoints returning large collections
- Use transactions for operations that modify multiple entities
- Set appropriate cascade types for entity relationships

### API Design

- Follow RESTful API design principles
- Use appropriate HTTP methods (GET, POST, PUT, DELETE)
- Return appropriate HTTP status codes
- Implement proper error handling and return meaningful error messages
- Document APIs using Swagger/OpenAPI
- Version APIs to maintain backward compatibility
