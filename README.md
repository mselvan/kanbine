
# Kanbine Time Tracker

Kanbine is a time tracking application designed to help users manage and track their time across various projects and assignments.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Contributing](#contributing)
- [License](#license)

## Features

- User authentication and management
- Create and manage assignments
- Track time entries for assignments
- View and analyze time logs

## Technologies

- Java
- Spring Boot
- JPA/Hibernate
- MySQL
- Gradle
- Lombok

## Getting Started
Following command runs the app in docker, automatically provisions a mysql docker container.
```sh
docker-compose up --build
```

Once the app is up, visit http://localhost:8080/swagger-ui/index.html


### Setup

1. **Clone the repository:**

   ```sh
   git clone <your_repository_url>
   cd kanbine
   ```

2. **Configure MySQL database:**

   Update `src/main/resources/application.properties` with your MySQL database configuration:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/kanbine
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project:**

   ```sh
   cd backend
   ./gradlew build
   ```

## Running the Application

1. **Start the Spring Boot application:**

   ```sh
   ./gradlew bootRun
   ```

2. **Access the application:**

   The application will be running at `http://localhost:8080`.

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add some feature'`)
5. Push to the branch (`git push origin feature/your-feature`)
6. Create a new Pull Request

## License

This project is licensed under the MIT License.
