pipeline {
    agent any
      tools {
          maven 'maven-3.6.3' 
    }
    stages {
        stage('Verify Branche') {
            steps {
                echo "$GIT_BRANCH"
            }
        }
        
        stage('Build') {
            steps {
                sh "mvn -B -DskipTests clean package"
            }
        }
             
    }
}