#!/usr/bin/env groovy

package org.iti.ops

class ImageOps implements Serializable {
    def steps
    
    ImageOps(steps) {
        this.steps = steps
    }

    def loginRegistry(Map config = [:]) {
        def credentialsId = config.credentialsId ?: 'docker-registry'
        
        if (credentialsId && credentialsId != '') {
            steps.withCredentials(
                [
                    steps.usernamePassword(
                        credentialsId: credentialsId, usernameVariable: 'REGISTRY_USER',
                        passwordVariable: 'REGISTRY_PASS'
                    )
                ]
            ) {
                steps.sh 'echo $REGISTRY_PASS | docker login -u $REGISTRY_USER --password-stdin'
            }
            steps.echo "Successfully logged into registry Docker hub"
        } else {
            steps.echo "No credentials provided, skipping registry login"
        }
    }
    def buildAndPush(Map config = [:]) {
        steps.echo "Starting image build and push process..."
        
        // Build image
        steps.buildImage(
            imageName: config.imageName ?: 'java-app',
            imageTag: config.imageTag ?: 'latest',
            dockerfile: config.dockerfile ?: 'Dockerfile',
            context: config.context ?: '.'
        )
        
        // Push image
        steps.pushImage(
            imageName: config.imageName ?: 'java-app',
            imageTag: config.imageTag ?: 'latest'
        )
        
        steps.echo "Image build and push process completed successfully"
    }
    def logoutRegistry(Map config = [:]) {
        try {
            steps.sh "docker logout"
            steps.echo "Successfully logged out from all registries"
        } catch (Exception e) {
            steps.echo "Warning: Logout failed: ${e.getMessage()}"
        }
    }
}
