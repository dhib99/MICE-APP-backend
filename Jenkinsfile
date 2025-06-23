pipeline {
    agent {
        docker {
            image 'maven:3.9.3-openjdk-17'
            args '-v $HOME/.m2:/root/.m2'  // pour cacher le repo Maven local et accélérer les builds
        }
    }

    environment {
        BRANCH_NAME = 'main'  // ou 'backend' selon ta branche
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH_NAME}",
                    url: 'https://github.com/dhib99/MICE-APP-backend.git'
            }
        }

        stage('Build') {
            steps {
                echo '🛠️ Building the project...'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo '✅ Running tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'  // pour afficher les résultats de tests dans Jenkins
                }
            }
        }

        stage('Package') {
            steps {
                echo '📦 Packaging the app...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
