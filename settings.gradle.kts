
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google() // ✅ Firebase artifacts are hosted here
        mavenCentral()
    }
}

rootProject.name = "kla kmoum"
include(":app")
