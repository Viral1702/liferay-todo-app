# Liferay Todo Workspace

A Liferay 7.4 workspace with a Todo service module and Hello World portlet demonstrating MySQL integration.

## Run with Docker

```bash
docker compose up -d
```

Wait ~2-3 minutes for Liferay to start, then build and deploy modules:

```bash
./gradlew :modules:todo:todo-api:jar :modules:todo:todo-service:jar :modules:hello-world:jar
cp modules/todo/todo-api/build/libs/*.jar deploy/
cp modules/todo/todo-service/build/libs/*.jar deploy/
cp modules/hello-world/build/libs/*.jar deploy/
```

On first boot, fix the DB column:

```bash
docker exec -it liferay-mysql mysql -u viral -pviral lportal \
  -e "ALTER TABLE TODO_Todo CHANGE todoText text_ VARCHAR(75);"
```

## Access
- Liferay: http://localhost:8080
- Default login: test@liferay.com / test
