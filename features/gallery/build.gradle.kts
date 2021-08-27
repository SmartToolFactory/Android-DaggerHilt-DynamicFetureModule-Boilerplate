import extension.addBaseDynamicFeatureModuleDependencies
import extension.addInstrumentationTestDependencies
import extension.addUnitTestDependencies

plugins {
    id(Plugins.ANDROID_DYNAMIC_FEATURE_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_EXTENSIONS_PLUGIN)
    id(Plugins.KOTLIN_KAPT_PLUGIN)
    id(Plugins.DAGGER_HILT_PLUGIN)
    id(Plugins.NAVIGATION_SAFE_ARGS)
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

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.COMPOSE_VERSION
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Modules.APP))
    implementation(project(Modules.AndroidLibrary.CORE))
    implementation(project(Modules.AndroidLibrary.DOMAIN))

    addBaseDynamicFeatureModuleDependencies()

    // Support and Widgets
    implementation(Libs.APPCOMPAT)
    implementation(Libs.MATERIAL)
    implementation(Libs.CONSTRAINT_LAYOUT)

    // Lifecycle, LiveData, ViewModel
    implementation(Libs.LIFECYCLE_EXTENSIONS)

    addUnitTestDependencies()

    addInstrumentationTestDependencies()
}
