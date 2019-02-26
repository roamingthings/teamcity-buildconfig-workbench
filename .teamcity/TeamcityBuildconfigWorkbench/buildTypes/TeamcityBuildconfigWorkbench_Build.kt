package TeamcityBuildconfigWorkbench.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

object TeamcityBuildconfigWorkbench_Build : BuildType({
    name = "Build"

    vcs {
        root(TeamcityBuildconfigWorkbench.vcsRoots.TeamcityBuildconfigWorkbench_SshGitGogs22alxsTeamcityBuildconfigWorkbenchGitRefs)
    }

    steps {
        gradle {
            tasks = "clean build"
            buildFile = ""
            gradleWrapperPath = ""
        }
    }

    triggers {
        vcs {
        }
    }
})
