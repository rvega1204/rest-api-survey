# Survey API - Spring Boot REST Project

## Overview
This is a free **REST API** project built with **Java 21**, **Spring Boot**, **JPA**, and **H2 database**. It provides endpoints to manage surveys and their related questions. The project also integrates **Spring Security** for authentication and includes **Mockito, JUnit**, and **Integration Tests** to ensure code quality.

## Features
- RESTful API for managing surveys and questions.
- Built using **Spring Boot** with **JPA** for persistence.
- **H2 database** for lightweight storage.
- **Spring Security** for authentication and authorization.
- Unit and integration testing using **Mockito** and **JUnit**.

## Endpoints
### Surveys
- `GET /surveys` - Retrieve all surveys.
- `GET /surveys/{id}` - Retrieve a survey by ID.

### Questions
- `GET /surveys/questions` - Retrieve all questions.
- `GET /surveys/{surveyId}/questions/{questionId}` - Retrieve a specific question from a survey.
- `POST /surveys/{surveyId}/questions` - Add a new question to a survey.
- `DELETE /surveys/{surveyId}/questions/{questionId}` - Delete a question from a survey.
- `PUT /surveys/{surveyId}/questions/{questionId}` - Update a question in a survey.

## Technologies Used
- **Java 21**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database**
- **Mockito & JUnit** (for testing)

## Installation & Run
1. Clone the repository:
   ```sh
   git clone https://github.com/rvega1204/rest-api-survey.git
   ```
2. Navigate to the project directory:
   ```sh
   cd survey-api
   ```
3. Build the project:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```
5. Access the API at:
   ```sh
   http://localhost:8080
   ```

## Testing
Run the tests using:
```sh
mvn test
```

## License
This project is open-source and free to use for learning purposes. This is a basic project, you can modify it according to your needs, have fun!
