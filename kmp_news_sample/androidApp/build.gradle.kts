plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    val lifecycle_version = "2.4.0-rc01"
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
}

android {
    compileSdkVersion(34)
    namespace="com.azharkova.newsapp.android"
    defaultConfig {
        applicationId = "com.azharkova.newsapp.android"
        minSdkVersion(24)
        targetSdkVersion(34)
        versionCode = 1
        versionName = "1.0"
        namespace = "com.azharkova.newsapp.android"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}