plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("de.mannodermaus.android-junit5") version "1.8.2.1"
}



android {
    compileSdk = Config.compile_sdk
    namespace = Config.namespace

    buildToolsVersion = "30.0.2"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

//    sourceSets {
//        this.getByName("androidTest") {
//            this.java.srcDirs("src/sharedTestData/java")
//        }
//
//        this.getByName("test") {
//            this.java.srcDirs("src/sharedTestData/java")
//        }
//    }


    buildFeatures {
        viewBinding = true
    }

    lint {
        // Turns off checks for the issue IDs you specify.
        disable += "Instantiatable"
    }

    defaultConfig {
        applicationId = Config.application_id
        minSdk = Config.min_sdk
        targetSdk = Config.target_sdk
        versionCode = Releases.version_code
        versionName = Releases.version_name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    flavorDimensions += listOf("default")
    productFlavors {
        create("fake") {
            dimension = "default"
            applicationIdSuffix = ".fake"
            versionNameSuffix = "-fake"
            buildConfigField("String", "TYPE", "\"FAKE\"")
        }
        create("real") {
            dimension = "default"
            applicationIdSuffix = ".real"
            versionNameSuffix = "-real"
            buildConfigField("String", "TYPE", "\"REAL\"")
        }
    }

}

dependencies {
    // PROJECTS
    implementation(project(Modules.core))
    implementation(project(Modules.model))
    implementation(project(Modules.utils))
    implementation(project(Modules.repository))
    implementation(project(Modules.tests))


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
    testImplementation(Koin.junit5Test)

    // Room
    implementation(Room.runtime)
    kapt(Room.compiler)
    implementation(Room.room_ktx)


    // Test
    // testImplementation(TestImpl.junit)
    //androidTestImplementation(TestImpl.runner)
    //androidTestImplementation(TestImpl.espresso)
    // androidTestImplementation(TestImpl.extjunit)
    //testImplementation("pl.pragmatists:JUnitParams:1.1.1")

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

    // MOCKITO
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
    testImplementation("org.junit.platform:junit-platform-surefire-provider:1.3.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")// for test coroutines

    // for test liveData
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    //Robolectric
    testImplementation("org.robolectric:robolectric:4.5.1")
    implementation("androidx.test:core:1.5.0")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    implementation("androidx.test:runner:1.5.2")
    implementation("androidx.test.ext:truth:1.5.0")

    //UI Automator
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")

    // test fragments
    debugImplementation("androidx.fragment:fragment-testing:1.5.5")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

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