pipeline {
    agent any

    environment {
        // Docker Hub credentials (Jenkins Credential ID)
        DOCKER_HUB_CREDENTIALS = credentials('kduah') // Replace 'kduah' with your actual Docker Hub credential ID

        // Docker image name (Docker Hub repository in the format 'username/repository')
        DOCKER_IMAGE = 'kduah/deployment' // Replace with your Docker Hub username and repository name

        // Maven installation name (must match the name in Jenkins Global Tool Configuration)
        MAVEN_HOME = tool name: 'Maven 3.9.9', type: 'maven' // Ensure this matches your Maven setup in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone the Git repository
                git url: 'https://github.com/Kwaku-Duah/integration-deployment.git', branch: 'main' // Replace with your repository URL and branch
            }
        }

        stage('Build with Maven') {
            steps {
                // Use the configured Maven tool to build the project
                withMaven(maven: 'Maven 3.8.1') { // Ensure this matches the Maven installation name
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the Dockerfile in the repository
                    dockerImage = docker.build("${DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push the Docker image to Docker Hub using the configured credentials
                    docker.withRegistry('https://registry.hub.docker.com', 'kduah') { // 'kduah' is the Docker Hub credential ID
                        dockerImage.push()
                        dockerImage.push('latest') // Optionally tag the image as 'latest'
                    }
                }
            }
        }

        stage('Deploy to Local Environment') {
            steps {
                echo 'Deploying to the local environment...'
                script {
                    // Start PostgreSQL and Redis containers if not already running
                    // Stop and remove existing application container if it exists
                    sh '''
                        # Start PostgreSQL
                        docker run -d --name concurrency-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:14-alpine || true
                        
                        # Start Redis
                        docker run -d --name concurrency-redis -p 6379:6379 redis:alpine || true
                        
                        # Stop and remove existing application container
                        docker stop concurrency-app-1 || true
                        docker rm concurrency-app-1 || true
                    '''

                    // Run the new Docker container for the Spring Boot application
                    sh "docker run -d --name concurrency-app-1 -p 8084:8084 ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker system to save space
            sh 'docker system prune -f'
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}
