package _Self

import _Self.buildTypes.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.awsConnection

object Project : Project({

    buildType(Build)

    features {
        awsConnection {
            id = "AmazonWebServicesAws_4"
            name = "Amazon Web Services (AWS)"
            regionName = "eu-west-1"
            credentialsType = static {
                accessKeyId = "AKIA5JH2VERVJFGDHSDZ"
                secretAccessKey = "credentialsJSON:5eb86222-6fe6-4757-99f0-d5b683d9dc2f"
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
