pipeline {
    agent any
    tools {
        maven 'MAVEN_DEF'
    }
    stages {
        stage ('Unit Test') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage ('Unit Test') {
            steps {
                sh 'mvn clean test'
            }
        }
    }
}