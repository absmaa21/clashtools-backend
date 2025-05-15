# Setup and Installation Guide

## Prerequisites

Before you begin, ensure you have the following installed:

- Java Development Kit (JDK) 17 or higher
- Maven 3.8.x or higher
- PostgreSQL 14.x or higher
- Git

## Getting the Source Code

Clone the repository from GitHub:

```bash
git clone https://github.com/absmaa21/clashtools-backend.git
cd clashtools-backend
```

## Database Setup

1. Create a PostgreSQL database for the application:

```bash
psql -U postgres
CREATE DATABASE clashtools;
CREATE USER clashtoolsuser WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE clashtools TO clashtoolsuser;
\q
```

2. Update the database configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clashtools
spring.datasource.username=clashtoolsuser
spring.datasource.password=your_password
```

## Application Configuration

1. Configure JWT Secret Key:

   For development, you can use the default configuration. For production, it's recommended to set the JWT secret key as an environment variable:

   ```bash
   export JWT_SECRET=your_secure_jwt_secret_key
   ```

   And update the application.properties file:

   ```properties
   jwt.secret=${JWT_SECRET}
   ```

2. Configure CORS settings:

   Update the CORS configuration in `src/main/java/at/htlkaindorf/clashtoolsbackend/config/WebConfig.java` to specify allowed origins instead of using the wildcard "*".

## Building the Application

Build the application using Maven:

```bash
mvn clean install
```

This will compile the code, run tests, and package the application into a JAR file.

## Running the Application

### Development Mode

For development, you can run the application using Maven:

```bash
mvn spring-boot:run
```

### Production Mode

For production, you can run the JAR file directly:

```bash
java -jar target/clashtoolsbackend-0.0.1-SNAPSHOT.jar
```

You can also specify configuration properties when running the JAR:

```bash
java -Dspring.profiles.active=prod -Djwt.secret=your_secure_jwt_secret_key -jar target/clashtoolsbackend-0.0.1-SNAPSHOT.jar
```

## Verifying the Installation

1. The application should be running on http://localhost:8080
2. Access the Swagger UI at http://localhost:8080/swagger-ui.html to explore the API

## Troubleshooting

### Database Connection Issues

If you encounter database connection issues:

1. Verify that PostgreSQL is running:
   ```bash
   sudo service postgresql status
   ```

2. Check that the database credentials in `application.properties` match your PostgreSQL setup.

3. Ensure that the database exists and the user has appropriate permissions:
   ```bash
   psql -U postgres -c "\l" | grep clashtools
   ```

### Application Startup Issues

If the application fails to start:

1. Check the logs for error messages.

2. Verify that the required ports (default: 8080) are not in use:
   ```bash
   netstat -tulpn | grep 8080
   ```

3. Ensure that the JDK version is compatible:
   ```bash
   java -version
   ```

## Next Steps

After installation, you might want to:

1. Create an admin user
2. Populate the database with initial data
3. Configure the application for production use

See the [Developer Onboarding Guide](developer-onboarding.md) for more information on getting started with development.
