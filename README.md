# ISA-Microservices: Music Streaming App

**Overview**

A microservices-based music streaming application built with Spring Boot, Angular, and Docker. It includes service discovery, API gateway, event-based communication, and RESTful APIs.

**Project Structure**

- **discovery-service/** - Eureka Server for service discovery

- **gateway-service/** - API Gateway with load balancing

- **playlist-service/** - Manages playlists (CRUD, events, REST API)

- **song-service/** - Manages songs (CRUD, events, REST API)

- **ng-music-app/** - Angular frontend (UI components, models, services)

- **docker-compose.yml** - Configuration for containerized deployment

- **Demo/** - Initial monolithic version (before microservices split)

**Features**

* Spring Boot Microservices with separate services for playlists and songs

* Event-Based Communication using an event-driven approach alongside RESTful APIs

* Eureka Service Discovery & API Gateway for dynamic routing and load balancing

* MongoDB as the external database for both services (initially used H2 for development)

* Angular Frontend for user interaction

* Docker-Compose Deployment for easy containerized setup

