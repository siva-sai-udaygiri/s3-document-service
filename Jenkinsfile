pipeline {
  agent any

  options {
    timestamps()
    disableConcurrentBuilds()
  }

  environment {
    AWS_REGION = 'us-east-1'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build + Unit Tests') {
      steps {
        script {
          if (isUnix()) {
            sh 'mvn -B -ntp clean test'
          } else {
            bat 'mvn -B -ntp clean test'
          }
        }
      }
    }

    stage('Terraform fmt (check only)') {
      steps {
        dir('infra') {
          script {
            if (isUnix()) {
              sh 'terraform fmt -check -recursive'
            } else {
              bat 'terraform fmt -check -recursive'
            }
          }
        }
      }
    }

    // Optional (recommended later): validate the Terraform config
    // stage('Terraform validate') {
    //   steps {
    //     dir('infra/app') {
    //       script {
    //         if (isUnix()) {
    //           sh 'terraform init -backend=false'
    //           sh 'terraform validate'
    //         } else {
    //           bat 'terraform init -backend=false'
    //           bat 'terraform validate'
    //         }
    //       }
    //     }
    //   }
    // }
  }

  post {
    always {
      junit 'target/surefire-reports/*.xml'
    }
  }
}
