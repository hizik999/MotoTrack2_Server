# MotoTrack2 Server

Spring Boot backend for the MotoTrack2 mobile app.

## Requirements

- Java 17 or newer
- Maven 3.6.3 or newer

## Local run

The default profile uses an in-memory H2 database, so the server starts without PostgreSQL:

```bash
mvn spring-boot:run
```

API base URL:

```text
http://localhost:2022
```

## PostgreSQL run

Use the `postgres` profile when you want to connect the server to PostgreSQL:

```bash
DB_URL=jdbc:postgresql://localhost:5432/mototrack2 \
DB_USERNAME=postgres \
DB_PASSWORD=postgres \
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

## API

```http
GET /moto
GET /moto/{id}
POST /moto
PUT /moto/{id}?lat={lat}&lon={lon}
DELETE /moto/{id}
```

Create a moto point with JSON:

```json
{
  "lat": 55.7522,
  "lon": 37.6156
}
```

Response:

```json
{
  "id": 1,
  "lat": 55.7522,
  "lon": 37.6156
}
```

## Verification

```bash
mvn test
```
