package _Self

import _Self.buildTypes.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.awsConnection

object Project : Project({

    buildType(Build)

    params {
        param("teamcity.ui.settings.readOnly", "true")
    }

    features {
        awsConnection {
            id = "AmazonWebServicesAws_4"
            name = "Amazon Web Services (AWS)"
            regionName = "eu-west-1"
            credentialsType = static {
                accessKeyId = "AKIA5JH2VERVJFGDHSDZ"
                secretAccessKey = "zxx268812497aef1753dee6f463183d6865f68bfe4a4fa33e5650da1a642154a6060df8f6b68cf03140775d03cbe80d301b"
                stsEndpoint = "https://sts.eu-west-1.amazonaws.com"
            }
            allowInBuilds = false
        }
    }

    cleanup {
        keepRule {
            id = "KEEP_RULE_3"
            keepAtLeast = allBuilds()
            applyToBuilds {
                inBranches {
                    branchState = active()
                }
                inPersonalBuilds = onlyPersonal()
            }
            dataToKeep = historyAndStatistics {
                preserveArtifacts = all()
            }
            applyPerEachBranch = true
            preserveArtifactsDependencies = true
        }
    }
})
