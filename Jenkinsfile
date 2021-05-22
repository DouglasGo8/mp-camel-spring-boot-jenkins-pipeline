pipeline {
    agent any
    tools {
        maven 'MAVEN_DEF'
    }
    stages {
        stage ('Compile State') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage ('UnitTest Stage') {
            steps {
                sh 'mvn clean test'
            }
        }
         stage ('docker build') {
            steps {
                sh 'docker build -t app .'
            }
        }
        stage ('docker push') {
            steps {
                sh 'docker push douglasdb/app:latest'
            }
        }
    }
}