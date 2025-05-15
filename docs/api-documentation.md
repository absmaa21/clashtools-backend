# API Documentation

## Overview

This document provides comprehensive documentation for the Clashtools Backend API. It includes detailed information about the available endpoints, request/response formats, authentication, and examples.

## Base URL

The base URL for all API endpoints is:

```
http://localhost:8080/api
```

For production environments, replace `localhost:8080` with your domain.

## Authentication

Most API endpoints require authentication. The API uses JWT (JSON Web Token) for authentication.

### Obtaining a Token

To obtain a JWT token, send a POST request to the `/api/auth/login` endpoint with your credentials:

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password"
}
```

Example response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Using the Token

Include the JWT token in the `Authorization` header of your requests:

```http
GET /api/accounts
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Refreshing the Token

When the JWT token expires, you can use the refresh token to obtain a new JWT token:

```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Example response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Logging Out

To log out and invalidate your tokens, send a POST request to the `/api/auth/logout` endpoint:

```http
POST /api/auth/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## API Endpoints

### Authentication

#### Register a New User

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "new_user",
  "email": "user@example.com",
  "password": "secure_password"
}
```

Example response:

```
User successfully registered
```

#### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password"
}
```

Example response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Refresh Token

```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Example response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Logout

```http
POST /api/auth/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```
Successfully logged out
```

### Accounts

#### Get All Accounts

```http
GET /api/accounts
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
[
  {
    "id": 1,
    "accountName": "My Clash Account",
    "userId": 1,
    "username": "john_doe",
    "baseEntityIds": [1, 2, 3]
  },
  {
    "id": 2,
    "accountName": "Secondary Account",
    "userId": 1,
    "username": "john_doe",
    "baseEntityIds": [4, 5]
  }
]
```

#### Get Account by ID

```http
GET /api/accounts/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
{
  "id": 1,
  "accountName": "My Clash Account",
  "userId": 1,
  "username": "john_doe",
  "baseEntityIds": [1, 2, 3]
}
```

#### Create Account

```http
POST /api/accounts
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "accountName": "New Clash Account",
  "userId": 1
}
```

Example response:

```json
{
  "id": 3,
  "accountName": "New Clash Account",
  "userId": 1,
  "username": "john_doe",
  "baseEntityIds": []
}
```

#### Update Account

```http
PUT /api/accounts/3
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "accountName": "Updated Clash Account",
  "userId": 1
}
```

Example response:

```json
{
  "id": 3,
  "accountName": "Updated Clash Account",
  "userId": 1,
  "username": "john_doe",
  "baseEntityIds": []
}
```

#### Delete Account

```http
DELETE /api/accounts/3
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```
204 No Content
```

#### Get Accounts by User ID

```http
GET /api/accounts/by-user/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
[
  {
    "id": 1,
    "accountName": "My Clash Account",
    "userId": 1,
    "username": "john_doe",
    "baseEntityIds": [1, 2, 3]
  },
  {
    "id": 2,
    "accountName": "Secondary Account",
    "userId": 1,
    "username": "john_doe",
    "baseEntityIds": [4, 5]
  }
]
```

### Base Entities

#### Get All Base Entities

```http
GET /api/base-entities
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
[
  {
    "id": 1,
    "name": "Town Hall",
    "level": 10
  },
  {
    "id": 2,
    "name": "Barracks",
    "level": 8
  }
]
```

#### Get Base Entity by ID

```http
GET /api/base-entities/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
{
  "id": 1,
  "name": "Town Hall",
  "level": 10
}
```

#### Create Base Entity

```http
POST /api/base-entities
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "name": "Archer Tower",
  "level": 12
}
```

Example response:

```json
{
  "id": 3,
  "name": "Archer Tower",
  "level": 12
}
```

#### Update Base Entity

```http
PUT /api/base-entities/3
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "name": "Archer Tower",
  "level": 13
}
```

Example response:

```json
{
  "id": 3,
  "name": "Archer Tower",
  "level": 13
}
```

#### Delete Base Entity

```http
DELETE /api/base-entities/3
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```
204 No Content
```

### Attributes

#### Get All Attributes

```http
GET /api/attributes
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
[
  {
    "id": 1,
    "attributeName": {
      "id": 1,
      "name": "damage_per_second"
    },
    "translations": [
      {
        "id": 1,
        "languageCode": "en",
        "name": "Damage per Second"
      },
      {
        "id": 2,
        "languageCode": "de",
        "name": "Schaden pro Sekunde"
      }
    ]
  },
  {
    "id": 2,
    "attributeName": {
      "id": 2,
      "name": "hit_points"
    },
    "translations": [
      {
        "id": 3,
        "languageCode": "en",
        "name": "Hit Points"
      },
      {
        "id": 4,
        "languageCode": "de",
        "name": "Trefferpunkte"
      }
    ]
  }
]
```

#### Get Attribute by ID

```http
GET /api/attributes/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
{
  "id": 1,
  "attributeName": {
    "id": 1,
    "name": "damage_per_second"
  },
  "translations": [
    {
      "id": 1,
      "languageCode": "en",
      "name": "Damage per Second"
    },
    {
      "id": 2,
      "languageCode": "de",
      "name": "Schaden pro Sekunde"
    }
  ]
}
```

#### Create Attribute

```http
POST /api/attributes
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "attributeNameId": 3,
  "translations": [
    {
      "languageCode": "en",
      "name": "Attack Range"
    },
    {
      "languageCode": "de",
      "name": "Angriffsreichweite"
    }
  ]
}
```

Example response:

```json
{
  "id": 3,
  "attributeName": {
    "id": 3,
    "name": "attack_range"
  },
  "translations": [
    {
      "id": 5,
      "languageCode": "en",
      "name": "Attack Range"
    },
    {
      "id": 6,
      "languageCode": "de",
      "name": "Angriffsreichweite"
    }
  ]
}
```

#### Update Attribute

```http
PUT /api/attributes/3
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "attributeNameId": 3,
  "translations": [
    {
      "languageCode": "en",
      "name": "Attack Range"
    },
    {
      "languageCode": "de",
      "name": "Angriffsreichweite"
    },
    {
      "languageCode": "fr",
      "name": "Portée d'attaque"
    }
  ]
}
```

Example response:

```json
{
  "id": 3,
  "attributeName": {
    "id": 3,
    "name": "attack_range"
  },
  "translations": [
    {
      "id": 5,
      "languageCode": "en",
      "name": "Attack Range"
    },
    {
      "id": 6,
      "languageCode": "de",
      "name": "Angriffsreichweite"
    },
    {
      "id": 7,
      "languageCode": "fr",
      "name": "Portée d'attaque"
    }
  ]
}
```

#### Delete Attribute

```http
DELETE /api/attributes/3
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```
204 No Content
```

#### Get Attributes by Attribute Name ID

```http
GET /api/attributes/by-attribute-name/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Example response:

```json
[
  {
    "id": 1,
    "attributeName": {
      "id": 1,
      "name": "damage_per_second"
    },
    "translations": [
      {
        "id": 1,
        "languageCode": "en",
        "name": "Damage per Second"
      },
      {
        "id": 2,
        "languageCode": "de",
        "name": "Schaden pro Sekunde"
      }
    ]
  }
]
```

## Error Handling

The API returns appropriate HTTP status codes and error messages for different types of errors:

### 400 Bad Request

Returned when the request is invalid, such as missing required fields or invalid data.

Example:

```json
{
  "timestamp": "2023-07-15T12:34:56.789Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request data",
  "path": "/api/accounts"
}
```

### 401 Unauthorized

Returned when authentication fails, such as invalid credentials or expired token.

Example:

```json
{
  "timestamp": "2023-07-15T12:34:56.789Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid credentials",
  "path": "/api/auth/login"
}
```

### 404 Not Found

Returned when the requested resource is not found.

Example:

```json
{
  "timestamp": "2023-07-15T12:34:56.789Z",
  "status": 404,
  "error": "Not Found",
  "message": "Account not found",
  "path": "/api/accounts/999"
}
```

### 500 Internal Server Error

Returned when an unexpected error occurs on the server.

Example:

```json
{
  "timestamp": "2023-07-15T12:34:56.789Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/accounts"
}
```

## Rate Limiting

The API implements rate limiting to prevent abuse. If you exceed the rate limit, you will receive a 429 Too Many Requests response:

```json
{
  "timestamp": "2023-07-15T12:34:56.789Z",
  "status": 429,
  "error": "Too Many Requests",
  "message": "Rate limit exceeded. Try again in 60 seconds.",
  "path": "/api/auth/login"
}
```

## API Versioning

The API uses URL versioning. The current version is v1, which is implicit in the base URL. Future versions will be explicitly versioned, such as `/api/v2/...`.

## Additional Resources

- [Swagger UI](http://localhost:8080/swagger-ui.html): Interactive API documentation
- [OpenAPI Specification](http://localhost:8080/openapi.yaml): OpenAPI specification file
