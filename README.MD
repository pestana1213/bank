# Banking Transactions API

A Spring Boot REST API for managing banking transactions, including scheduling and updating transactions.

## API Endpoints

### 1. Get All Transactions
- **GET** `/transactions`
- **Response**: `200 OK`  
  Returns a list of all transactions as `TransactionDTOResponse` objects.

### 2. Get Transaction by ID
- **GET** `/transactions/{id}`
- **Path Variable**: `id` (Long)
- **Response**: `200 OK`  
  Returns a single transaction as a `TransactionDTOResponse`.

### 3. Get Scheduled Date for Transaction
- **GET** `/transactions/scheduling/{id}`
- **Path Variable**: `id` (Long)
- **Response**: `200 OK`  
  Returns the scheduled date (`Date`) for the transaction.

### 4. Create a Transaction
- **POST** `/transactions`
- **Request Body**: `CreateTransactionDTO` (JSON)
- **Response**: `201 Created`  
  Returns the created transaction as a `TransactionDTOResponse`.

#### Example Request Body:
```json
{
  "value": 1500.0,
  "scheduledDate": "2025-12-25T14:30:00",
  "accountFrom": "123456789",
  "accountTo": "987654321"
}
```
### 4. Delete a Transaction
- **DELETE** `/transactions/{id}`
- **Path Variable**: `id` (Long)
- **Response**: `200 OK`  

### 5. Modify a Transaction
- **PATCH** `/transactions`
- **Request Body**: `TransactionDTO` (JSON)
- **Response**: `201 Created`  
  Returns the created transaction as a `TransactionDTOResponse`.
- 
#### Example Request Body:
The id is mandatory, but only one of value of scheduledDate needs to be present
```json
{
  "id": 1,
  "value": 1500.0,
  "scheduledDate": "2025-12-25T14:30:00"
}
``` 

# Prerequisites
- Java 21
- Maven 3.9+
- Spring Boot 3.4.4

# Running  the Application

Build the project:

```bash
    mvn clean install
```
Run the Spring Boot app:

```bash
    mvn spring-boot:run
```
# H2 Database Console
Access in-browser H2 console at http://localhost:8080/h2-console.

- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password


## Preloaded Transaction Data 🗃️
The database initializes with these sample transactions:

| ID | Transaction Value | Scheduled Date       | Fee  | Accountfrom | AccountTo |
|----|-------------------|----------------------|------|-------------|-----------|
| 1  | 1000.0            | 2025-03-28 10:00:00 | 10.0 | 1           | 2         |
| 2  | 2000.0            | 2025-03-29 14:30:00 | 15.0 | 3           | 4         |
| 3  | 3000.0            | 2025-03-30 09:15:00 | 20.0 | 2           | 3         |

# Running Spring Boot Application with Docker

## 1. Pull the Docker Image

Docker Hub repo: https://hub.docker.com/repository/docker/joaopestana123/transactions/general

You can pull the Docker image from Docker Hub using the following command:

```bash
    docker pull joaopestana123/transactions:latest
```
```bash
    docker run -p 8080:8080 joaopestana123/transactions:latest
```

And hit the endpoints as above