plugins {
    id("com.android.library") version "8.7.3"
    id("org.jetbrains.kotlin.android") version "2.2.21"
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21"
    id("com.google.devtools.ksp") version "2.2.21-2.0.4"
    id("com.google.dagger.hilt.android") version "2.56.2"
    id("maven-publish")
}

android {
    namespace = "jr.brian.esdecompanionlib"
    compileSdk = 36

    defaultConfig {
        minSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx.v1170)
    implementation(libs.androidx.lifecycle.runtime.ktx.v294)
    implementation(libs.androidx.lifecycle.viewmodel.compose.v294)
    implementation(libs.androidx.lifecycle.runtime.compose.v294)

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.09.00")
    implementation(composeBom)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose.v297)

    // Image Loading - Coil
    implementation(libs.coil.kt.coil.compose)
    implementation(libs.coil.kt.coil.gif)
    implementation(libs.coil.kt.coil.svg)

    // Media3 for video playback
    implementation(libs.androidx.media3.exoplayer.v191)
    implementation(libs.androidx.media3.ui.v191)

    // DataStore for preferences
    implementation(libs.androidx.datastore.preferences.v120)

    // DocumentFile for file access
    implementation(libs.androidx.documentfile.v110)

    // Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-compiler:2.56.2")
    implementation(libs.androidx.hilt.navigation.compose.v130)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Networking - Retrofit & OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.gson)

    // API & Data
    implementation(libs.igdb.api) {
        exclude(group = "com.google.protobuf")
    }
    implementation(libs.protobuf.java)

    // Audio Processing
    implementation(libs.tarsos.dsp)

    // UI Libraries
    implementation(libs.androidsvg)
    implementation(libs.shimmer)
    implementation(libs.reorderable)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v130)
    androidTestImplementation(libs.androidx.espresso.core.v370)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.brianjr03"
                artifactId = "esde-companion-lib"
                version = "0.4.2"
            }
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("com.google.protobuf:protobuf-java:4.29.3")
    }
}

