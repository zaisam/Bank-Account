pipeline {
    agent any
      tools {
          maven 'maven' 
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
