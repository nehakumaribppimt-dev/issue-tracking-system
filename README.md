# Issue Tracking System

A small Spring Boot REST API project written with straightforward class and variable names.

## Requirements

- Java 17 (or newer compatible JDK)
- Maven 3.9+ (IntelliJ can import the Maven project)
- MySQL 8+

## Run in IntelliJ

1. Extract the ZIP and open this folder in IntelliJ using **File > Open**.
2. Allow IntelliJ to load the Maven `pom.xml` dependencies.
3. Start MySQL and create a user with database creation permission, or use local root.
4. Edit `src/main/resources/application.properties`, or set environment variables `DB_USER`, `DB_PASSWORD`, `DB_URL`.
5. Run the green arrow next to `Application.main()`.
6. The server runs at `http://localhost:8081`.

## Endpoints

- `POST /api/auth/register` with `{{"name":"Neha","email":"neha@example.com","password":"mypassword123"}}`
- `POST /api/auth/login` with `{{"email":"neha@example.com","password":"mypassword123"}}`
- Send the returned JWT as `Authorization: Bearer <token>` for other endpoints.

- `GET /api/projects`, `GET /api/projects/1`, `POST /api/projects`, `PUT /api/projects/1`, `DELETE /api/projects/1`
- `GET /api/issues`, `GET /api/issues/1`, `POST /api/issues`, `PUT /api/issues/1`, `DELETE /api/issues/1`

POST and PUT accept JSON fields matching the entity Java fields. For related records,
use `{"project":{"id":1},"assignedTo":{"id":1}}` after those records exist.

## Example JSON

```json
{
  "title": "Fix login",
  "description": "Login sometimes fails",
  "status": "OPEN",
  "priority": "HIGH",
  "project": {
    "id": 1
  },
  "assignedTo": {
    "id": 1
  }
}
```

## GitHub

Run `git init`, `git add .`, `git commit -m "Initial project"`, then create an empty repository on GitHub and follow its
push instructions.

## Notes

- This is a learning-oriented backend starter, not a production-ready app.
- MySQL tables are created by Hibernate when the application starts.
- Never commit real passwords or JWT secrets. Use environment variables outside local development.
- Register/login uses BCrypt and a signed JWT. The default secret is **for local development only**. Set `JWT_SECRET` in
  any deployed environment.
- Pagination, filtering, stricter role permissions and full validation are improvements to add next.

