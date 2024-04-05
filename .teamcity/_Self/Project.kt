package _Self

import _Self.buildTypes.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.amazonEC2CloudProfile
import jetbrains.buildServer.configs.kotlin.projectFeatures.slackConnection

object Project : Project({

    buildType(Build)

    template(Tw87232)

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
})
