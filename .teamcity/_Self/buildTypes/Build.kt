package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildFeatures.merge
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.matrix
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Build : BuildType({
    name = "Build"

    artifactRules = "target => target"

    params {
        param("slack_connection_id", "PROJECT_EXT_17")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            id = "Maven2"
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
        script {
            id = "simpleRunner"
            enabled = false
            scriptContent = """
                echo "##teamcity[testStarted name='MyTest.test1']"
                echo "##teamcity[testFailed name='MyTest.test1' message='failure message' details='message and stack trace']"
                echo "##teamcity[testFinished name='MyTest.test1']"
            """.trimIndent()
        }
        script {
            id = "simpleRunner_1"
            enabled = false
            scriptContent = """
                echo "##teamcity[testSuiteStarted name='test_suite']"
                echo "##teamcity[testRetrySupport enabled='true']"
                echo "##teamcity[testStarted name='MyTest.test1']"
                echo "##teamcity[testFailed name='MyTest.test1']"
                echo "##teamcity[testFinished name='MyTest.test1' duration='0']"
                sleep 60
                echo "##teamcity[testStarted name='MyTest.test1' captureStandardOutput='true']"
                echo "##teamcity[testFinished name='MyTest.test1' duration='0']"
                sleep 60
                echo "##teamcity[testSuiteFinished name='test_suite']"
            """.trimIndent()
        }
        script {
            id = "simpleRunner_2"
            enabled = false
            scriptContent = "blablabla"
        }
        script {
            id = "simpleRunner_3"
            scriptContent = """
                msg2=":rotating_light: *run.reports.recap-81 (i-0334d67911e5ce76d)* running since 2024-08-08 16:31:07!"
                echo "##teamcity[notification notifier='slack' message='${'$'}msg2' sendTo='${'$'}sendTo' connectionId='%slack_connection_id%']"
            """.trimIndent()
        }
    }

    triggers {
        vcs {
            enabled = false
            branchFilter = "+pr:*"
        }
        finishBuildTrigger {
            enabled = false
            buildType = "BranchFilters_Build"
            branchFilter = "+pr:*"
        }
    }

    failureConditions {
        supportTestRetry = true
    }

    features {
        perfmon {
        }
        commitStatusPublisher {
            enabled = false
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = vcsRoot()
            }
        }
        matrix {
            enabled = false
            os = listOf(
                value("Linux"),
                value("Mac OS"),
                value("Windows")
            )
            param("arch", listOf(
                value("aarch64")
            ))
        }
        notifications {
            enabled = false
            notifierSettings = emailNotifier {
                email = "olga.sventukh@jetbrains.com"
            }
            branchFilter = "+pr:*"
            buildFailed = true
            buildFinishedSuccessfully = true
        }
        merge {
            enabled = false
            branchFilter = "+pr:*"
        }
        notifications {
            notifierSettings = slackNotifier {
                connection = "PROJECT_EXT_17"
                sendTo = "#testing"
                messageFormat = simpleMessageFormat()
            }
            buildStarted = true
            buildFailedToStart = true
            buildFailed = true
            buildFinishedSuccessfully = true
            firstBuildErrorOccurs = true
        }
        notifications {
            notifierSettings = slackNotifier {
                connection = "PROJECT_EXT_17"
                sendTo = "#testing-2"
                messageFormat = simpleMessageFormat()
            }
            buildStarted = true
            buildFailedToStart = true
            buildFailed = true
            buildFinishedSuccessfully = true
        }
    }
})
