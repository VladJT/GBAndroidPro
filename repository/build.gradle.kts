plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "jt.projects.repository"
    compileSdk = Config.compile_sdk


    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdk = Config.min_sdk
        targetSdk = Config.target_sdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.java_version
        targetCompatibility = Config.java_version

    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget_version
    }
}

dependencies {
    // PROJECTS
    implementation(project(Modules.utils))
    implementation(project(Modules.model))

    // Room
    implementation(Room.runtime)
    kapt(Room.compiler)
    implementation(Room.room_ktx)

    // Retrofit 2
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)
    implementation(Retrofit.logging_interceptor)
    implementation(Retrofit.adapter_rxjava2)//RxJava2 для Retrofit
    implementation(Retrofit.adapter_coroutines)//КОРУТИНЫ для Retrofit
}