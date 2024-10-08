# Health Hive

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Version](https://img.shields.io/badge/version-1.0.0-yellow.svg)

## Description

**Health Hive** is a blockchain-based health and lab report storing system. It leverages modern technologies like Spring Boot, React, React Native, IPFS, Firebase, Pinatacloud, Docker, Prometheus, and Grafana to provide a secure and efficient solution for managing health records.

This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/tips). Feel free to contact us for further questions.

## Table of Contents

- [Development](#development)
- [Build](#build)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)
- [Further Readings](#further-readings)

## Development

When starting the application, `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development, it is recommended to use the profile `local`. In IntelliJ, `-Dspring.profiles.active=local` can be added in the VM options of the Run Configuration after enabling this property in "Modify options". Create your own `application-local.yml` file to override settings for development.

After starting the application, it is accessible under `localhost:8080`.

## Build

The application can be built using the following command:

```bash
./mvnw clean package
