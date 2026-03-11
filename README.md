# Getting Started

This project demonstrates the seamless integration between Spring Boot and Docker Compose.
By leveraging Spring's development-time services, the application automatically discovers and connects to infrastructure
(like PostgreSQL) without requiring manual JDBC configuration.

**Key Features**

- Zero-Config Connection: Spring Boot automatically parses compose.yaml to provide the spring.datasource.url, username,
  and password.
- Infrastructure as Code: Local development environment parity using Docker.
- Optimized for macOS: A "Desktop-free" Docker approach using remote contexts via SSH.

Services & Auto-Configuration
The project includes a compose.yaml file defining the following:

- PostgreSQL: Uses the `postgres:latest` image.
- Auto-Wiring: At runtime, Spring Boot's spring-boot-docker-compose module detects the Postgres service and
  provides the necessary connection properties to Spring Data JPA automatically.

**Prerequisites**
To run this project on macOS without the overhead of Docker Desktop:
Install CLI Tools:

```Bash
brew install docker docker-compose
```

Remote Docker Host: Ensure you have a Linux server (or VM) with Docker installed and SSH access enabled.

#### **Remote Docker Setup (The "Lite" Mac Way)**

Instead of installing Docker Desktop, this project uses a client-server model. You can bind your local Mac Docker CLI to
a remote Linux engine.

1. Create a New Context

```Bash
docker context create alpine-linux-docker --docker "host=ssh://user@192.168.1.10"
```

2. Switch to the Context

```Bash
docker context use alpine-linux-docker
```

Now, any docker or docker compose command run on your Mac will execute on the remote Linux server.

3. In application.properties, add docker host address for application to aware of

```bash
spring.docker.compose.host=192.168.1.10
```

### P6Spy

P6Spy is a framework that enables database data to be seamlessly intercepted and logged with no code changes to existing
application.
The P6Spy distribution includes P6Log, an application which logs all JDBC transactions for any Java application.

### Reference Documentation

For further reference, please consider the following sections:

* [Spring Data JPA](https://docs.spring.io/spring-boot/4.0.3/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Docker Compose Support](https://docs.spring.io/spring-boot/4.0.3/reference/features/dev-services.html#features.dev-services.docker-compose)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)


