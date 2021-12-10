pipeline {
       agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }

    stages {
        stage('Verify Branche') {
            steps {
                echo "$GIT_BRANCH"
            }
        }
        
        stage('Build') {
            steps {
               sh 'mvn -B -DskipTests clean package' 
            }
        }
             
    }
}
