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
                     //         sh "mvn -B -DskipTests clean package"
                        // Give full permision for the build on the development directory
                        // Executes maven tests (unitary and integration) and build the app .war file
                        sh 'mvn -B install'
                    }
            }
        }
             
    }
}
