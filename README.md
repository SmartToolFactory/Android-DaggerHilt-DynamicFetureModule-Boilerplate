# Gradle Kotlin DSL, ktLint, detekt, Git Hooks Sample

[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.3.72-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

Sample boiler plate code to implement Kotlin DSL, ktlint, detekt and Git Hooks to app on app, library and dynamic feature module step by step with each commit. Also added git hooks using script below in ```.git\hooks``` folder with a excutable **pre-commit.sh** to turn simple text to excutable call ```chmod a+x pre-commit```

```
#!/bin/sh


echo "Running static analysis..."


# Inspect code using KtLint, and Detekt

# Run KtLint only
#./gradlew app:ktlintCheck --daemon

# Format code using KtLint, then run Detekt and KtLint static analysis
./gradlew app:ktlintFormat app:detekt app:ktlintCheck --daemon

status=$?


if [ "$status" = 0 ] ; then

    echo "Static analysis found no problems."

    exit 0

else

    echo 1>&2 "Static analysis found violations it could not fix."

    exit 1

fi
```

which calls in order

1. ktLint to format code before commit
2. runs detekt for static code analysis
3. ktlintCheck to check code,

if after these tasks status is 0 then commit is successul, otherwise you need to check out console for links to your code and fix those errors.


Steps taken to migrate to Gradle with Kotlin

* Rename settings.gradle to settings.gradle.kts
* Rename project level build.gradle to build.gradle.kts
* Add plugins using the snippet below
```
plugins {
    id(Plugins.KTLINT) version PluginVersion.KTLINT_VERSION
    id(Plugins.DETEKT) version PluginVersion.DETEKT_VERSION
}
```

Then us the snippet below to add **ktlint** and **detekt** to each module used in project

```
subprojects {

    // KtLint
    apply(plugin = Plugins.KTLINT) // Version should be inherited from parent

    // Optionally configure plugin
    ktlint {
        debug.set(true)
    }

    // Detekt
    apply(plugin = Plugins.DETEKT)

    detekt {
        config = files("${project.rootDir}/config/detekt/detekt.yml")
        parallel = true

        reports {
            xml {
                enabled = true
                destination = file("${project.rootDir}/build/reports/detekt_report.xml")
            }
            html {
                enabled = true
                destination = file("${project.rootDir}/build/reports/detekt_report.html")
            }
            txt {
                enabled = true
                destination = file("${project.rootDir}/build/reports/detekt_report.txt")
            }
        }
    }
}
```
* Create a **buildSrc** folder and add it's build.gradle.kts with
```
plugins {
    `kotlin-dsl`
}
```
to turn it a dsl folder

Created [DependencyHandler](buildSrc/src/main/java/extension/DependencyHandlerExtension.kt) extension functions to not repeat adding same dependencies to different modules over and over again

For instance

```
fun DependencyHandler.addCoreModuleDependencies() {

    implementation(Deps.KOTLIN)
    implementation(Deps.ANDROIDX_CORE_KTX)

    // Support and Widgets
    implementation(Deps.APPCOMPAT)
    implementation(Deps.MATERIAL)
    implementation(Deps.CONSTRAINT_LAYOUT)
    implementation(Deps.RECYCLER_VIEW)
    implementation(Deps.VIEWPAGER2)

    // Lifecycle, LiveData, ViewModel
    implementation(Deps.ARCH_LIFECYCLE)

    // Navigation Components
    implementation(Deps.NAVIGATION_FRAGMENT)
    implementation(Deps.NAVIGATION_UI)
    implementation(Deps.NAVIGATION_RUNTIME)
    implementation(Deps.NAVIGATION_DYNAMIC)

    // Dagger
    implementation(Deps.DAGGER_HILT_ANDROID)
    kapt(Deps.DAGGER_HILT_COMPILER)

    // Room
    implementation(Deps.ROOM_RUNTIME)
    // For Kotlin use kapt instead of annotationProcessor
    kapt(Deps.ROOM_COMPILER)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(Deps.ROOM_KTX)
    // optional - RxJava support for Room
    implementation(Deps.ROOM_RXJAVA2)

    // RxJava
    implementation(Deps.RX_JAVA)
    // RxAndroid
    implementation(Deps.RX_ANDRIOD)

    // Coroutines
    implementation(Deps.COROUTINES_CORE)
    implementation(Deps.COROUTINES_ANDROID)

    // Retrofit
    implementation(Deps.RETROFIT)
    implementation(Deps.RETROFIT_GSON_CONVERTER)
    implementation(Deps.RETROFIT_RX_JAVA2_ADAPTER)
    // change base url runtime
    implementation(Deps.RETROFIT_URL_MANAGER)
    // Gson
    implementation(Deps.GSON)

}
```
