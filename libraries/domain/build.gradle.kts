import extension.addInstrumentationTestDependencies
import extension.addUnitTestDependencies

plugins {
    id(Plugins.ANDROID_LIBRARY_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_EXTENSIONS_PLUGIN)
    id(Plugins.KOTLIN_KAPT_PLUGIN)
    id(Plugins.DAGGER_HILT_PLUGIN)
}

android {

    compileSdk = AndroidVersion.COMPILE_SDK_VERSION
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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

//    sourceSets {
//
//        val sharedTestDir =
//            "${project(Modules.AndroidLibrary.TEST_UTILS).projectDir}/src/test-shared/java"
//
//        getByName("test") {
//            java.srcDir(sharedTestDir)
//        }
//
//        getByName("androidTest") {
//            java.srcDir(sharedTestDir)
//            resources.srcDir("${project(Modules.AndroidLibrary.TEST_UTILS).projectDir}/src/test/resources")
//        }
//    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Modules.AndroidLibrary.DATA))

    implementation(Libs.KOTLIN)
    implementation(Libs.ANDROIDX_CORE_KTX)

    // Dagger
    implementation(Libs.DAGGER_HILT_ANDROID)
    kapt(Libs.DAGGER_HILT_COMPILER)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    testImplementation(project(Modules.AndroidLibrary.TEST_UTILS))

    testImplementation(Libs.GSON)

    addUnitTestDependencies()
    addInstrumentationTestDependencies()
}
