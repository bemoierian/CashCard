# Cash Card Simulation Application

This Java Spring Boot application simulates a cash card system, providing CRUD (Create, Read, Update, Delete) operations for managing cash cards. It was developed following Test-Driven Development (TDD) practices, ensuring reliability and maintainability.

## Features

- CRUD operations for managing cash cards.
- Utilizes Spring Test for unit tests of controllers and models.
- Uses H2 in-memory database for data storage.
- Employs Spring Data JDBC for Object-Relational Mapping (ORM).
- Implements Spring Security for secure access.
- Supports role-based access control.

## Technologies Used

- Java
- Spring Boot
- Spring Test
- H2 Database
- Spring Data JDBC
- Spring Security

## Getting Started

To run this application locally, follow these steps:

1. Clone the repository to your local machine:

```bash
git clone https://github.com/bemoierian/CashCard.git
```

2. Navigate to the project directory:

```bash
cd CashCard
```

3. Build the project using Gradle:

```bash
./gradlew build
```

## Testing

This application includes unit tests for controllers and models to ensure the reliability of the codebase. To run the tests, use the following Gradle command:

```bash
./gradlew test
```
