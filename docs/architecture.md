# Architecture Documentation

## Overview

This document provides an overview of the architecture of the Clashtools Backend application. It describes the high-level components, their interactions, and the design patterns used in the application.

## System Architecture

The Clashtools Backend is built using a layered architecture pattern, which separates the application into distinct layers, each with a specific responsibility. This promotes separation of concerns, maintainability, and testability.

```
┌─────────────────────────────────────────────────────────────┐
│                      Client Applications                     │
└───────────────────────────────┬─────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                         API Layer                            │
│                                                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐  │
│  │  Controllers    │  │  DTOs           │  │  Mappers    │  │
│  └─────────────────┘  └─────────────────┘  └─────────────┘  │
└───────────────────────────────┬─────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                      Service Layer                           │
│                                                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐  │
│  │  Services       │  │  Validation     │  │  Security   │  │
│  └─────────────────┘  └─────────────────┘  └─────────────┘  │
└───────────────────────────────┬─────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    Repository Layer                          │
│                                                             │
│  ┌─────────────────┐  ┌─────────────────┐                   │
│  │  Repositories   │  │  Entities       │                   │
│  └─────────────────┘  └─────────────────┘                   │
└───────────────────────────────┬─────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                      Database                                │
└─────────────────────────────────────────────────────────────┘
```

### Layers

1. **API Layer**: Handles HTTP requests and responses. It includes controllers, DTOs (Data Transfer Objects), and mappers.
2. **Service Layer**: Contains the business logic of the application. It includes services, validation, and security.
3. **Repository Layer**: Manages data access and persistence. It includes repositories and entities.
4. **Database**: Stores the application data.

## Component Architecture

### Controllers

Controllers handle HTTP requests and delegate processing to the service layer. They are responsible for:
- Receiving HTTP requests
- Validating request data
- Delegating to the service layer
- Returning appropriate HTTP responses

Key controllers include:
- `AuthController`: Handles authentication operations
- `AccountController`: Manages account-related operations
- `BaseEntityController`: Manages base entity operations
- `AttributeController`: Manages attribute operations
- `LevelController`: Manages level operations

### DTOs (Data Transfer Objects)

DTOs are used to transfer data between the API layer and the service layer. They are designed to:
- Decouple the API contract from the internal domain model
- Provide a clear API contract
- Validate input data

Key DTO packages include:
- `dto.account`: Account-related DTOs
- `dto.attribute`: Attribute-related DTOs
- `dto.auth`: Authentication-related DTOs
- `dto.baseentity`: Base entity-related DTOs
- `dto.level`: Level-related DTOs
- `dto.user`: User-related DTOs

### Mappers

Mappers convert between entities and DTOs. They are responsible for:
- Converting entities to DTOs for API responses
- Converting DTOs to entities for persistence

Key mappers include:
- `UserMapper`: Maps between User entities and DTOs
- `AccountMapper`: Maps between Account entities and DTOs
- `BaseEntityMapper`: Maps between BaseEntity entities and DTOs
- `AttributeMapper`: Maps between Attribute entities and DTOs

### Services

Services contain the business logic of the application. They are responsible for:
- Implementing business rules
- Coordinating between repositories
- Managing transactions
- Handling security concerns

Key services include:
- `AuthService`: Handles authentication and authorization
- `AccountService`: Manages account-related operations
- `BaseEntityService`: Manages base entity operations
- `AttributeService`: Manages attribute operations
- `LevelService`: Manages level operations

### Repositories

Repositories provide an abstraction over data access. They are responsible for:
- Querying the database
- Persisting entities
- Managing relationships between entities

Key repositories include:
- `UserRepository`: Manages User entities
- `AccountRepository`: Manages Account entities
- `BaseEntityRepository`: Manages BaseEntity entities
- `AttributeRepository`: Manages Attribute entities
- `LevelRepository`: Manages Level entities

### Entities

Entities represent the domain model of the application. They are responsible for:
- Defining the structure of the data
- Defining relationships between data
- Providing a representation of the business domain

Key entities include:
- `User`: Represents a user of the application
- `Account`: Represents a user's account
- `BaseEntity`: Represents a base game object
- `Attribute`: Represents a characteristic or property
- `Level`: Represents a level value

## Authentication and Authorization

The application uses JWT (JSON Web Token) for authentication and authorization:

```
┌─────────────┐                                  ┌─────────────┐
│             │                                  │             │
│   Client    │                                  │   Server    │
│             │                                  │             │
└──────┬──────┘                                  └──────┬──────┘
       │                                                │
       │  1. Login Request (username, password)         │
       │ ─────────────────────────────────────────────► │
       │                                                │
       │  2. Validate Credentials                       │
       │                                                │
       │  3. Generate JWT and Refresh Token             │
       │                                                │
       │  4. Return Tokens                              │
       │ ◄───────────────────────────────────────────── │
       │                                                │
       │  5. Request with JWT in Authorization Header   │
       │ ─────────────────────────────────────────────► │
       │                                                │
       │  6. Validate JWT                               │
       │                                                │
       │  7. Process Request                            │
       │                                                │
       │  8. Return Response                            │
       │ ◄───────────────────────────────────────────── │
       │                                                │
       │  9. When JWT Expires, Use Refresh Token        │
       │ ─────────────────────────────────────────────► │
       │                                                │
       │  10. Validate Refresh Token                    │
       │                                                │
       │  11. Generate New JWT and Refresh Token        │
       │                                                │
       │  12. Return New Tokens                         │
       │ ◄───────────────────────────────────────────── │
       │                                                │
└───────────────────────────────────────────────────────┘
```

## Data Flow

The typical data flow in the application is as follows:

1. The client sends an HTTP request to a controller.
2. The controller validates the request and converts it to a DTO.
3. The controller delegates to a service.
4. The service implements the business logic, using repositories to access data.
5. The repositories interact with the database to retrieve or persist data.
6. The service returns the result to the controller.
7. The controller converts the result to a response DTO and returns it to the client.

## Design Patterns

The application uses several design patterns:

1. **Repository Pattern**: Abstracts the data access layer, making it easier to change the underlying data store.
2. **DTO Pattern**: Separates the API contract from the internal domain model.
3. **Mapper Pattern**: Provides a clean way to convert between entities and DTOs.
4. **Service Layer Pattern**: Encapsulates business logic in a separate layer.
5. **Dependency Injection**: Uses Spring's dependency injection to manage component dependencies.
6. **Builder Pattern**: Used in entity and DTO classes to create complex objects.

## Technology Stack

The application is built using the following technologies:

- **Java 17**: The programming language
- **Spring Boot 3.x**: The application framework
- **Spring Security**: For authentication and authorization
- **Spring Data JPA**: For data access
- **Hibernate**: ORM for database operations
- **PostgreSQL**: The database
- **Maven**: For build management
- **Swagger/OpenAPI**: For API documentation
- **JUnit and Mockito**: For testing

## Deployment Architecture

The application can be deployed in various environments:

```
┌─────────────────────────────────────────────────────────────┐
│                     Production Environment                   │
│                                                             │
│  ┌─────────────┐   ┌─────────────┐   ┌─────────────────┐    │
│  │  Load       │   │  Clashtools │   │  PostgreSQL     │    │
│  │  Balancer   ├──►│  Backend    ├──►│  Database       │    │
│  └─────────────┘   └─────────────┘   └─────────────────┘    │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                     Testing Environment                      │
│                                                             │
│  ┌─────────────┐   ┌─────────────────┐                      │
│  │  Clashtools │   │  PostgreSQL     │                      │
│  │  Backend    ├──►│  Database       │                      │
│  └─────────────┘   └─────────────────┘                      │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    Development Environment                   │
│                                                             │
│  ┌─────────────┐   ┌─────────────────┐                      │
│  │  Clashtools │   │  PostgreSQL     │                      │
│  │  Backend    ├──►│  Database       │                      │
│  └─────────────┘   └─────────────────┘                      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## Future Architecture Considerations

As the application evolves, the following architectural improvements could be considered:

1. **Microservices Architecture**: Split the application into smaller, focused microservices.
2. **Event-Driven Architecture**: Use events for communication between components.
3. **Caching**: Implement caching for frequently accessed data.
4. **API Gateway**: Add an API gateway for routing, authentication, and rate limiting.
5. **Containerization**: Use Docker and Kubernetes for containerization and orchestration.
6. **Monitoring and Observability**: Implement comprehensive monitoring and observability.

## Conclusion

The Clashtools Backend application follows a layered architecture pattern, with clear separation of concerns between the API, service, and repository layers. It uses modern design patterns and technologies to provide a robust, maintainable, and scalable solution.
