plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.serialization)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.protobuf)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.valance.medicine"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.valance.medicine"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.kotlinx.coroutines.test)

    //firebase
    implementation(libs.firebase.crashlytics)
    implementation (libs.firebase.firestore.ktx)

    //Nav
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.fragment.ktx)

    //lottie
    implementation (libs.lottie)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //protoDB
    implementation (libs.androidx.datastore.preferences)

    //logger
    implementation (libs.log4j.core)
    implementation (libs.log4j.api)

    //hilt
    implementation(libs.hilt.android.v244)
    kapt(libs.hilt.android.compiler)


}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobufJavalite.get()}"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }

        }
    }
}


