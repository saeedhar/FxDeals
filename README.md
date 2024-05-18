# Clustered Data Warehouse

## Overview
Clustered Data Warehouse is a Spring Boot application designed to manage FX deals. It provides endpoints to create, delete, and retrieve FX deals using a unique identifier. The application ensures data validation, custom error handling, and supports Docker for easy deployment.

## Features
- **FX Deal Management**: Create, delete, and retrieve FX deals.
- **Simple Codebase**: Easy-to-read and maintainable code.
- **Data Validation**: Ensures FX deals have valid data and are unique before saving.
- **Custom Error Handling**: Provides meaningful error responses.
- **Docker Support**: Includes Docker and Docker Compose configurations for seamless deployment.

## Getting Started

### Prerequisites
- Java 17
- Spring Boot 3.8.4
- Docker (for Docker-based deployment)
- Docker Compose (for Docker-based deployment)

### Building the Application
1. **Clone the Repository**:
   sh git clone <repository-url> cd <repository-directory>


2. **Build the Application**:
   sh mvn clean package


### Running the Application

#### Using Docker
1. **Ensure Docker and Docker Compose are installed and running**.
2. **Navigate to the project directory**.
3. **Start the application and its dependencies**:
   sh docker-compose up --build


#### Manually
1. **Ensure Java 17 is installed**.
2. **Navigate to the project directory**.
3. **Run the application**:
   sh java -jar target/clustered.data.warehouse-0.0.1-SNAPSHOT.jar


## API Documentation

### Endpoints

#### Create FX Deal
- **URL**: `POST /api/fxdeals`
- **Description**: Creates a new FX deal.
- **Request Body**: JSON representation of `FxDeal`.
- **Response**: JSON representation of `FxDealResponse` with a success message and the created FX deal's details.

#### Retrieve FX Deal
- **URL**: `GET /api/fxdeals/{uniqueId}`
- **Description**: Retrieves an FX deal by its unique identifier.
- **Path Variable**: `uniqueId` - The unique identifier of the FX deal.
- **Response**: JSON representation of `FxDealResponse` with the FX deal's details or a `404 Not Found` if the deal does not exist.

#### Delete FX Deal
- **URL**: `DELETE /api/fxdeals/{uniqueId}`
- **Description**: Deletes an FX deal by its unique identifier.
- **Path Variable**: `uniqueId` - The unique identifier of the FX deal.
- **Response**: Success message or a `404 Not Found` if the deal does not exist.

### Error Handling
- **400 Bad Request**: Returned for validation errors in the request body.
- **404 Not Found**: Returned when the FX deal is not found.
- **409 Conflict**: Returned when attempting to create an FX deal with a unique identifier that already exists.
- **500 Internal Server Error**: Returned when other issues occur.

## Docker Configuration

### Dockerfile
dockerfile FROM openjdk:17-jdk-slim WORKDIR /app COPY target/clustered.data.warehouse-0.0.1-SNAPSHOT.jar app.jar EXPOSE 8080 ENTRYPOINT ["java","-jar","app.jar"]

### Docker Compose
yaml version: '3' services: app: build:. ports: - "8080:8080" depends_on: - db environment: SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/fx_deals?useSSL=false&serverTimezone=UTC SPRING_DATASOURCE_USERNAME: user SPRING_DATASOURCE_PASSWORD: P@s
𝑤
0
𝑟
𝑑
𝑑
𝑏
:
𝑖
𝑚
𝑎
𝑔
𝑒
:
𝑚
𝑦
𝑠
𝑞
𝑙
:
8.0
𝑐
𝑜
𝑚
𝑚
𝑎
𝑛
𝑑
:
−
−
𝑑
𝑒
𝑓
𝑎
𝑢
𝑙
𝑡
−
𝑎
𝑢
𝑡
ℎ
𝑒
𝑛
𝑡
𝑖
𝑐
𝑎
𝑡
𝑖
𝑜
𝑛
−
𝑝
𝑙
𝑢
𝑔
𝑖
𝑛
=
𝑚
𝑦
𝑠
𝑞
𝑙
𝑛
𝑎
𝑡
𝑖
𝑣
𝑒
𝑝
𝑎
𝑠
𝑠
𝑤
𝑜
𝑟
𝑑
𝑒
𝑛
𝑣
𝑖
𝑟
𝑜
𝑛
𝑚
𝑒
𝑛
𝑡
:
𝑀
𝑌
𝑆
𝑄
𝐿
𝑅
𝑂
𝑂
𝑇
𝑃
𝐴
𝑆
𝑆
𝑊
𝑂
𝑅
𝐷
:
𝑁
𝑒
𝑤
𝑅
𝑜
𝑜
𝑡
𝑃
𝑎
𝑠
𝑠
𝑤
𝑜
𝑟
𝑑
123
𝑀
𝑌
𝑆
𝑄
𝐿
𝐷
𝐴
𝑇
𝐴
𝐵
𝐴
𝑆
𝐸
:
𝑓
𝑥
𝑑
𝑒
𝑎
𝑙
𝑠
𝑀
𝑌
𝑆
𝑄
𝐿
𝑈
𝑆
𝐸
𝑅
:
𝑢
𝑠
𝑒
𝑟
𝑀
𝑌
𝑆
𝑄
𝐿
𝑃
𝐴
𝑆
𝑆
𝑊
𝑂
𝑅
𝐷
:
𝑃
@
𝑠
w0rd ports: - "3306:3306" volumes: - mysql_data:/var/lib/mysql