package extension

import Compose
import Libs
import TestDeps
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Adds required dependencies to app module
 */
fun DependencyHandler.addAppModuleDependencies() {

    implementation(Libs.KOTLIN)
    implementation(Libs.ANDROIDX_CORE_KTX)

    // Support and Widgets
    implementation(Libs.APPCOMPAT)
    implementation(Libs.MATERIAL)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.RECYCLER_VIEW)
    implementation(Libs.VIEWPAGER2)
    implementation(Libs.SWIPE_REFRESH_LAYOUT)

    // Views, Animations
    implementation(Libs.LOTTIE)

    // Lifecycle, LiveData, ViewModel
    implementation(Libs.LIFECYCLE_LIVEDATA_KTX)
    implementation(Libs.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Libs.LIFECYCLE_EXTENSIONS)

    // Navigation Components
    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI)
    implementation(Libs.NAVIGATION_RUNTIME)
    implementation(Libs.NAVIGATION_DYNAMIC)

    // Dagger Hilt
    implementation(Libs.DAGGER_HILT_ANDROID)
    kapt(Libs.DAGGER_HILT_COMPILER)


    // RxJava
    implementation(Libs.RX_JAVA3)
    // RxAndroid
    implementation(Libs.RX_JAVA3_ANDROID)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Leak Canary
    debugImplementation(Libs.LEAK_CANARY)

    // Room
    implementation(Libs.ROOM_RUNTIME)
    // For Kotlin use kapt instead of annotationProcessor
    kapt(Libs.ROOM_COMPILER)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(Libs.ROOM_KTX)
    // optional - RxJava support for Room
    implementation(Libs.ROOM_RXJAVA3)

    // Retrofit
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON_CONVERTER)
    implementation(Libs.RETROFIT_RX_JAVA3_ADAPTER)
    // change base url runtime
//    implementation(Deps.RETROFIT_URL_MANAGER)
    // Gson
    implementation(Libs.GSON)
    implementation(Libs.CHUCKER_DEBUG)

    // Glide
    implementation(Libs.GLIDE)
    kapt(Libs.GLIDE_COMPILER)

    // Compose
    addComposeDependencies()
}

/**
 * Adds dependencies to core module
 */
fun DependencyHandler.addCoreModuleDependencies() {
    implementation(Libs.KOTLIN)
    implementation(Libs.ANDROIDX_CORE_KTX)

    // Support and Widgets
    implementation(Libs.APPCOMPAT)
    implementation(Libs.MATERIAL)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.RECYCLER_VIEW)
    implementation(Libs.VIEWPAGER2)
    implementation(Libs.SWIPE_REFRESH_LAYOUT)

    // Lifecycle, LiveData, ViewModel
    implementation(Libs.LIFECYCLE_LIVEDATA_KTX)
    implementation(Libs.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Libs.LIFECYCLE_EXTENSIONS)

    // Navigation Components
    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI)
    implementation(Libs.NAVIGATION_RUNTIME)
    implementation(Libs.NAVIGATION_DYNAMIC)

    // Dagger
    implementation(Libs.DAGGER_HILT_ANDROID)
    kapt(Libs.DAGGER_HILT_COMPILER)

    // RxJava
    implementation(Libs.RX_JAVA3)
    // RxAndroid
    implementation(Libs.RX_JAVA3_ANDROID)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)
}

/**
 * Adds core dependencies such as kotlin, appcompat, navigation and dagger-hilt to Dynamic
 * Feature modules.
 *
 */
fun DependencyHandler.addBaseDynamicFeatureModuleDependencies() {
    implementation(Libs.KOTLIN)
    implementation(Libs.ANDROIDX_CORE_KTX)

    // Lifecycle, LiveData, ViewModel
    implementation(Libs.LIFECYCLE_LIVEDATA_KTX)
    implementation(Libs.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Libs.LIFECYCLE_EXTENSIONS)

    // Navigation Components
    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI)
    implementation(Libs.NAVIGATION_RUNTIME)
    implementation(Libs.NAVIGATION_DYNAMIC)

    // Dagger Hilt
    implementation(Libs.DAGGER_HILT_ANDROID)
    kapt(Libs.DAGGER_HILT_COMPILER)

    // RxJava
    implementation(Libs.RX_JAVA3)
    // RxAndroid
    implementation(Libs.RX_JAVA3_ANDROID)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Compose
    addComposeDependencies()
}

fun DependencyHandler.addComposeDependencies() {
    implementation(Compose.FOUNDATION)
    implementation(Compose.LAYOUT)
    implementation(Compose.UI)
    implementation(Compose.UI_UTIL)
    implementation(Compose.RUNTIME)
    implementation(Compose.MATERIAL)
    implementation(Compose.ANIMATION)
    implementation(Compose.TOOLING)
    implementation(Compose.ICONS_EXTENDED)
    implementation(Compose.Activity.activityCompose)
//    implementation(Compose.Lifecycle.viewModelCompose)
//    implementation(Compose.Navigation.navigationCompose)
    implementation(Compose.Coil.coilCompose)
}

/**
 * Adds Unit test dependencies
 */
fun DependencyHandler.addUnitTestDependencies() {

    // (Required) Writing and executing Unit Tests on the JUnit Platform
    testImplementation(TestDeps.JUNIT5_API)
    testRuntimeOnly(TestDeps.JUNIT5_ENGINE)

    // (Optional) If you need "Parameterized Tests"
    testImplementation(TestDeps.JUNIT5_PARAMS)

    testImplementation(TestDeps.ANDROIDX_CORE_TESTING)
    testImplementation(TestDeps.ROBOLECTRIC)

    // AndroidX Test - JVM testing
    testImplementation(TestDeps.ANDROIDX_TEST_CORE_KTX)
//    testImplementation(TestDeps.ANDROIDX_JUNIT)

    // Coroutines Test
    testImplementation(TestDeps.COROUTINES_TEST)

    // MockWebServer
    testImplementation(TestDeps.MOCK_WEB_SERVER)

    // Gson
    testImplementation(TestDeps.GSON)

    // MockK
    testImplementation(TestDeps.MOCK_K)
    // Truth
    testImplementation(TestDeps.TRUTH)
}

fun DependencyHandler.addInstrumentationTestDependencies() {

    // AndroidX Test - Instrumented testing
    androidTestImplementation(TestDeps.ANDROIDX_JUNIT)
    androidTestImplementation(TestDeps.ANDROIDX_CORE_TESTING)

    // Espresso
    androidTestImplementation(TestDeps.ANDROIDX_ESPRESSO)

    // Testing Navigation
    androidTestImplementation(TestDeps.NAVIGATION_TEST)

    // Coroutines Test
    androidTestImplementation(TestDeps.COROUTINES_TEST)

    // MockWebServer
    androidTestImplementation(TestDeps.MOCK_WEB_SERVER)
    // Gson
    androidTestImplementation(TestDeps.GSON)

    // MockK
    androidTestImplementation(TestDeps.MOCK_K)
    // Truth
    androidTestImplementation(TestDeps.TRUTH)
}

/*
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL
 * syntax in module\build.gradle.kts files.
 */
@Suppress("detekt.UnusedPrivateMember")
private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
private fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

private fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

private fun DependencyHandler.project(
    path: String,
    configuration: String? = null
): ProjectDependency {
    val notation = if (configuration != null) {
        mapOf("path" to path, "configuration" to configuration)
    } else {
        mapOf("path" to path)
    }

    return uncheckedCast(project(notation))
}

@Suppress("unchecked_cast", "nothing_to_inline", "detekt.UnsafeCast")
private inline fun <T> uncheckedCast(obj: Any?): T = obj as T