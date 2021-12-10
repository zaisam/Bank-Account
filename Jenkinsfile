pipeline {
    agent any

    stages {
        stage('Verify Branche') {
            steps {
                echo "$GIT_BRANCH"
            }
        }
        
        stage('Build') {
            steps {
                script {
                        // Give full permision for the build on the development directory
                        sh 'chmod -R 777 *'
                        // Executes maven tests (unitary and integration) and build the app .war file
                        sh 'mvn -B install'
                    }
            }
        }
             
    }
}
