# Jenkins Shared Library

A comprehensive Jenkins shared library providing reusable pipeline components for Maven builds and Docker image operations.

## Overview

This shared library contains custom pipeline functions and classes designed to streamline CI/CD workflows by encapsulating common operations like Maven builds, Docker image building, and container registry operations.

## Structure

```
src/org/iti/
├── SharedLibrary.groovy      # Main library class
├── ops/
│   ├── ImageOps.groovy       # Docker image operations
│   └── MavenOps.groovy      # Maven build operations
└── vars/                     # Global pipeline functions
    ├── buildImage.groovy     # Docker image building
    ├── buildJar.groovy       # Maven JAR building
    ├── loginRegistry.groovy  # Container registry login
    ├── postAction.groovy     # Post-build actions
    └── pushImage.groovy      # Image pushing to registry
```

## Features

### Maven Operations (`MavenOps`)
- **buildProject**: Build Maven project with custom goals
- **runTests**: Execute Maven test suite
- **installDependencies**: Resolve and install Maven dependencies

### Docker Operations (`ImageOps`)
- **buildAndPush**: Complete workflow for building and pushing Docker images
- **buildImage**: Build Docker images with custom Dockerfile and context
- **pushImage**: Push images to container registries
- **loginRegistry**: Authenticate with container registries

## Usage

### In Jenkinsfile

```groovy
@Library('iti-sharedlib') _

pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                script {
                    def sharedLib = new org.iti.SharedLibrary(this)
                    
                    // Build Maven project
                    sharedLib.mavenOps.buildProject(
                        mavenHome: 'Maven',
                        goals: 'clean package',
                        pomFile: 'pom.xml'
                    )
                    
                    // Build and push Docker image
                    sharedLib.imageOps.buildAndPush(
                        imageName: 'my-app',
                        imageTag: 'latest',
                        dockerfile: 'Dockerfile',
                        context: '.',
                        credentialsId: 'docker-registry-credentials',
                        registryUrl: 'docker.io'
                    )
                }
            }
        }
    }
}
```

### Direct Function Calls

```groovy
// Build Docker image
buildImage(
    imageName: 'my-app',
    imageTag: 'v1.0.0',
    dockerfile: 'Dockerfile',
    context: '.'
)

// Build JAR with Maven
buildJar(
    mavenHome: 'Maven',
    goals: 'clean package',
    pomFile: 'pom.xml'
)
```

## Configuration

### Maven Configuration
- `mavenHome`: Maven installation name (default: 'Maven')
- `goals`: Maven goals to execute (default: 'clean package')
- `pomFile`: Path to pom.xml (default: 'pom.xml')

### Docker Configuration
- `imageName`: Docker image name (default: 'java-app')
- `imageTag`: Image tag (default: 'latest')
- `dockerfile`: Dockerfile path (default: 'Dockerfile')
- `context`: Build context (default: '.')
- `registryUrl`: Container registry URL (default: 'docker.io')
- `credentialsId`: Jenkins credentials ID for registry authentication

## Prerequisites

- Jenkins with Pipeline plugin
- Docker installed and configured
- Maven installation configured in Jenkins
- Container registry credentials (if pushing images)

## Installation

1. Clone this repository to your Jenkins server
2. Configure the shared library in Jenkins Global Pipeline Libraries
3. Reference the library in your Jenkinsfiles using `@Library('library-name')`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

[Add your license information here]
