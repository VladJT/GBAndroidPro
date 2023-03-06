plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}



android {
    namespace = "jt.projects.model"
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
    // AndroidX
    implementation(Design.appcompat)

    implementation(Retrofit.converter_gson)
}