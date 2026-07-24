# DevOps API

## Technologies
- Java 26
- Spring Boot
- PostgreSQL
- Docker
- Docker Compose
- GitHub Actions

## Running locally

./mvnw package

docker build -t devops-api .

docker compose up

## API

POST /messages

GET /messages/{id}

## Running tests

./mvnw test

## CI

GitHub Actions runs tests on every push.

## Troubleshooting

If PostgreSQL isn't running:
docker compose up postgres

View logs:
docker compose logs -f app