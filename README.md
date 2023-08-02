# Address Book

This is a simple service for storing and searching postal addresses.

## Deployment diagram

![Deployment diagram](diagrams/deployment.png)

## How to navigate in this project

There are 3 big parts:
- [infra](infra/) contains a Terraform module for provisioning some "foundational" infrastructure.
- [dev/app](dev/app/) contains a Spring Boot application written in Kotlin which contains all the business logic.
- finally, [dev/stack](dev/stack/) contains a Java-based CDK app which provisions a DocumentDB cluster and deploys the Spring Boot application in ECS Fargate with an Application Load Balancer on top.

On top of that, there is a build pipeline for the Spring Boot application in [.github/workflows/main.yml](.github/workflows/main.yml).
