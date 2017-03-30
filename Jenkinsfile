node {
  branch = sh(returnStdout: true, script: "git branch | grep \\* | awk '{print \$2}'").trim()

  stage 'Checkout'
  git 'https://github.com/uken/spring_demo.git'

  stage 'Tests'
  sh "./mvnw verify"

  if (branch == 'master' || branch == 'production') {
    stage 'Maven build'
    sh "./mvnw clean package -Dmaven.test.skip=true"

    stage 'Docker build'
    docker.build('spring_demo')

    stage 'Docker push'
    docker.withRegistry('https://661382096004.dkr.ecr.us-east-1.amazonaws.com/bingo-pop', 'ecr:us-east-1:main-aws-credentials') {
      docker.image('spring_demo').push('v_' + currentBuild.number)
    }

    if (branch == 'master') {
      stage 'Deploy To Staging'
       sh "./ecs/deploy.sh spring_demo_service ${currentBuild.number} spring_demo bingo-pop-refds 8080 Platform-Jenkins-EC2BuilderIamUser-6DB6WP8EH17K bingo-pop"
    }
  }
}

