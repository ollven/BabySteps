package _Self

import _Self.buildTypes.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.slackConnection

object Project : Project({

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
    }

    cleanup {
        baseRule {
            option("disableCleanupPolicies", true)
        }
    }
})
