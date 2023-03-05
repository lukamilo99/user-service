# User service

## Description
A service is responsible for managing user information, including storing, retrieving, and deleting user data. It also handles user authentication by verifying user credentials and exchanging them for JWT token, which is used for future authentication. Authorization is done using Spring Security and user roles. This service acts both as message consumer, as well as producer. It communicates with the rent service synchronously and notification service asynchronously. Data is stored in relational database(MySQL). 

## Structure
This service is part of a Rent-a-Car application that is structured as a set of microservices:
* [User service](https://github.com/lukamilo99/user-service) <br/>
* [Rent service](https://github.com/lukamilo99/rent-service) <br/>
* [Notification service](https://github.com/lukamilo99/notification-service) <br/>
* [Api gateway service](https://github.com/lukamilo99/api-gateway-service) <br/>
* [Registry service](https://github.com/lukamilo99/registry-service) <br/>

Each microservice is responsible for a specific task, and they work together to provide the functionality.
