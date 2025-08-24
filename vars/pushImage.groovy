#!/usr/bin/env groovy

def call(Map config = [:]) {
    def registry   = config.registry ?: "docker.io"
    def imageName = config.imageName ?: 'java-app'
    def namespace  = config.namespace ?: "mostafaelnagar"
    def imageTag = config.imageTag ?: 'latest'
    def fullImage  = "${registry}/${namespace}/${imageName}:${imageTag}"
    
    echo "Pushing Docker image: ${fullImage}"
    
    sh "docker tag ${imageName}:${imageTag} ${fullImage}"
    sh "docker push ${fullImage}"
    
    echo "Docker image pushed successfully: ${fullImage}"
}
