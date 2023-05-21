import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")

}

android {
    namespace = "com.abdelhalim.boilrbite"
    compileSdk = 33

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


}

dependencies {
    implementation("androidx.recyclerview:recyclerview:1.3.0")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.abdomi7"
            artifactId = "BoilrBite"
            version = "1.0.6"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        mavenLocal()
    }
}