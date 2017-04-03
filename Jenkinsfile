node {
  branch = sh(returnStdout: true, script: "git branch | grep \\* | awk '{print \$2}'").trim()
  app_version = "rc_${currentBuild.number}"

  stage('Checkout') {
    git 'https://github.com/uken/spring_demo.git'
  }

  stage('Tests') {
    sh "./mvnw verify"
  }

  if (branch == 'master') {
    stage('Maven build') {
      sh "./mvnw clean package -Dmaven.test.skip=true"
    }

    stage('Docker build') {
      docker.build('spring_demo')
    }

    stage('Docker push') {
      docker.withRegistry('https://661382096004.dkr.ecr.us-east-1.amazonaws.com/bingo-pop', 'ecr:us-east-1:main-aws-credentials') {
        docker.image('spring_demo').push(app_version)
      }
    }

    stage('Deploy Staging') {
      sh """
        ./ecs/deploy.sh \
          spring_demo_service \
          ${app_version} \
          spring_demo \
          bingo-pop-ecs-staging \
          iarn:aws:elasticloadbalancing:us-east-1:661382096004:targetgroup/bingo-pop-refds-staging/8c9a57ec295f3c13 \
          8080 \
          Platform-Jenkins-EC2BuilderIamUser-6DB6WP8EH17K \
          bingo \
          staging \
          bingo-pop
      """
    }
  }
}
