Deploying to Render — quick guide

1) Prepare repo
- Push your code to GitHub and ensure changes are committed.

2) Configure app to read env vars
- `application.properties` already uses environment variables for datasource and port.
  - `server.port` uses `${PORT:8080}`
  - `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`

3) Choose deployment method on Render
Option A — Use Render Web Service (without Docker)
- In Render dashboard: New -> Web Service -> Connect your GitHub repo.
- Branch: `main` (or your branch)
- Build Command: `mvn -DskipTests package`
- Start Command: `java -Dserver.port=$PORT -jar target/*.jar`
- Add environment variables under Service -> Environment -> Add Key:
  - `SPRING_DATASOURCE_URL` (e.g., `jdbc:postgresql://<host>:<port>/<db>`)
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
- If you plan to use a Render Postgres DB, create it in Render and use the provided connection string.

Option B — Use Docker (recommended if you need a specific JDK)
- We added a `Dockerfile` to repo. In Render: New -> Web Service -> Docker
- Render will build the image using the Dockerfile and run the container.
- Set the same environment variables as above.

4) Database note
- Render has managed PostgreSQL. It's easiest to use Postgres on Render and update `SPRING_DATASOURCE_URL` to a Postgres JDBC URL.
- If you must use SQL Server, ensure your database is reachable from Render (public IP + allow connections) and set `SPRING_DATASOURCE_URL` accordingly.

5) Health check & port
- Render supplies `$PORT`; the start command uses it. Optionally add a health check path `/actuator/health` if you add `spring-boot-starter-actuator`.

6) After deploy
- Check Logs in Render dashboard to see build and runtime logs.
- Test the site URL Render provides.

If you want, I can:
- Create a `render.yaml` for automatic service configuration, or
- Create a Postgres DB on Render and update `application.properties` to default to Postgres, or
- Add `spring-boot-starter-actuator` and an `/health` endpoint for Render health checks.

Which option should I do next?