# Freelancer Skill Exchange RESTful API

## Overview
This repository contains a RESTful API for a Freelancer Skill Exchange platform. It allows users to manage services, skill exchanges between users, and more.

## Features
- CRUD operations for Users, Services, Exchanges
- Dynamic updating of offered and requested services
- Error handling and validation
- Integration with Spring Boot

## Technologies Used
- Java
- Spring Boot
- Postman (for testing)
- H2 Database For Runtime Testing ( Username "sa", Password "password", JDBC URL "jdbc:h2:mem:testdb" )

## Setup Instructions
To run this project locally, follow these steps:

### Prerequisites
- JDK 11 or higher
- Maven

### Steps
1. **Clone the repository**
2. **Build the project**
3. **Run the application**
4. **Access H2 Database and connect**
   - The API will be available at http://localhost:8080/h2-console
   - Credentials
     Username "sa"
     Password "password"
     JDBC URL "jdbc:h2:mem:testdb"
5. **Access the API**
   - The API will be available at `http://localhost:8080/`

## API Endpoints

### Users

- **GET api/users**: Retrieve all users
- **GET api/users/{id}**: Retrieve a user by ID
- **POST api/users**: Create a new user
- **PUT api/users/{id}**: Update an existing user
- **DELETE api/users/{id}**: Delete a user

### Services

- **GET /services**: Retrieve all services
- **GET /services/{id}**: Retrieve a service by ID
- **POST /services**: Create a new service
- **PUT /services/{id}**: Update an existing service
- **DELETE /services/{id}**: Delete a service

### Exchanges

- **GET /exchanges**: Retrieve all exchanges
- **GET /exchanges/{id}**: Retrieve an exchange by ID
- **POST /exchanges**: Create a new exchange
- **PUT /exchanges/{id}**: Update an existing exchange
- **DELETE /exchanges/{id}**: Delete an exchange

## Testing the API
To test the API endpoints, you can use Postman or any other REST client. Here are some sample requests:

### Sample Data
Use the following JSON data for testing:

#### Create User
```json
{
  "name": "John Doe",
  "email": "johndoe@example.com"
}
```

#### Create Service
```json
{
  "title": "Web Development",
  "description": "Building responsive websites",
  "providerId": 1
}
```

#### Create Exchange
```json
{
  "requesterId": 1,
  "serviceId": 1,
  "providerId": 2,
  "status": "pending"
}
```

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.
