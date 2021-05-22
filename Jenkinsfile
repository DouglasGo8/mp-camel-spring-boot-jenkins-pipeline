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
        stage ('acceptance stage' ){
            steps {
                sh 'docker run -d --rm -p 8080:12080 --name app douglasdb/app'
            }
        }
        stage ('remove container') {
            steps {
                sh 'docker stop app'
            }
        }
    }

    post {
        always {
            sh 'rm -r *'
        }
    }
}