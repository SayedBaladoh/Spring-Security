# JWT authentication in a Spring Boot 3 with Spring Security 6
A simple demo to implement JWT based authentication and authorization in a Spring Boot 3 application with Spring Security 6.

- JWT authentication and authorization
- Role-based Access Control.

---

## Table of contents
* [Technologies](#technologies)
* [Prerequisites](#prerequisites)
* [Getting Started](#getting-started)
* [About me](#about-me)
* [Contributing](#contributing)
* [Acknowledgments](#acknowledgments)

---

## Technologies
1. Java (21 or higher)
2. Maven Dependency Management
3. Spring Boot:
    - Spring Web: for building web APIs
    - Spring security: for security
    - Spring Data JPA: to store user data
    - PostgreSQL Driver: to interact with the Postgres database from a Java application.
4. JWT Json Web token
5. Postgresql
6. Lombok

---

## Prerequisites
- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/install.html)
- [Postgresql](https://www.postgresql.org/)
- An HTTP client such as Postman, cURL, etc.

---

## Getting Started

### **Clone the Repository**
```bash
git clone https://github.com/SayedBaladoh/Spring-Security.git
cd Spring-Security
cd JWT-Authentication-in-Spring-Boot-3-with-Spring-Security-6
```

### **Running Locally**

#### 1. **Start `Postgresql` Locally**
You can use docker:
```bash
docker run -d --name postgres-db -p 5432:5432 -e POSTGRES_PASSWORD=pass12345 postgres
```

#### 2. **Update Application Configuration**
The application is configured via `application.yml`:

```yaml
spring:
  application:
    name: spring-security
  datasource:
    url: jdbc:postgresql://${POSTGRES_HOST:localhost}:${POSTGRES_PORT:5432}/${POSTGRES_DB:spring_security_demo}
    username: ${POSTGRES_USERNAME:postgres}
    password: ${POSTGRES_PASSWORD:pass12345}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8081

security:
  jwt:
    secret-key: ${SECRET_KEY:AGgHUbsGwd4ywipmOB686bZm4SsaypBZIWgSjPWGFffPBSCBfbqUTNt5EoeBfrcqyD05L4K96A1}
    token-duration: ${TOKEN_DURATION:1D}
```

##### Environment Variables

- `POSTGRES_HOST`: Postgresql hostname (default: `localhost`).
- `POSTGRES_PORT`: Postgresql port (default: `5432`).
- `POSTGRES_DB`: Postgresql database name (default: `spring_security_demo`).
- `POSTGRES_USERNAME`: Postgresql username (default: `postgres`).
- `POSTGRES_PASSWORD`: Postgresql password (default: `pass12345`).
- `SECRET_KEY`: Secret key (default: `AGgHUbsGwd4ywipmOB686bZm4SsaypBZIWgSjPWGFffPBSCBfbqUTNt5EoeBfrcqyD05L4K96A1`). 
   You can get secret key from this website (https://randomkeygen.com/).
- `TOKEN_DURATION`: Token duration (default: `1D` 1 day).

The [SpringSecurityApplication.initializeDb](./src/main/java/com/sayedbaladoh/SpringSecurityApplication.java) initialize the app data by adding some roles and users.
 You can change it.

If you want to change from database, You can generate password from this website (https://bcrypt-generator.com/).

#### 3. **Build and Run**
- Run the application with the command 
```bash
mvn spring-boot:run
```
it will start at port 8081.

- or

```bash
mvn clean package -DskipTests
java -jar target/spring-security-0.0.1-SNAPSHOT.jar
```

#### 4. **Test the different endpoints using CURL or Postman**.
##### **Login**
- **Endpoint**: `POST /api/v1/auth/login`
- **Payload**:
   - `username`: The username.
   - `password`: The password.
- **Example Request**:
```bash
curl --location 'http://localhost:8081/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data '{
"username": "admin1",
"password": "12345"
}'
```
- **Example Response**:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6IlJPTEVfQURNSU4iLCJzdWIiOiJhZG1pbjEiLCJpYXQiOjE3MzI3NzY5MDEsImV4cCI6MTczMjg2MzMwMX0.M4BRk2AGO37fTeBcuQmICCgaM_H0xd8ocf0CZmrxaP0"
}
```

##### **Signup new user**
- **Endpoint**: `POST /api/v1/auth/signup`
- **Payload**:
   - `username`: The username.
   - `password`: The password.
   - `firstName`: The user first name.
   - `lastName`: The user last name.
- **Example Request**:
```bash
curl --location 'http://localhost:8081/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "mahmoud1",
    "password": "12345",
    "firstName": "Mahmoud",
    "lastName": "Ahmed 1"
}'
```
- **Example Response**:
```json
{
   "id": 4,
   "firstName": "Mahmoud",
   "lastName": "Ahmed 1",
   "username": "mahmoud1",
   "createdAt": "2024-11-28T07:01:00.610+00:00",
   "updatedAt": "2024-11-28T07:01:00.610+00:00"
}
```

##### **Get all users**
- **Endpoint**: `GET /api/v1/users`
- **Example Request with token for `USER` Role**:
```bash
curl --location 'http://localhost:8081/api/v1/users' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6IlJPTEVfU1VQRVJfQURNSU4iLCJzdWIiOiJhZG1pbjEiLCJpYXQiOjE3MzI3MTAwMDgsImV4cCI6MTczMjc5NjQwOH0.ydwfXrR2fdDWC_DaAZ91P-RybZIecNHvR0w_J2C3mmQ'
```
- **The Example Response**:
```json
{
   "type": "about:blank",
   "title": "Forbidden",
   "status": 403,
   "detail": "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.",
   "instance": "/api/v1/users",
   "description": "The JWT signature is invalid"
}
```
- **Example Request with token for `ADMIN` Role**:
```bash
curl --location 'http://localhost:8081/api/v1/users' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6IlJPTEVfQURNSU4iLCJzdWIiOiJhZG1pbjEiLCJpYXQiOjE3MzI3NzY5MDEsImV4cCI6MTczMjg2MzMwMX0.M4BRk2AGO37fTeBcuQmICCgaM_H0xd8ocf0CZmrxaP0'
```
- **Example Response**:
```json
[
    {
        "id": 1,
        "firstName": "Ahmed",
        "lastName": "Mohamed",
        "username": "super_admin1",
        "createdAt": "2024-11-28T06:52:21.666+00:00",
        "updatedAt": "2024-11-28T06:52:21.666+00:00"
    },
    {
        "id": 2,
        "firstName": "Mohamed",
        "lastName": "Mohamed",
        "username": "admin1",
        "createdAt": "2024-11-28T06:52:21.830+00:00",
        "updatedAt": "2024-11-28T06:52:21.830+00:00"
    },
    {
        "id": 3,
        "firstName": "Mahmoud",
        "lastName": "Ahmed",
        "username": "user1",
        "createdAt": "2024-11-28T06:52:21.978+00:00",
        "updatedAt": "2024-11-28T06:52:21.978+00:00"
    },
    {
        "id": 4,
        "firstName": "Mahmoud",
        "lastName": "Ahmed 1",
        "username": "mahmoud1",
        "createdAt": "2024-11-28T07:01:00.610+00:00",
        "updatedAt": "2024-11-28T07:01:00.610+00:00"
    }
]
```
##### **Get current authenticated user**
- **Endpoint**: `GET /api/v1/me`
- **Example Request:
```bash
curl --location 'http://localhost:8081/api/v1/users/me' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6IlJPTEVfU1VQRVJfQURNSU4iLCJzdWIiOiJhZG1pbjEiLCJpYXQiOjE3MzI3NjgzNjQsImV4cCI6MTczMjg1NDc2NH0.AqDvYFFdyREGkE5jlPc7_jshpmnXs3aOrX4sWwLa43g'
```
- **The Example Response**:
```json
{
  "id": 2,
  "firstName": "Mohamed",
  "lastName": "Mohamed",
  "username": "admin1",
  "roles": [
    {
      "id": 2,
      "name": "ROLE_ADMIN",
      "description": "Administrator role",
      "createdAt": "2024-11-28T07:13:26.135+00:00",
      "updatedAt": "2024-11-28T07:13:26.135+00:00"
    }
  ],
  "createdAt": "2024-11-28T07:13:26.415+00:00",
  "updatedAt": "2024-11-28T07:13:26.415+00:00"
}
```

##### You can check all endpoints with different token from different user role.

---

## About me

I am Sayed Baladoh - Phd. Tech Lead / Principal Software Engineer. I like software development. You can contact me via:

* [LinkedIn](https://www.linkedin.com/in/sayedbaladoh/)
* [Mail](mailto:sayedbaladoh@yahoo.com)
* [Phone +20 1004337924](tel:+201004337924)

---

## Contributing

Any improvement or comment about the project is always welcome! Please create a pull request or submit an issue for any
suggestions.

---

## Acknowledgments

Thanks for reading. Share it with someone you think it might be helpful.