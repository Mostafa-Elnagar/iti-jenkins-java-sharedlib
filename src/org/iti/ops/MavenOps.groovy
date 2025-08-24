#!/usr/bin/env groovy

package org.iti.ops

class MavenOps implements Serializable {
    def steps
    
    MavenOps(steps) {
        this.steps = steps
    }
    
    def buildProject(Map config = [:]) {
        steps.echo "Starting Maven build process..."
        
        steps.buildJar(
            goals: config.goals ?: 'clean package',
            pomFile: config.pomFile ?: 'pom.xml'
        )
        
        steps.echo "Maven build process completed successfully"
    }
    
    def runTests(Map config = [:]) {
        steps.echo "Running Maven tests..."
        
        steps.buildJar(
            goals: 'test',
            pomFile: config.pomFile ?: 'pom.xml'
        )
        
        steps.echo "Maven tests completed"
    }
    
    def installDependencies(Map config = [:]) {
        steps.echo "Installing Maven dependencies..."
        
        steps.buildJar(
            goals: 'dependency:resolve',
            pomFile: config.pomFile ?: 'pom.xml'
        )
        
        steps.echo "Maven dependencies installed"
    }
}
