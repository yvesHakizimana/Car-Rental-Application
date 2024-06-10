# Car Rental Application

Welcome to the Car Rental Application, a protected REST API built with Spring Boot. This application allows users to sign up, authenticate, and book cars for rental.

## Features
- **User Authentication:** Secure OAuth2 authentication and authorization using GitHub or Google credentials.
- **Database Integration:** Integration with PostgreSQL database to store user data and car rental information.
- **REST API Endpoints:** Protected RESTful API endpoints for user authentication, car listing, and booking functionalities.
- **Data Persistence:** Utilization of JPA and Hibernate for efficient data persistence and management.
- **Containerization:** Dockerized setup for easy deployment and scalability.
- **Security Measures:** Robust security measures in place to ensure user data privacy and application integrity.

## Technologies Used
- Spring Boot: For building the REST API backend.
- PostgreSQL: For storing user and car rental data.
- OAuth2: For secure user authentication and authorization.
- Docker: For containerization and deployment.
- JPA and Hibernate: For efficient data persistence.
- GitHub and Google OAuth2: For user authentication.
- RESTful API: For communication between the frontend and backend.

## Getting Started
To get started with the Car Rental Application, follow these steps:

1. **Clone the Repository:** Clone this repository to your local machine.
2. **Set up PostgreSQL:** Ensure PostgreSQL is installed and configured with the required database schema.
3. **Set up OAuth2 Credentials:** Obtain client ID and client secret for GitHub and Google OAuth2 authentication.
4. **Configure Application:** Update the application.properties file with the necessary database and OAuth2 configurations.
5. **Run the Application:** Build and run the Spring Boot application.
6. **Access the API:** Use an API testing tool like Postman to interact with the protected REST API endpoints.

## API Endpoints
- **POST /signup:** Sign up a new user.
- **POST /login:** Log in and obtain access token for authentication.
- **GET /cars:** Retrieve a list of available cars for rental.
- **POST /book/{carId}:** Book a specific car for rental.

## Contributing
Contributions are welcome! If you have any suggestions or would like to contribute to the project, feel free to open an issue or submit a pull request on GitHub.

## License
This project is licensed under the [MIT License](LICENSE).

## Contact
For any inquiries or support, please contact Yves Hakizimana at yvhakizimana123@gmail.com.

Let's hit the road with Car Rental Application! 🚗💨
