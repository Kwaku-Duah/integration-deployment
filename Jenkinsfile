pipeline {
    agent any
    
    stages {
        stage('Build with Maven') {
            steps {
                sh './mvn clean package'
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

