# Grable Project

## Overview
Grable is a comprehensive solution designed to manage restaurant information, including menus and menu items. It enables the creation, retrieval, update, and deletion of restaurant profiles, menus, and specific menu items through a RESTful API.

## Group 4 Members
- [Eldaniz Akbarzade](mailto:eldaniz.akbarzade@ut.ee)
- [Karl Soosalu](mailto:karl.soosalu@ut.ee)
- [Mushfug Soltanov](mailto:mushfug.soltanov@ut.ee)
- [Samir Musali](mailto:samir.musali@ut.ee)

## Features
- Manage restaurant profiles
- Handle restaurant menus
- Control menu items
- Filter restaurants by location and name
- Support for PostgreSQL database
- Utilizes Spring Boot for the backend

## Getting Started

### Prerequisites
- Java 17
- Maven
- PostgreSQL

### Setup
1. Clone the repository.
2. Ensure PostgreSQL is running and a database named `grable` is created.
3. Update `src/main/resources/application.properties` with your PostgreSQL username and password.

### Build
Navigate to the project root directory and run:
```sh
mvn clean install
```

### Run
Execute the following command:
```sh
java -jar target/grable-0.0.1-SNAPSHOT.jar
```
Alternatively, use Spring Boot's Maven plugin:
```sh
mvn spring-boot:run
```

## REST API
The project defines several endpoints under `/restaurants` for managing restaurants, their menus, and menu items. Refer to `RestClientFile.rest` for examples of API calls.

## Documentation
The Grable API is fully documented using OpenAPI 3.0. The documentation can be accessed through the Swagger UI once the application is running. Visit `http://localhost:8080/swagger-ui.html` in your web browser to explore the API endpoints, their request/response structures, and to try out the API directly from the UI.

This comprehensive documentation is ideal for developers looking to integrate with the Grable API, providing all necessary details to understand and interact with the API effectively.

### Endpoints
- `GET /restaurants`: List all restaurants.
- `POST /restaurants`: Create a new restaurant.
- `GET /restaurants/{id}`: Get a restaurant by ID.
- `PUT /restaurants/{id}`: Update a restaurant.
- `DELETE /restaurants/{id}`: Delete a restaurant.
- `GET /restaurants/{id}/menu`: Get the restaurant's menu.
- `POST /restaurants/{id}/menu`: Create a menu for the restaurant.
- `DELETE /restaurants/{id}/menu/{menuId}`: Delete the menu.
- `GET /restaurants/{id}/menu/items`: List menu items.
- `POST /restaurants/{id}/menu/{menuId}/items`: Create a new menu item.
- More endpoints for menu items management.

## Testing
Tests can be run with Maven:
```sh
mvn test
```

## License
See the [LICENSE](./LICENSE) file and https://opensource.org/licenses/MIT

## Contributing
Contributions are always welcome. See the [contributing guide](/CONTRIBUTING.md) to learn how you can help.