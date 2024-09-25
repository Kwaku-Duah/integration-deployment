


pipeline {
    agent any
    
    stages {
        stage('Setup Git Config') {
            steps {
                // Set Git config for postBuffer to handle large files
                sh 'git config --global http.postBuffer 524288000'
            }
        }

        stage('Checkout Code') {
            steps {
                // Perform git checkout after setting the postBuffer
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                // Build the project using Maven
                sh './mvn clean package'
            }
        }

        stage('Stop Docker Services') {
            steps {
                // Stop any existing Docker services
                sh 'docker-compose down'
            }
        }

        stage('Start Docker Services') {
            steps {
                // Start the Docker services and rebuild images if necessary
                sh 'docker-compose up -d --build'
            }
        }
    }
}

