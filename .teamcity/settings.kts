import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.sshExec
import jetbrains.buildServer.configs.kotlin.projectFeatures.slackConnection
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.11"

project {

    buildType(Build)

    features {
        slackConnection {
            id = "PROJECT_EXT_22"
            displayName = "Slack"
            botToken = "credentialsJSON:a7587364-a07b-443f-811e-e694c19e869a"
            clientId = "6045005489155.6045015317203"
            clientSecret = "credentialsJSON:34618669-c7e5-4214-84c0-5b5dc2e001cf"
            serviceMessageMaxNotificationsPerBuild = "-1"
        }
    }
}

object Build : BuildType({
    name = "Build"

    artifactRules = "**/*.jar"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        sshExec {
            id = "ssh_exec_runner"
            enabled = false
            commands = """
                pwd 
                ./script.bat
            """.trimIndent()
            targetUrl = "10.128.93.139"
            authMethod = uploadedKey {
                username = "Administrator"
                passphrase = "credentialsJSON:57da657d-67c5-4f44-a37d-71887366ca3b"
                key = "ollven_windows.pem"
            }
        }
        maven {
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
        notifications {
            notifierSettings = slackNotifier {
                connection = "PROJECT_EXT_22"
                sendTo = "#testing"
                messageFormat = simpleMessageFormat()
            }
            buildStarted = true
            buildFailedToStart = true
            buildFailed = true
            buildFinishedSuccessfully = true
            firstBuildErrorOccurs = true
            buildProbablyHanging = true
        }
        notifications {
            notifierSettings = emailNotifier {
                email = "olga.sventukh@jetbrains.com"
            }
            buildStarted = true
            buildFailedToStart = true
            buildFailed = true
            buildProbablyHanging = true
        }
    }

    cleanup {
        keepRule {
            id = "KEEP_RULE_8"
            keepAtLeast = builds(14)
            dataToKeep = historyAndStatistics {
                preserveArtifacts = all()
            }
        }
        keepRule {
            id = "KEEP_RULE_9"
            keepAtLeast = builds(1)
            dataToKeep = historyAndStatistics {
                preserveArtifacts = all()
            }
            applyPerEachBranch = true
        }
    }
})
