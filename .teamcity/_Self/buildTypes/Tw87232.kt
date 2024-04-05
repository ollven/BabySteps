package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.matrix
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Tw87232 : Template({
    name = "TW-87232"

    artifactRules = "**/*.jar"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            id = "RUNNER_1"
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            jdkHome = "%env.JDK_11%"
            coverageEngine = jacoco {
                classLocations = "+:build/main/**/*"
                jacocoVersion = "%teamcity.tool.jacoco.0.8.2%"
            }
            param("teamcity.coverage.idea.includePatterns", "de.ollven.*")
        }
    }

    triggers {
        vcs {
            id = "TRIGGER_1"
        }
    }

    features {
        perfmon {
            id = "BUILD_EXT_1"
        }
        matrix {
            id = "BUILD_EXT_4"
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
})
