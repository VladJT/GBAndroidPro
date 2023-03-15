plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}



android {
    namespace = "jt.projects.tests"
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
    implementation(project(Modules.model))

    // JUNIT 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    // (Optional) If you also have JUnit 4-based tests
    implementation("junit:junit:4.13.2")

    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")

    // ESPRESSO
    androidTestImplementation(TestImpl.espresso)
    implementation("androidx.test.espresso:espresso-intents:3.5.1")
    implementation("androidx.test.espresso:espresso-contrib:3.5.1")//for rec view

}