pipeline {
    agent any

    environment {
        PATH = "/opt/homebrew/Cellar/maven/3.9.9/libexec/bin:$PATH"
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
        echo 'Pipeline terminé.'
    }
    success {
        echo 'Build réussie!'
        emailext (
            to: 'selimdhibmillioman@gmail.com',
            subject: "Build réussie: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Voir les détails sur Jenkins: ${env.BUILD_URL}"
        )
    }
    failure {
        echo 'Build échouée.'
        emailext (
            to: 'selimdhibmillioman@gmail.com',
            subject: "Build échouée: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Voir les détails sur Jenkins: ${env.BUILD_URL}"
        )
    }
}

}
