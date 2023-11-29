//file:noinspection HardCodedStringLiteral
final projectName = "framework-util"
final repoUrl = env.trinextGitHubUrl + projectName

pipeline {
    agent any
    options {
        // This is required if you want to clean before build
        skipDefaultCheckout(true)
    }
    environment {
        tonysPAT = credentials('tonysPAT')
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