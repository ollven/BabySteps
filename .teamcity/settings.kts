import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.amazonEC2CloudProfile
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.sshExec
import jetbrains.buildServer.configs.kotlin.matrix
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
        feature {
            id = "PROJECT_EXT_50"
            type = "sonar-qube"
            param("useToken", "true")
            param("name", "sonar")
            param("id", "d84ee010-c3e1-488f-9f29-60a9af1b11ad")
            param("url", "http://localhost:9000")
            param("token", "scrambled:c3FhXzc1ZDE2YmI4YTEyMjk2ODk5ZTkwMjJiYTAzMmQ1MjBiMWMwOGUwOWI=")
        }
        amazonEC2CloudProfile {
            id = "amazon-3"
            name = "Tests Profile"
            terminateIdleMinutes = 30
            region = AmazonEC2CloudProfile.Regions.EU_WEST_DUBLIN
            authType = accessKey {
                keyId = "credentialsJSON:253da1b9-5eba-438c-9825-64f7503af886"
                secretKey = "credentialsJSON:5eb86222-6fe6-4757-99f0-d5b683d9dc2f"
            }
        }
    }

    cleanup {
        baseRule {
            option("disableCleanupPolicies", true)
        }
    }
}

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
        perfmon {
        }
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
