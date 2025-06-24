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

        stage('Exporter résultats de test') {
            steps {
                echo '📄 Extraction des résultats de test...'
                // Extrait le résumé des tests depuis les fichiers Surefire
                sh '''
                    PASSES=$(grep -o 'Tests run:.*Failures: 0, Errors: 0, Skipped: 0' target/surefire-reports/*.txt || true)
                    echo "$PASSES" > resultats_test.txt
                '''
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
                sh 'echo "Build terminé avec succès le $(date)" > mon_fichier.txt'
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
                attachmentsPattern: '**/mon_fichier.txt, **/resultats_test.txt'
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
            junit '**/target/surefire-reports/*.xml'
            echo '📬 Pipeline terminé.'
        }
    }
}
