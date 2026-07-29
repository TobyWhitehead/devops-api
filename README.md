# DevOps API

A Java Spring Boot REST API built as a DevOps learning project to demonstrate modern software delivery practices. The application provides a simple CRUD API for storing messages in a PostgreSQL database while showcasing containerisation, CI/CD, deployment automation and production-ready application practices.

## Features

* REST API built with Spring Boot
* PostgreSQL database with Spring Data JPA
* Request validation
* Global exception handling
* Structured JSON logging
* Spring Boot Actuator health endpoint
* Docker multi-stage build
* Docker Compose deployment
* Environment variable configuration
* GitHub Actions CI pipeline
* Docker Hub image publishing
* Deployment automation using `deploy.sh`

---

## Tech Stack

### Backend

* Java 26
* Spring Boot
* Spring Data JPA
* Maven
* PostgreSQL

### DevOps

* Docker
* Docker Compose
* GitHub Actions
* Docker Hub
* Linux (WSL)
* Git & GitHub

---

## Project Structure

```text
devops-api/
├── .github/
│   └── workflows/
│       └── build.yml
├── src/
│   ├── main/
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── deploy.sh
├── pom.xml
└── README.md
```

---

# Architecture

```text
                 Git Push
                    │
                    ▼
            GitHub Repository
                    │
                    ▼
             GitHub Actions
        ┌───────────┴───────────┐
        │                       │
        │                 Run Maven Tests
        │
        └───────────────┬───────────────┘
                        │
                        ▼
             Build Docker Image
                        │
                        ▼
            Push Image to Docker Hub
                        │
                        ▼
                 deploy.sh
                        │
                        ▼
             docker compose pull
                        │
                        ▼
              docker compose up -d
                        │
         ┌──────────────┴──────────────┐
         ▼                             ▼
 Spring Boot API              PostgreSQL Container
         │
         ▼
 Spring Data JPA
         │
         ▼
 PostgreSQL Database
```

---

# Running Locally

## Prerequisites

* Docker Desktop
* Docker Compose

Clone the repository:

```bash
git clone git@github.com:TobyWhitehead/devops-api.git
cd devops-api
```

Create a `.env` file if required.

Start the application:

```bash
docker compose up --build
```

The API will be available at:

```
http://localhost:8080
```

---

# Running the Tests

Run the test suite with Maven:

```bash
./mvnw test
```

The CI pipeline executes these tests automatically on every push.

---

# Docker

The application uses a multi-stage Docker build.

### Build stage

* Compiles the application
* Packages the executable JAR

### Runtime stage

* Uses a lightweight Java 26 JRE image
* Copies only the packaged JAR
* Starts the application

This keeps the production image smaller and avoids shipping build tools.

---

# Environment Variables

Application configuration is provided through environment variables rather than being hardcoded in the application.

Sensitive configuration files such as `.env` are intentionally excluded from version control. A template file (`.env.example`) is provided to show the required variables without exposing credentials.

Example:

```env
POSTGRES_USER=devops
POSTGRES_PASSWORD=change_me
POSTGRES_DB=devopsdb
```

Create a local `.env` file based on `.env.example` before running the application.

## Docker Compose Configuration

Docker Compose loads the values from the `.env` file and injects them into the application containers.

The PostgreSQL container uses these variables to initialise the database:

```yaml
POSTGRES_USER
POSTGRES_PASSWORD
POSTGRES_DB
```

The Spring Boot application receives the same values to connect to PostgreSQL:

```yaml
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

The credentials must match between the PostgreSQL and Spring Boot containers so that the application can successfully authenticate with the database.

When running locally, the `.env` file exists only on the developer machine. During deployment, the `.env` file is created separately on the server and is not pulled from GitHub.

For production environments, these values should be managed using a dedicated secrets management solution such as AWS Secrets Manager rather than storing them directly on the server.

---

# CI Pipeline

Every push to the main branch automatically performs the following:

1. Checkout repository
2. Set up Java
3. Run Maven tests
4. Build Docker image
5. Push Docker image to Docker Hub

The Docker image is tagged as both:

* `latest`
* Git commit SHA

Using the commit SHA allows previous versions to be redeployed if required.

---

# Deployment

Deployment is performed using the included `deploy.sh` script.

Deployment process:

1. Pull latest Docker image
2. Restart containers with Docker Compose
3. Verify the deployment using the Spring Boot health endpoint

Health endpoint:

```
http://localhost:8080/actuator/health
```

---

# Logging

The application uses structured JSON logging.

Examples include:

* Application startup
* Message creation
* Warning logs for missing resources
* Error logs for unexpected exceptions

Structured logs make the application easier to monitor and analyse.

---

# Error Handling

A global exception handler provides consistent API responses.

Example:

```json
{
  "timestamp": "2026-07-29T12:30:45Z",
  "status": 404,
  "error": "Message with id 999 not found",
  "path": "/messages/999"
}
```

---

# API Endpoints

| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| GET    | `/messages`        | Retrieve all messages     |
| GET    | `/messages/{id}`   | Retrieve a single message |
| POST   | `/messages`        | Create a new message      |
| GET    | `/actuator/health` | Application health        |

---

# Troubleshooting

### Docker cannot connect to PostgreSQL

When running inside Docker, ensure the datasource host is:

```
postgres:5432
```

rather than:

```
localhost:5432
```

---

### GitHub Actions reports `Permission denied`

Ensure the Maven wrapper is executable:

```bash
chmod +x mvnw
```

Commit the permission change:

```bash
git add mvnw
git commit -m "Make Maven wrapper executable"
```

---

### Health endpoint unavailable

Verify that Spring Boot Actuator is enabled and that:

```
/actuator/health
```

returns a status of `UP`.

---

# Future Improvements

Potential future enhancements include:

* Infrastructure as Code using Terraform
* Monitoring with Prometheus and Grafana
* Kubernetes deployment
* Automated cloud deployment
* HTTPS reverse proxy
* Automated rollback strategy

---

# Purpose

This project was built to gain practical experience with modern DevOps workflows, including containerisation, CI/CD pipelines, deployment automation and production-oriented application development using Spring Boot.
