pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Build') {
            steps {
                echo '🛠️ Compilation du projet...'
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                echo '✅ Exécution des tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo '📦 Création du package...'
                sh 'mvn package'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
