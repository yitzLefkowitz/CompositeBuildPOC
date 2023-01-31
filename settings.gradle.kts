pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    fun ExtraPropertiesExtension.getOrElse(key: String, defaultProvider: () -> String): String {
        return runCatching { get(key)!!.toString() }.getOrElse { defaultProvider() }
    }

    val artifactoryRepoName: String = extra.getOrElse("artifactoryRepoName") {
        System.getenv("ANDROID_ARTIFACTORY_REPO_NAME")
    }

    val artifactoryUrl: String = extra.getOrElse("artifactoryUrl") {
        System.getenv("ANDROID_ARTIFACTORY_URL")
    }

    val artifactoryHeaderName: String = extra.getOrElse("artifactoryHeaderName") {
        System.getenv("ANDROID_ARTIFACTORY_HEADER_NAME")
    }

    val artifactoryAndroidApiKey: String = extra.getOrElse("artifactoryAndroidApiKey") {
        System.getenv("ARTIFACTORY_ANDROID_API_KEY")
    }

    repositories {
        google()
        mavenCentral()
        maven {
            name = artifactoryRepoName
            url = uri(artifactoryUrl)
            credentials(HttpHeaderCredentials::class.java) {
                name = artifactoryHeaderName
                value = artifactoryAndroidApiKey
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}
rootProject.name = "Composite Build POC"
include(":app")

