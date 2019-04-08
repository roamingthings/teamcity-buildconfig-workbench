import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

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

version = "2018.2"

object TriggerMappings {

    /*
     * If this project is a subproject of ParentProject1Id add finishBuildTriggers that reference build steps from
     * two other projects (AnotherProject1Id, AnotherProject2Id)
     * If this project is a subproject of ParentProject2Id add a finishBuildTrigger that reference build steps from
     * one other project (AnotherProject3Id)
     * In any other case don't add any finishBuildTrigger at all
     *
     * /
     * +- AnotherProject1Id
     * |  |
     * |  \- AnotherProject1Id_triggerBuildType
     * |
     * +- AnotherProject2Id
     * |  |
     * |  \- AnotherProject2Id_triggerBuildType
     * |
     * +- AnotherProject3Id
     * |  |
     * |  \- AnotherProject3Id_triggerBuildType
     * |
     * +- ParentProject1Id
     * |  |
     * |  \- ParentProject1Id_ThisProjectId_Build (Trigger after AnotherProject1Id, AnotherProject2Id)
     * |
     * +- ParentProject2Id
     * |  |
     * |  \- ParentProject2Id_ThisProjectId_Build (Trigger after AnotherProject3Id)
     * |
     * +- ParentProject3Id
     *    |
     *    \- ParentProject3Id_ThisProjectId_Build (no finshBuildTrigger)
     */
    private val finishTriggerByParentId = mapOf(
            "ParentProject1Id" to listOf("AnotherProject1Id_triggerBuildType", "AnotherProject2Id_triggerBuildType"),
            "ParentProject2Id" to listOf("AnotherProject3Id_triggerBuildType")
    )

    fun triggerIdsForParentProjectId(parentProjectId: String): List<String>? {
        return finishTriggerByParentId[parentProjectId]
    }
}

project {

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        gradle {
            tasks = "clean build"
            buildFile = ""
            gradleWrapperPath = ""
        }
    }

    // Depending on the ID of the parent project add finishBuildTriggers as specified in the configuration Map
    val triggerIds = TriggerMappings.triggerIdsForParentProjectId(DslContext.parentProjectId.absoluteId)
    triggers {
        vcs {
            branchFilter = ""
        }
        if (triggerIds != null) {
            for (triggerId in triggerIds) {
                finishBuildTrigger {
                    buildType = triggerId
                }
            }
        }
    }
})
