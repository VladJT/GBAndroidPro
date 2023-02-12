plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}



android {
    compileSdk = Config.compile_sdk
    namespace = Config.namespace

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = Config.application_id
        minSdk = Config.min_sdk
        targetSdk = Config.target_sdk
        versionCode = Releases.version_code
        versionName = Releases.version_name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
//            }
//        }
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    implementation(project(Modules.core))
    implementation(project(Modules.model))
    implementation(project(Modules.utils))
    implementation(project(Modules.repository))


    // AndroidX
    implementation(Design.appcompat)

    // Design
    implementation(Design.material)

    // Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    // КОРУТИНЫ
    implementation(Kotlin.coroutines_core)
    implementation(Kotlin.coroutines_android)

    // Koin for Android
    implementation(Koin.core)
    implementation(Koin.viewmodel)
    implementation(Koin.compat)
    testImplementation(Koin.test)

    // Room
    implementation(Room.runtime)
    kapt(Room.compiler)
    implementation(Room.room_ktx)

    // Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.runner)
    androidTestImplementation(TestImpl.espresso)
    androidTestImplementation(TestImpl.extjunit)
    // implementation(fileTree(dir: 'libs', include: ['*.jar'])

    // OTHERS
    //implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // RXJAVA
    implementation(RxJava.rxjava)
    implementation(RxJava.rxandroid)
    implementation(RxJava.adapter_rxjava3)

    // DAGGER 2
    implementation(Dagger2.dagger)
    implementation(Dagger2.dagger_android)
    implementation(Dagger2.dagger_android_support)
    kapt(Dagger2.dagger_compiler)
    kapt(Dagger2.dagger_android_processor)

    //Picasso
    implementation(Picasso.picasso)

    //Glide
    implementation(Glide.glide)
    kapt(Glide.compiler)
}