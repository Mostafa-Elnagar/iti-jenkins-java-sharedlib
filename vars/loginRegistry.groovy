#!/usr/bin/env groovy

def call(Map config = [:]) {
    def credentialsId = config.credentialsId ?: 'docker-registry'
    
    if (credentialsId && credentialsId != '') {
        withCredentials([usernamePassword(credentialsId: credentialsId, 
                                       usernameVariable: 'REGISTRY_USER', 
                                       passwordVariable: 'REGISTRY_PASS')]) {
            sh "echo ${REGISTRY_PASS} | docker login -u ${REGISTRY_USER} --password-stdin"
        }
        echo "Successfully logged into registry Docker hub"
    } else {
        echo "No credentials provided, skipping registry login"
    }
}
