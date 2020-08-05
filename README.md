# Boilerplate for creating project with MVVM, Dagger Hilt, Dynamic Feature Modules

[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.3.72-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)


This is a boilerplate code to use when ceating a project not to repeat same process over again when starting a new project. Folders are created, dependies and libraries are set up
to create a requirements when starting a new project.

* Gradle Kotlin DSL is used for setting up gradle files with ```buildSrc``` folder and extensions.
* KtLint, Detekt, and Git Hooks is used for checking, and formatting code and validating code before commits.
* Dagger Hilt, Dynamic Feature Modules with Navigation Components, ViewModel, Retrofit, Room, RxJava, Coroutines libraries adn dependencies are set up.
* ```features``` and ```libraries``` folders are used to include android libraries and dynamic feature modules
* In core module dagger hilt dependencies and ```@EntryPoint``` is created

###Note
Change ```applicationId```in ```Version.AndroidVersion``` first

```
object AndroidVersion {
    const val APPLICATION_ID = "com...."
}
```

## Gradle Kotlin DSL, ktLint, detekt And Git Hooks Integration
Kotlin DSL, ktlint, detekt and Git Hooks to app on app, library and dynamic feature module step by step with each commit.
Also added git hooks using script below in ```.git\hooks``` folder with a excutable **pre-commit.sh** to turn simple text to excutable call ```chmod a+x pre-commit```

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

    ...

}
```

## Modules

*Module heierarchy*
```
     --> gallery module(dynamic featature module)
     |    |
     |  app module <-<----
     |    |          |   |
     -core module    |   |
                     |   |
      domain module<------
           |             |
      data module<--------
                         |
      test-utils module--

```

### Data Module
Data module is empty to create repository, database and REST apis, or cache implementations. This layer only has RxJavaExtension class

### Domain Module
Domain module is for usecase or interactor to contatain business logic.

### Core Module
Core module can be used for containing libraries and other android related stuff. It only contains *CoreModule* and **CoreModuleDependencies**
for adding *dependent components* to dynamic feature modules or app module if required

### App Module
Main app with Application that uses @DaggerHiltApp, you can build your app in this module

### Dynamic Feature Modules
Modules for seperating fetaure from app to prevent app being monolithic and having parallel gradle builds

### test-utils Module
Module ready for unit-testing with libraries and extensions to be used in every module
add build.gradle file with ```testImplementation(project(Modules.AndroidLibrary.TEST_UTILS))```
If needed put your json data into response.json which is located in test/resources in test-utils module

## Dynamic Feature Navigation with Navigation Components

To navigate with Dynamic Feature Modules main layout should contain a ```DynamicNavHostFragment```,
```
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph" />
```

### Main navigation graph
And navigation folder should contain navigation graph with

    <!-- dynamic feature module-->
    <include-dynamic
        android:id="@+id/nav_graph_gallery"
        android:name="com.smarttoolfactory.gallery"
        app:graphResName="nav_graph_gallery"
        app:moduleName="gallery">

        <argument
            android:name="count"
            android:defaultValue="0"
            app:argType="integer" />
    </include-dynamic>

There are 3 important properties that should be carefully added to main graph for not receiving error

1. id of the navigation,``` android:id="@+id/nav_graph_gallery"``, should be same with the dynamic feature id
2. ```graphResName``` is the name of the navigation folder which is nav_graph_gallery.xml for this boilerplate
3. module name should be exactly same name dynamic feature module is named.


### Dynamic Feature Module navigation graph
In nav_graph_gallery.xml

```
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@id/nav_graph_gallery"
    app:moduleName="gallery"
    app:startDestination="@id/galleryFragment">
    <fragment
        android:id="@+id/galleryFragment"
        android:name="com.smarttoolfactory.gallery.GalleryFragment"
        android:label="GalleryFragment"
        tools:layout="@layout/fragment_gallery" />
</navigation>
```

Make sure that

1. id is same with the where this navigation is called except do not use *@+id/* since it creates new resource id, use *@id/*
2. Add module name as in other navigation graph
3. And start destination for first fragment to be displayed after navigating to this fragment.


### Navigating with navController and InstallMonitor

For navigation i used ```DynamicInstallFragment``` fragment which is inside *core module* since it provides

```private val installMonitor = DynamicInstallMonitor()``` to track installation state of the dynamic feature module
we navigate.

Navigate to a dynamic feature module using

```
fun navigateWithInstallMonitor(navController: NavController, @IdRes destinationId: Int) {

        navController.navigate(
            destinationId,
            null,
            null,
            DynamicExtras(installMonitor)
        )
}
```
which checks if the dynamic feature we wish to navigate is installed already ```installMonitor.isInstallRequired```
and. If the dynamic feature is not installed returns states for status where you can take action.

## Dagger Hilt and Dynamic Feature Module injections

### Application
Application class only need to use  ```@HiltAndroidApp``` annotation

### Activity
Activity should use ```@AndroidEntryPoint```annotation

### Core Module
Core module should have provision methods for dependecies to be injected in core module to any other dependent module

```
@EntryPoint
@InstallIn(ApplicationComponent::class)
interface CoreModuleDependencies {

    /*
       Provision methods to provide dependencies to components that depend on this component
     */
    fun coreDependency(): CoreDependency

}
```

### Dynamic Feature Modules

Modules that depend on core module shoud create a component, that depends on core module, with

```
@Component(
    dependencies = [CoreModuleDependencies::class],
    modules = [GalleryModule::class]
)
interface GalleryComponent {

    fun inject(galleryFragment: GalleryFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreModuleDependencies: CoreModuleDependencies,
            @BindsInstance application: Application
        ): GalleryComponent
    }
}
```

And creat this component in a ```Fragment``` or ```Activity``` using

```
        val coreModuleDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            CoreModuleDependencies::class.java
        )

        DaggerGalleryComponent.factory().create(
            coreModuleDependencies,
            requireActivity().application
        )
            .inject(this)
```

🔥 ```EntryPointAccessors.fromApplication``` depends on which component ```CoreModule``` uses ```@InstallIn``` with
