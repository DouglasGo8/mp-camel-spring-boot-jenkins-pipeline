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
        stage ('build app') {
            steps {
                sh 'mvn -DskipTests clean compile package'
            }
        }
        stage ('docker build') {
            steps {
                sh 'docker build -t app .'
            }
        }
        stage ('docker push') {
            steps {
                sh 'docker tag app douglasdb/app'
                sh 'docker push douglasdb/app'
            }
        }
    }
}