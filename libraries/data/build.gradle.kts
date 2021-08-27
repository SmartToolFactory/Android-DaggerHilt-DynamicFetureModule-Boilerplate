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

    compileSdkVersion(AndroidVersion.COMPILE_SDK_VERSION)
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
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

    // Specifies one flavor dimension. Intend to use both reactive libraries as flavors as project develops

//    flavorDimensions("reactive")
//
//    productFlavors {
//
//        create("rxjava") {
//            dimension = "reactive"
//            applicationIdSuffix = ".rxjava"
//            versionNameSuffix  = "-rxjava"
//        }
//        create("coroutines") {
//            dimension = "reactive"
//            applicationIdSuffix =".coroutines"
//            versionNameSuffix = "-coroutines"
//        }
//    }

    sourceSets {

        val sharedTestDir =
            "${project(Modules.AndroidLibrary.TEST_UTILS).projectDir}/src/test-shared/java"

        getByName("test") {
//            java.srcDir(sharedTestDir)
            resources.srcDir(
                "${project(Modules.AndroidLibrary.TEST_UTILS).projectDir}" +
                    "/src/test/resources"
            )
        }

        getByName("androidTest") {
//            java.srcDir(sharedTestDir)
            resources.srcDir(
                "${project(Modules.AndroidLibrary.TEST_UTILS).projectDir}" +
                    "/src/test/resources"
            )
        }
    }

    configurations.all {
        resolutionStrategy {
            exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-debug")
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.KOTLIN)
    implementation(Libs.ANDROIDX_CORE_KTX)

    // Dagger
    implementation(Libs.DAGGER_HILT_ANDROID)
    kapt(Libs.DAGGER_HILT_COMPILER)

    // Room
    implementation(Libs.ROOM_RUNTIME)
    // For Kotlin use kapt instead of annotationProcessor
    kapt(Libs.ROOM_COMPILER)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(Libs.ROOM_KTX)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Retrofit
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON_CONVERTER)

    // Gson
    implementation(Libs.GSON)
    // Chucker
    implementation(Libs.CHUCKER_DEBUG)

    addUnitTestDependencies()
    testImplementation(project(Modules.AndroidLibrary.TEST_UTILS))

    addInstrumentationTestDependencies()
    androidTestImplementation(project(Modules.AndroidLibrary.TEST_UTILS))
    implementation(kotlin("reflect"))
}
