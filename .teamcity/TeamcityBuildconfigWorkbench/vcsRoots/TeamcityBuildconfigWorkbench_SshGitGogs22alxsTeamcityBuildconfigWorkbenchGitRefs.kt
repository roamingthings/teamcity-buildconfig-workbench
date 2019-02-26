package TeamcityBuildconfigWorkbench.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

object TeamcityBuildconfigWorkbench_SshGitGogs22alxsTeamcityBuildconfigWorkbenchGitRefs : GitVcsRoot({
    uuid = "eaf938e7-0f5e-4e11-af3e-9d5cbe2a4140"
    name = "ssh://git@gogs:22/alxs/teamcity-buildconfig-workbench.git#refs/heads/master"
    url = "ssh://git@gogs:22/alxs/teamcity-buildconfig-workbench.git"
    authMethod = uploadedKey {
        userName = "git"
        uploadedKey = "teamcity_agent"
    }
})
