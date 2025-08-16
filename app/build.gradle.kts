
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services") // ✅ Add this
    id("com.google.dagger.hilt.android") version "2.44" apply true
}
hilt {
    enableAggregatingTask = false
}

android {
    namespace = "com.app.klakmoum"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.klakmoum"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.core.ktx)
    implementation(libs.activity.compose)

    implementation(libs.gson)
    implementation(libs.navigation.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.runtime.saveable)
    implementation(libs.compose.ui)
    implementation(libs.material)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.icons)

    implementation(libs.coil.compose)

    implementation(libs.recyclerview)
    implementation(libs.constraintlayout)
    implementation(libs.appcompat)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.navigation.compose.old)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.lottie.compose)

    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    implementation(libs.barcode.scanning)

    implementation(libs.accompanist.permissions)

    implementation(libs.datastore.preferences)

    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.rxjava)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.otto)

}
