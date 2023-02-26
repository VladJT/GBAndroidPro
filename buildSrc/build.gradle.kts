repositories {
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

buildscript {
    dependencies {
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")
    }
}
//
//tasks {
//    test {
//        testLogging.showExceptions = true
//    }
//}
//
//tasks.withType<Test> {
//    useJUnitPlatform()
//}