# Jenkins Shared Library

Reusable pipeline components for Maven builds and Docker operations.

## Quick Start

1. **Clone**
   ```bash
   git clone https://github.com/Mostafa-Elnagar/iti-jenkins-java-sharedlib.git
   ```

2. **Add to Jenkins**
   - Manage Jenkins → Configure System
   - Add Global Pipeline Library
   - Name: `iti-jenkins-java-sharedlib`
   - Source: Git
   - Repository: `https://github.com/Mostafa-Elnagar/iti-jenkins-java-sharedlib.git`

3. **Use in Pipeline**
   ```groovy
   @Library('iti-jenkins-java-sharedlib') _
   
   pipeline {
       // Your pipeline code
   }
   ```

## Structure

```
src/org/iti/
├── SharedLibrary.groovy    # Main library
└── ops/
    ├── ImageOps.groovy     # Docker operations
    └── MavenOps.groovy    # Maven operations

vars/                       # Global functions
├── buildImage.groovy       # Docker building
├── buildJar.groovy        # Maven building
├── loginRegistry.groovy   # Registry auth
├── postAction.groovy      # Cleanup
└── pushImage.groovy       # Image pushing
```

## Core Classes

### MavenOps
- `buildProject()` - Build Maven project
- `runTests()` - Run test suite
- `installDependencies()` - Resolve deps

### ImageOps
- `loginRegistry()` - Auth with registry
- `buildAndPush()` - Build and push image
- `logoutRegistry()` - Logout

## Usage

```groovy
// Maven
def mavenOps = new org.iti.ops.MavenOps(this)
mavenOps.buildProject()
mavenOps.runTests()

// Docker
def imageOps = new org.iti.ops.ImageOps(this)
imageOps.buildAndPush(imageName: 'my-app', imageTag: 'latest')

// Global functions
postAction(cleanWorkspace: true)
```

## Prerequisites

- Jenkins with Pipeline plugin
- Docker installed
- Maven configured
- Registry credentials

## Related

- **Main Pipeline**: [full-jenkins-pipeline](https://github.com/Mostafa-Elnagar/full-jenkins-pipeline)
