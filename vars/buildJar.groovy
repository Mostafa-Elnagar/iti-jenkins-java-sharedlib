#!/usr/bin/env groovy

def call(Map config = [:]) {
    def goals = config.goals ?: 'clean package'
    def pomFile = config.pomFile ?: 'pom.xml'
    def mavenTool = config.mavenTool ?: '/usr/share/maven'
    
    echo "Building JAR with Maven..."
    echo "Goals: ${goals}"
    echo "POM file: ${pomFile}"
    
    // Validate that pom.xml exists
    if (!fileExists(pomFile)) {
        error "POM file ${pomFile} not found!"
    }
    
    try {
        withMaven(maven: mavenTool) {
            sh "mvn -f ${pomFile} ${goals}"
        }
        echo "JAR build completed successfully"
    } catch (Exception e) {
        echo "Error during Maven build: ${e.getMessage()}"
        error "Maven build failed: ${e.getMessage()}"
    }
}
