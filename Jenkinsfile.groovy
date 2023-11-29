//file:noinspection HardCodedStringLiteral


final projectName = ""
final repoUrl = env.trinextGitHubUrl + projectName
final currentBranch = env.BRANCH_NAME
final newDockerImageTag = env.gitHubImageRegistryDomain + "/" + env.triNextGitHubOrgName + "/" + projectName + ":" + currentBranch
final teamsWebhookUrl = ""
final pathToDockerFiles = "./docker"

pipeline {
    agent any
    options {
        // This is required if you want to clean before build
        skipDefaultCheckout(true)
    }
    environment {
        tonysPAT = credentials('tonysPAT')
        newDockerImageTag = "$newDockerImageTag"
        projectName = "$projectName"
    }
    stages {
        stage('Prepare') {
            steps {
                cleanWorkspace()
            }
        }
        stage('Test') {
            steps {
                echo 'Testing'
                cleanWorkspace()
                runGradleBuildAndPublishResults()
                echo 'checking and publishing Test Coverage'
                jacocoSetup()
                generateAndPublishJavadoc()
            }
        }
        stage('image Management') {
            steps {
                imageManagement()
            }
        }
        stage('Deploy and Test Container and Publish new Image if successful') {
            when {
                expression {
                    isPR()
                }
                anyOf {
                    branch stagingBranch
                    branch mainBranch
                    branch devBranch
                }
            }
            steps {
                deploy(pathToDockerFiles)
//                script {
//                    sh "newman run postman/reachable.postman_collection.json -e postman/" + currentBranch + ".postman_environment.json --bail"
//                }
            }
            post {
                success {
                    script {
                        publishNewDockerImage()
                        office365ConnectorSend webhookUrl: teamsWebhookUrl,
                                message: 'Application has been deployed to ' + currentBranch,
                                status: 'Success'
                    }
                }
                failure {
                    script {
                        rollback(pathToDockerFiles)
                        office365ConnectorSend webhookUrl: teamsWebhookUrl,
                                message: 'Failed deploying Application to ' + currentBranch + '.\n Successfully rolled back to previous version.',
                                status: 'Failure'
                    }
                }
            }
        }
    }
    post {
        success {
            setBuildStatus(repoUrl, "kannst mergen", "SUCCESS")
        }
        failure {
            setBuildStatus(repoUrl, "Sach ma hackst? Das willst du mergen? Nicht mit mir!", "FAILURE")
            office365ConnectorSend webhookUrl: teamsWebhookUrl,
                    message: 'Failed Pipeline for ' + currentBranch + '.',
                    status: 'Failure'
        }
    }
}