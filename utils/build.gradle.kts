plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "jt.projects.utils"
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

    // Design
    implementation(Design.material)

    // Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    // Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
    androidTestImplementation(TestImpl.extjunit)

    // Koin for Android
    implementation(Koin.core)
    implementation(Koin.viewmodel)
    implementation(Koin.compat)
    testImplementation(Koin.test)

    // RXJAVA
    implementation(RxJava.rxjava)
    implementation(RxJava.rxandroid)
    implementation(RxJava.adapter_rxjava3)

    implementation(Retrofit.converter_gson)

    //Coil
    implementation(Coil.coil)
}