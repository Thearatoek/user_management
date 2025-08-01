import org.jetbrains.kotlin.gradle.internal.kapt.incremental.UnknownSnapshot

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("com.android.library") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false // adjust as needed
    id("com.google.gms.google-services") version "4.4.3" apply false
}
