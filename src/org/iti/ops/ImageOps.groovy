#!/usr/bin/env groovy

package org.iti.ops

class ImageOps implements Serializable {
    def steps
    
    ImageOps(steps) {
        this.steps = steps
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
        
        // Login to registry
        if (config.credentialsId && config.credentialsId != '') {
            steps.loginRegistry(
                registryUrl: config.registryUrl ?: 'docker.io',
                credentialsId: config.credentialsId
            )
        }
        
        // Push image
        steps.pushImage(
            imageName: config.imageName ?: 'java-app',
            imageTag: config.imageTag ?: 'latest',
            registryUrl: config.registryUrl ?: 'docker.io'
        )
        
        steps.echo "Image build and push process completed successfully"
    }
}
