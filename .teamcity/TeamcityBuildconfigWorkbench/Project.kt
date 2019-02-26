package TeamcityBuildconfigWorkbench

import TeamcityBuildconfigWorkbench.buildTypes.*
import TeamcityBuildconfigWorkbench.vcsRoots.*
import TeamcityBuildconfigWorkbench.vcsRoots.TeamcityBuildconfigWorkbench_SshGitGogs22alxsTeamcityBuildconfigWorkbenchGitRefs
import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.Project
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.versionedSettings

object Project : Project({
    id("TeamcityBuildconfigWorkbench")
    parentId("_Root")
    name = "Teamcity Buildconfig Workbench"

    vcsRoot(TeamcityBuildconfigWorkbench_SshGitGogs22alxsTeamcityBuildconfigWorkbenchGitRefs)

    buildType(TeamcityBuildconfigWorkbench_Build)

    features {
        versionedSettings {
            id = "PROJECT_EXT_1"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = "${TeamcityBuildconfigWorkbench_SshGitGogs22alxsTeamcityBuildconfigWorkbenchGitRefs.id}"
            showChanges = true
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})
