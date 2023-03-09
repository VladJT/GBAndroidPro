import org.gradle.api.JavaVersion
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

object Config {
    const val application_id = "jt.projects.gbandroidpro"
    const val namespace = "jt.projects.gbandroidpro"
    const val compile_sdk = 33
    const val min_sdk = 29
    const val target_sdk = 33
    val java_version = JavaVersion.VERSION_1_8
    const val jvmTarget_version = "1.8"
    const val isMinifyEnabled = false
}

object Releases {
    const val version_code = 1
    const val version_name = "1.1"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"

    //Features
    const val historyScreen = ":historyScreen"
}

object Versions {
    //Design
    const val appcompat = "1.5.1"
    const val material = "1.7.0"

    //Kotlin
    const val core = "1.8.0"
    const val stdlib = "1.5.21"
    const val coroutinesCore = "1.6.4"
    const val coroutinesAndroid = "1.6.4"

    //Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val interceptor = "4.9.2"
    const val adapterCoroutines = "0.9.2"
    const val adapterRxjava2 = "1.0.0"

    //Koin
    const val koin = "3.2.0"

    //Coil
    const val coil = "2.2.2"

    //Room
    const val roomKtx = "2.5.0"
    const val runtime = "2.5.0"
    const val roomCompiler = "2.5.0"

    //Test
    const val jUnit = "4.13.2"
    const val runner = "1.2.0"
    const val espressoCore = "3.5.1"
    const val extjUnit = "1.1.5"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val adapter_rxjava2 =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.adapterRxjava2}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"

    //Koin для поддержки Android (Scope,ViewModel ...)
    const val viewmodel = "io.insert-koin:koin-android:${Versions.koin}"

    //Для совместимости с Java
    const val compat = "io.insert-koin:koin-android-compat:${Versions.koin}"

    // При написании юнит-тестов можно создавать модули в рантайме и вызывать функцию startKoin  внутри тестов
    const val test = "io.insert-koin:koin-test:${Versions.koin}"
    // Needed JUnit version
    const val junit4Test =  "io.insert-koin:koin-test-junit4:${Versions.koin}"
    const val junit5Test =  "io.insert-koin:koin-test-junit5:${Versions.koin}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val extjunit = "androidx.test.ext:junit:${Versions.extjUnit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:4.12.0"
    const val compiler = "com.github.bumptech.glide:compiler:4.12.0"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:2.71828"
}

object RxJava {
    const val rxandroid = "io.reactivex.rxjava3:rxandroid:3.0.0"
    const val rxjava = "io.reactivex.rxjava3:rxjava:3.0.0"
    const val adapter_rxjava3 = "com.squareup.retrofit2:adapter-rxjava3:2.9.0"
}

object Dagger2 {
    const val dagger = "com.google.dagger:dagger:2.44"
    const val dagger_android = "com.google.dagger:dagger-android:2.44"
    const val dagger_android_support = "com.google.dagger:dagger-android-support:2.44"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:2.44"
    const val dagger_android_processor = "com.google.dagger:dagger-android-processor:2.44"
}