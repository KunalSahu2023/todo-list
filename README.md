# 📝 Full-Stack To-Do List Application

A secure and scalable To-Do List REST API built using **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, and PostgreSQL**.

The application provides complete Todo CRUD operations with **JWT-based authentication, Role-Based Access Control (RBAC), user-specific Todo ownership, validation, exception handling, pagination, searching, and filtering**.

## 🚀 Features

### 🔐 Authentication & Authorization

- User registration
- User login
- JWT-based authentication
- Secure password hashing using BCrypt
- Role-Based Access Control (RBAC)
- `USER` and `ADMIN` roles
- Protected API endpoints
- Admin-only endpoints
- User-specific Todo access

### 📝 Todo Management

- Create Todo
- Get all Todos
- Get Todo by ID
- Update Todo
- Delete Todo
- Mark Todo as completed/uncompleted
- Todo ownership based on authenticated user

### 🔎 Search, Filter & Pagination

- Pagination support
- Filter Todos by completion status
- Search Todos by title
- User-specific Todo listing

### 🛡️ Validation & Exception Handling

- Request validation using Jakarta Bean Validation
- `@NotBlank`
- `@Size`
- Global exception handling
- Standardized error responses
- `404 Not Found` for unavailable resources
- `400 Bad Request` for validation errors

### 🗄️ Database

- PostgreSQL support for persistent storage
- H2 database support for local development/testing
- Spring Data JPA
- Hibernate ORM
- User-to-Todo relationship

# 🛠️ Tech Stack

## Backend

- Java 17+
- Spring Boot
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- Jakarta Bean Validation
- Lombok

## Database

- PostgreSQL
- H2 Database for development/testing

## Build Tool

- Maven

## API Testing

- Postman
