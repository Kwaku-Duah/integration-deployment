pipeline {
    agent any
    
    stages {
        stage('Build with Maven') {
            steps {
                sh './mvn clean install'
            }
        }


        stage('Stop Docker Services') {
            steps {
                sh 'docker-compose down'
            }
        }

        stage('Start Docker Services') {
            steps {
                sh 'docker-compose up -d --build'
            }
        }

    }


}

