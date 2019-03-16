def CONTAINER_NAME="jenkins-pipeline"
def CONTAINER_TAG="latest"
def DOCKER_HUB_USER="ftchub"
def HTTP_PORT="8090"

// Only keep the 7 most recent builds
properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '7']]])

node {

    stage('Initialize'){
        def dockerHome = tool 'myDocker'
        def mavenHome  = tool 'myMaven'
        def scannerHome = tool 'mySonarScanner'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${scannerHome}/bin:${env.PATH}"
    }

    stage('Source Pull') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'git_credential_id', url: 'https://github.com/ftcuser/cicd.git']]])
    }

    stage('Build'){
        sh "mvn clean install -DskipTests"
        sh "cd $WORKSPACE"
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
    }

    stage('Junit Test'){
         //step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
    }

    stage('Code Coverage'){
        //step([$class: 'JacocoPublisher', execPattern: 'target/jacoco.exec', exclusionPattern: '**/classes'])
    }
    
    stage('Quality Scan'){
        try {
            withSonarQubeEnv('SonarServer') {
            sh "mvn sonar:sonar"
            //sh "${scannerHome}/bin/sonar-scanner"
            }
        } catch(error){
            echo "The sonar server could not be reached ${error}"
        }
     }
    
    stage("Quality Gate") {
                timeout(time: 1, unit: 'HOURS') {
                    //waitForQualityGate abortPipeline: true
                    def qg = waitForQualityGate() 
                    if (qg.status != 'OK') {
                         error "Pipeline aborted due to quality gate failure: ${qg.status}"
    }
                }
    } 

    stage("Security Scan") {
        sh 'mvn com.github.spotbugs:spotbugs-maven-plugin:3.1.1:spotbugs'
        //step([$class: 'FindBugsPublisher', pattern: '**/target/spotbugsXml.xml'])
        findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/target/spotbugsXml.xml', unHealthy: ''    
        //def spotbugs = scanForIssues tool: [$class: 'SpotBugs'], pattern: '**/target/spotbugsXml.xml'
        //publishIssues issues:[spotbugs]
        
        
    } 
    
    stage("Remove Old Docker Image"){
        imagePrune(CONTAINER_NAME)
    }

    stage('Build New Docker Image'){
        imageBuild(CONTAINER_NAME, CONTAINER_TAG)
    }

    stage('Push to DockerHub Registry'){
        withCredentials([usernamePassword(credentialsId: 'docker_credential_id', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            pushToImage(CONTAINER_NAME, CONTAINER_TAG, USERNAME, PASSWORD)
        }
    }

    stage('Deploy App'){
        runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT)
    }

}

def imagePrune(containerName){
    try {
        sh "docker image prune -f"
        sh "docker stop $containerName"
    } catch(error){}
}

def imageBuild(containerName, tag){
    sh "docker build -t $containerName:$tag  -t $containerName --pull --no-cache ."
    echo "Image build complete"
}

def pushToImage(containerName, tag, dockerUser, dockerPassword){
    sh "docker login -u $dockerUser -p $dockerPassword"
    sh "docker tag $containerName:$tag $dockerUser/$containerName:$tag"
    sh "docker push $dockerUser/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPort){
    sh "docker pull $dockerHubUser/$containerName"
    sh "docker run -d --rm -p $httpPort:$httpPort --name $containerName $dockerHubUser/$containerName:$tag"
    echo "Application started on port: ${httpPort} (http)"
}
