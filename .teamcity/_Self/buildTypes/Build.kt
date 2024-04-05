package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.sshExec
import jetbrains.buildServer.configs.kotlin.matrix
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Build : BuildType({
    name = "Newname"

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
            jdkHome = "%env.JDK_11%"
            coverageEngine = jacoco {
                classLocations = "+:build/main/**/*"
                jacocoVersion = "%teamcity.tool.jacoco.0.8.2%"
            }
            param("teamcity.coverage.idea.includePatterns", "de.ollven.*")
        }
        step {
            name = "sonar"
            id = "sonar"
            type = "sonar-plugin"
            enabled = false
            param("target.jdk.home", "%env.JDK_17_0%")
            param("teamcity.tool.sonarquberunner", "%teamcity.tool.sonar-qube-scanner.4.2.0.1873-scanner%")
            param("additionalParameters", "-X")
            param("sonarServer", "d84ee010-c3e1-488f-9f29-60a9af1b11ad")
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        notifications {
            enabled = false
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
            enabled = false
            notifierSettings = emailNotifier {
                email = "olga.sventukh@jetbrains.com"
            }
            buildStarted = true
            buildFailedToStart = true
            buildFailed = true
            buildProbablyHanging = true
        }
        perfmon {
        }
        matrix {
            os = listOf(
                value("Mac OS"),
                value("Linux")
            )
            param("newParameter", listOf(
                value("Sn"),
                value("SomeKindOfLongName"),
                value("hedlwehkfhkhferkjfhrehfrekhfkreherkfhklerfhkewrhfgjlkehkerhkerhkferhkferhkfhrekfhrekfhreklhfkerfhkerhfkerhfkhjgjlljljhgjgjgjgjhghjhgjhgjhgjgjkgjhgjhgjgjgjgjghjgjgjgjhgjgjgjgjbjhbbjbjhbjgjhbgjkhgjhgkjgjkghjgjhgjkhljhlkjhjlkhjlhljhjkgkhghjhjgjlgljkfldkjfdkfdlkjkvfgd"),
                value("Value2"),
                value("Value3"),
                value("Value4"),
                value("Value5")
            ))
        }
    }

    cleanup {
        keepRule {
            disabled = true
            id = "KEEP_RULE_8"
            keepAtLeast = days(14)
            dataToKeep = historyAndStatistics {
                preserveArtifacts = all()
            }
        }
        keepRule {
            disabled = true
            id = "KEEP_RULE_9"
            keepAtLeast = builds(1)
            dataToKeep = historyAndStatistics {
                preserveArtifacts = all()
            }
            applyPerEachBranch = true
        }
    }
})
