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

        stage('Générer fichier') {
            steps {
                echo '📄 Génération du fichier texte...'
                sh 'echo "Build terminé avec succès le $(date)" > mon_fichier.txt'
                sh 'ls -l mon_fichier.txt' // Pour vérifier l'existence
            }
        }
    }

    post {
        success {
            emailext(
                subject: '✅ Build réussi',
                body: '''<p>Le build s'est terminé avec succès.</p>
<p>Voir les détails sur Jenkins : <a href="$BUILD_URL">$BUILD_URL</a></p>''',
                mimeType: 'text/html',
                to: 'selimdhibmillioman@gmail.com',
                attachmentsPattern: '**/mon_fichier.txt'
            )
        }

        failure {
            echo '❌ Build échouée.'
            emailext(
                to: 'selimdhibmillioman@gmail.com',
                subject: "Build échouée: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Voir les détails sur Jenkins: ${env.BUILD_URL}"
            )
        }

        always {
            echo '🧪 Publication des résultats de test...'
            junit '**/target/surefire-reports/*.xml'
            echo '📬 Pipeline terminé.'
        }
    }
}
