# Database Schema Documentation

## Overview
This document provides a comprehensive overview of the database schema used in the Clashtools Backend application. It describes the main entities, their attributes, and the relationships between them.

## Entity Relationship Diagram
The database schema consists of several interconnected entities that represent the core concepts of the application:

- **User**: Represents a user of the application
- **Role**: Represents a role that can be assigned to users
- **RefreshToken**: Represents a refresh token for JWT authentication
- **Account**: Represents a user's account in the game
- **BaseEntity**: Represents a base game object
- **BaseEntityName**: Represents the name of a base entity
- **BaseEntityLevel**: Represents a specific level of a base entity
- **Level**: Represents a level value
- **Attribute**: Represents a characteristic or property
- **AttributeName**: Represents the name of an attribute
- **AttributeTranslation**: Represents a translation of an attribute name
- **AttributeValue**: Represents a value of an attribute for a specific base entity level
- **Category**: Represents a category that can contain attribute names

## Entities and Relationships

### User
- **Attributes**:
  - `id` (Long): Primary key
  - `version` (Long): Version for optimistic locking
  - `username` (String): Unique username for login
  - `password` (String): Encrypted password
  - `mail` (String): Unique email address
- **Relationships**:
  - One-to-Many with `Account`: A user can have multiple accounts
  - Many-to-Many with `Role`: A user can have multiple roles
  - One-to-Many with `RefreshToken`: A user can have multiple refresh tokens

### Role
- **Attributes**:
  - `id` (Long): Primary key
  - `name` (String): Unique role name (e.g., "ROLE_USER", "ROLE_ADMIN")
- **Relationships**:
  - Many-to-Many with `User`: A role can be assigned to multiple users

### RefreshToken
- **Attributes**:
  - `id` (Long): Primary key
  - `token` (String): Unique token value
  - `expiryDate` (Instant): Expiry date of the token
- **Relationships**:
  - Many-to-One with `User`: A refresh token belongs to a user

### Account
- **Attributes**:
  - `id` (Long): Primary key
  - `accountName` (String): Unique account name
- **Relationships**:
  - Many-to-One with `User`: An account belongs to a user
  - One-to-Many with `BaseEntity`: An account can have multiple base entities

### BaseEntity
- **Attributes**:
  - `id` (Long): Primary key
- **Relationships**:
  - Many-to-One with `Account`: A base entity belongs to an account
  - Many-to-One with `BaseEntityName`: A base entity has a name
  - One-to-Many with `BaseEntityLevel`: A base entity can have multiple levels

### BaseEntityName
- **Attributes**:
  - `id` (Long): Primary key
  - `name` (String): Name of the base entity
- **Relationships**:
  - One-to-Many with `BaseEntity`: A base entity name can be used by multiple base entities

### BaseEntityLevel
- **Attributes**:
  - `id` (Long): Primary key
- **Relationships**:
  - Many-to-One with `BaseEntity`: A base entity level belongs to a base entity
  - Many-to-One with `Level`: A base entity level has a level value
  - Many-to-Many with `Attribute`: A base entity level can have multiple attributes

### Level
- **Attributes**:
  - `id` (Long): Primary key
  - `level` (int): Level value
- **Relationships**:
  - One-to-Many with `BaseEntityLevel`: A level value can be used by multiple base entity levels

### Attribute
- **Attributes**:
  - `id` (Long): Primary key
- **Relationships**:
  - Many-to-One with `AttributeName`: An attribute has a name
  - One-to-Many with `AttributeTranslation`: An attribute can have multiple translations
  - Many-to-Many with `BaseEntityLevel`: An attribute can be associated with multiple base entity levels

### AttributeName
- **Attributes**:
  - `id` (Long): Primary key
  - `name` (String): Unique attribute name
- **Relationships**:
  - One-to-Many with `Attribute`: An attribute name can be used by multiple attributes
  - Many-to-Many with `Category`: An attribute name can belong to multiple categories

### AttributeTranslation
- **Attributes**:
  - `id` (Long): Primary key
  - `languageCode` (String): Language code for the translation
  - `name` (String): Translated name
- **Relationships**:
  - Many-to-One with `Attribute`: A translation belongs to an attribute

### AttributeValue
- **Attributes**:
  - `id` (Long): Primary key
  - `valueType` (String): Type of the attribute value
  - `value` (Object): Value of the attribute
- **Relationships**:
  - Many-to-One with `Attribute`: An attribute value belongs to an attribute
  - Many-to-One with `BaseEntityLevel`: An attribute value belongs to a base entity level

### Category
- **Attributes**:
  - `id` (Long): Primary key
  - `name` (String): Category name
- **Relationships**:
  - Many-to-Many with `AttributeName`: A category can contain multiple attribute names

## Database Tables

The following tables are created in the database:

1. `app_user`: Stores user information
2. `role`: Stores role information
3. `user_roles`: Junction table for the many-to-many relationship between users and roles
4. `refresh_token`: Stores refresh token information
5. `account`: Stores account information
6. `base_entity`: Stores base entity information
7. `base_entity_name`: Stores base entity name information
8. `base_entity_level`: Stores base entity level information
9. `level`: Stores level information
10. `base_entity_level_attributes`: Junction table for the many-to-many relationship between base entity levels and attributes
11. `attribute`: Stores attribute information
12. `attribute_name`: Stores attribute name information
13. `attribute_translation`: Stores attribute translation information
14. `attribute_value`: Stores attribute value information
15. `category`: Stores category information
16. `category_attribute_names`: Junction table for the many-to-many relationship between categories and attribute names

## Indexes and Constraints

The database schema includes the following indexes and constraints:

- Primary key constraints on all tables
- Foreign key constraints for all relationships
- Unique constraints on `username` and `mail` in the `app_user` table
- Unique constraint on `name` in the `role` table
- Unique constraint on `token` in the `refresh_token` table
- Unique constraint on `accountName` in the `account` table
- Unique constraint on `name` in the `attribute_name` table

## Sequences

The database schema uses the following sequences for generating primary key values:

- `user_sequence`: For the `app_user` table
- `role_sequence`: For the `role` table
- `refresh_token_seq`: For the `refresh_token` table
- `account_seq`: For the `account` table
- `base_entity_seq`: For the `base_entity` table
- `base_entity_name_seq`: For the `base_entity_name` table
- `base_entity_level_seq`: For the `base_entity_level` table
- `level_sequence`: For the `level` table
- `attribute_sequence`: For the `attribute` table
- `attribute_name_sequence`: For the `attribute_name` table
- `attribute_translation_seq`: For the `attribute_translation` table
- `attribute_value_seq`: For the `attribute_value` table
- `category_seq`: For the `category` table
