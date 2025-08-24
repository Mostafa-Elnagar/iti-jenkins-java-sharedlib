#!/usr/bin/env groovy

def call(Map config = [:]) {
    def registryUrl = config.registryUrl ?: 'docker.io'
    def credentialsId = config.credentialsId ?: 'docker-registry'
    
    if (credentialsId && credentialsId != '') {
        withCredentials([usernamePassword(credentialsId: credentialsId, 
                                       usernameVariable: 'REGISTRY_USER', 
                                       passwordVariable: 'REGISTRY_PASS')]) {
            sh "echo ${REGISTRY_PASS} | docker login ${registryUrl} -u ${REGISTRY_USER} --password-stdin"
        }
        echo "Successfully logged into registry: ${registryUrl}"
    } else {
        echo "No credentials provided, skipping registry login"
    }
}
