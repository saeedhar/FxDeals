# Clustered Data Warehouse

## Features

- **FxDeal Management**: Create and delete and retrieve FX deals with unique identifier.
- **Simple Code**: the code is simple and easy to read.
- **Validation**: Ensures that FX deals have valid data and is not exist in DB before they are saved.
- **Error Handling**: Provides custom error responses for validation errors.
- **Docker Support**: Includes MakeFile and docker-compose configuration for easy deployment.

## Getting Started

### Prerequisites

- Java 17
- Spring boot 3.8.4

### Building the Application

1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean package` to build the application.

### Running the Application

#### Using Docker

1. Ensure Docker and Docker Compose are installed and running.
2. Navigate to the project directory.
3. Run `docker-compose up --build` to start the application and its dependencies.

#### Manually

1. Ensure Java 17 is installed.
2. Navigate to the project directory.
3. Run `java -jar target/ClusteredData` to start the application.

## API Documentation

### Endpoints

- **POST /api/fxdeals**: Creates a new FX deal.
 - Request Body: `FxDeal` object.
 - Response: `FxDealResponse` object with a success message and the created FX deal's details.

- **GET /api/fxdeals/{uniqueId}**: Retrieves an FX deal by its unique identifier.
 - Path Variable: `uniqueId` - The unique identifier of the FX deal.
 - Response: `FxDealResponse` object with the FX deal's details or a 404 Not Found if the deal does not exist.
 
 - **DELETE /api/fxdeals/{uniqueId}**: Delete an FX deal by its unique identifier.
 - Path Variable: `uniqueId` - The unique identifier of the FX deal.
 - Response: `Delete successfully` object FX deal's or a 404 Not Found if the deal does not exist.

### Error Handling

- **400 Bad Request**: Returned for validation errors in the request body.
- **404 Not Found**: Returned when customer not found.
- **409 Conflict**: Returned when attempting to create an FX deal with a unique identifier that already exists.
- **500 Internal Server Error**: Returned when there other issue happened.
