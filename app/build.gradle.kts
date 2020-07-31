import extension.addAppModuleDependencies
import extension.addInstrumentationTestDependencies
import extension.addUnitTestDependencies

plugins {
    id(Plugins.ANDROID_APPLICATION_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_EXTENSIONS_PLUGIN)
    id(Plugins.KOTLIN_KAPT_PLUGIN)
    id(Plugins.DAGGER_HILT_PLUGIN)
}

android {

    compileSdkVersion(AndroidVersion.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = AndroidVersion.APPLICATION_ID
        minSdkVersion(AndroidVersion.MIN_SDK_VERSION)
        targetSdkVersion(AndroidVersion.TARGET_SDK_VERSION)
        versionCode = AndroidVersion.VERSION_CODE
        versionName = AndroidVersion.VERSION_NAME
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

    //    signingConfigs {
//        create(BuildType.RELEASE) {
//            keyAlias = getLocalProperty("signing.key.alias")
//            keyPassword = getLocalProperty("signing.key.password")
//            storeFile = file(getLocalProperty("signing.store.file"))
//            storePassword = getLocalProperty("signing.store.password")
//        }
//    }

    flavorDimensions("debug")
    productFlavors {
    }

    sourceSets {
    }

    dataBinding {
        isEnabled = true
    }

    dynamicFeatures = mutableSetOf(Modules.DynamicFM.DASHBOARD)
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(Modules.AndroidLibrary.CORE))

    addAppModuleDependencies()

    addUnitTestDependencies()
    addInstrumentationTestDependencies()
}
