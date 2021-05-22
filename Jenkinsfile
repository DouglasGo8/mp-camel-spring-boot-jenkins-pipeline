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
    }
}