# Boilerplate for creating project with MVVM, Jetpack Compose, Dagger Hilt, Dynamic Feature Modules

[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.4.0-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)


This is a boilerplate code to use not to repeat same process over again when starting a new project. Folders are created, dependencies and libraries are set up.

Main branch has no DataSource, Repository or ViewModel classes. Checkout coroutines-flow
or compose branch. Data module in these branches
contain fake dao, local and remote data source, repository, mapper.


* Gradle Kotlin DSL is used for setting up gradle files with ```buildSrc``` folder and extensions.
* KtLint, Detekt, and Git Hooks is used for checking, and formatting code and validating code before commits.
* Dagger Hilt, Dynamic Feature Modules with Navigation Components, ViewModel, Retrofit, Room, Coroutines libraries adn dependencies are set up.
* ```features``` and ```libraries``` folders are used to include android libraries and dynamic feature modules
* In core module dagger hilt dependencies and ```@EntryPoint``` is created
* test-utils module for shared folder for tes and androidTest folders, LiveDataObserver and FlowObserver.
* Jetpack Compose dependencies added, app runs ```@Composeable``` as project is run
### Note
Change ```applicationId```in ```Version.AndroidVersion```

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

if after these tasks status is 0 then commit is successful, otherwise you need to check out console for links to your code and fix those errors.


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
     --> feature module(dynamic featature module)
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
Data module is empty to create repository, database and REST apis, or cache implementations.

### Domain Module
Domain module is for UseCase or Interactor to contain business logic.

### Core Module
Core module can be used for containing libraries and other android related stuff. It only contains *CoreModule* and **CoreModuleDependencies**
for adding *dependent components* to dynamic feature modules or app module if required

### App Module
Main app with Application that uses @DaggerHiltApp, you can build your app in this module

### Dynamic Feature Modules
Modules for separating feature from app to prevent app being monolithic and having parallel gradle builds

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
        android:id="@+id/nav_graph_feature"
        android:name="com.smarttoolfactory.feature"
        app:graphResName="nav_graph_feature"
        app:moduleName="feature">

        <argument
            android:name="count"
            android:defaultValue="0"
            app:argType="integer" />
    </include-dynamic>

There are 3 important properties that should be carefully added to main graph for not receiving error

1. id of the navigation,``` android:id="@+id/nav_graph_feature"```, should be same with the dynamic feature id
2. ```graphResName``` is the name of the navigation folder which is nav_graph_feature.xml for this boilerplate
3. module name should be exactly same name dynamic feature module is named.


### Dynamic Feature Module navigation graph
In nav_graph_feature.xml

```
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@id/nav_graph_feature"
    app:moduleName="feature"
    app:startDestination="@id/featureFragment">
    <fragment
        android:id="@+id/featureFragment"
        android:name="com.smarttoolfactory.feature.FeatureFragment"
        android:label="FeatureFragment"
        tools:layout="@layout/fragment_feature" />
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
Core module should have provision methods for dependencies to be injected in core module to any other dependent module

```
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreModuleDependencies {

    /*
       Provision methods to provide dependencies to components that depend on this component
     */
    fun coreDependency(): CoreDependency

}
```

### Dynamic Feature Modules

Modules that depend on core module should create a component, that depends on core module, with

```
@Component(
    dependencies = [CoreModuleDependencies::class],
    modules = [FeatureModule::class]
)
interface FeatureComponent {

    fun inject(featureFragment: FeatureFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreModuleDependencies: CoreModuleDependencies,
            @BindsInstance application: Application
        ): FeatureComponent
    }
}
```

And create this component in a ```Fragment``` or ```Activity``` using

```
        val coreModuleDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            CoreModuleDependencies::class.java
        )

        DaggerFeatureComponent.factory().create(
            coreModuleDependencies,
            requireActivity().application
        )
            .inject(this)
```

🔥 ```EntryPointAccessors.fromApplication``` depends on which component ```CoreModule``` uses ```@InstallIn``` with

## Testing


### test-utils
 ```test-shared``` folder contains common rules, and utilities for both ```test``` and ```androidTest``` for using
 both with unit tests and integration test.

```
    sourceSets {

        val sharedTestDir = "src/test-shared/java"

        getByName("test") {
            java.srcDir(sharedTestDir)
        }

        getByName("androidTest") {
            java.srcDir(sharedTestDir)
            resources.srcDir("src/test/resources")
        }
    }
```

### LiveDataObserver

This class is observer for testing ```LiveData``` that emits more than one values and similar to RxJava ```TestObserver```.

```
class LiveDataTestObserver<T> constructor(
    private val liveData: LiveData<T>
) : Observer<T> {

    init {
        liveData.observeForever(this)
    }

    private val testValues = mutableListOf<T>()

    override fun onChanged(t: T) {
        if (t != null) testValues.add(t)
    }

    fun assertNoValues(): LiveDataTestObserver<T> {
        if (testValues.isNotEmpty()) throw AssertionError(
            "Assertion error with actual size ${testValues.size}"
        )
        return this
    }

    fun assertValueCount(count: Int): LiveDataTestObserver<T> {
        if (count < 0) throw AssertionError(
            "Assertion error! value count cannot be smaller than zero"
        )
        if (count != testValues.size) throw AssertionError(
            "Assertion error! with expected $count while actual ${testValues.size}"
        )
        return this
    }

    fun assertValues(vararg predicates: T): LiveDataTestObserver<T> {
        if (!testValues.containsAll(predicates.asList())) throw  AssertionError("Assertion error!")
        return this
    }

    fun assertValues(predicate: (List<T>) -> Boolean): LiveDataTestObserver<T> {
        predicate(testValues)
        return this
    }

    fun values(predicate: (List<T>) -> Unit): LiveDataTestObserver<T> {
        predicate(testValues)
        return this
    }

    fun values(): List<T> {
        return testValues
    }

    /**
     * Removes this observer from the [LiveData] which was observing
     */
    fun dispose() {
        liveData.removeObserver(this)
    }

    /**
     * Clears data available in this observer and removes this observer from the [LiveData] which was observing
     */
    fun clear() {
        testValues.clear()
        dispose()
    }
}

fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {

    val testObserver = LiveDataTestObserver(this)

    // Remove this testObserver that is added in init block of TestObserver, and clears previous data
    testObserver.clear()
    observeForever(testObserver)

    return testObserver
}
```

### FlowTestObserver

TestObserver with declarative assertion methods to test more than multiple states and values sequentaially.

```
class FlowTestObserver<T>(
    private val coroutineScope: CoroutineScope,
    private val flow: Flow<T>,
    private val waitForDelay: Boolean = false
) {
    private val testValues = mutableListOf<T>()
    private var error: Throwable? = null

    private var isInitialized = false

    private var isCompleted = false

    private lateinit var job: Job


    private suspend fun initializeAndJoin() {
        job = createJob(coroutineScope)
    }


    private suspend fun initialize() {

        if (!isInitialized) {
            isInitialized = true

            if (waitForDelay) {
                try {
                    withTimeout(Long.MAX_VALUE) {
                        job = createJob(this)
                    }
                } catch (e: Exception) {
                    isCompleted = false
                }
            } else {
                initializeAndJoin()
            }
        }
    }

    private fun createJob(scope: CoroutineScope): Job {

        val job = flow
            .onStart {
            }
            .onCompletion {
                isCompleted = true
            }
            .catch { throwable ->
                error = throwable
            }
            .onEach {
                testValues.add(it)
            }
            .launchIn(scope)

        return job
    }


    suspend fun assertNoValues(): FlowTestObserver<T> {
        initialize()
        if (testValues.isNotEmpty()) throw AssertionError(
            "Assertion error! Actual size ${testValues.size}"
        )
        return this
    }

    suspend fun assertValueCount(count: Int): FlowTestObserver<T> {
        initialize()
        if (count < 0) throw AssertionError(
            "Assertion error! Value count cannot be smaller than zero"
        )
        if (count != testValues.size) throw AssertionError(
            "Assertion error! Expected $count while actual ${testValues.size}"
        )
        return this
    }

    suspend fun assertValues(vararg values: T): FlowTestObserver<T> {
        initialize()
        if (!testValues.containsAll(values.asList()))
            throw  AssertionError("Assertion error! At least one value does not match")
        return this
    }

    suspend fun assertValues(predicate: (List<T>) -> Boolean): FlowTestObserver<T> {

        initialize()

        if (!predicate(testValues))
            throw  AssertionError("Assertion error! At least one value does not match")
        return this
    }

    suspend fun assertError(throwable: Throwable): FlowTestObserver<T> {

        initialize()

        val errorNotNull = exceptionNotNull()

        if (!(errorNotNull::class.java == throwable::class.java &&
                    errorNotNull.message == throwable.message)
        )
            throw AssertionError("Assertion Error! throwable: $throwable does not match $errorNotNull")
        return this
    }

    suspend fun assertError(errorClass: Class<Throwable>): FlowTestObserver<T> {

        initialize()

        val errorNotNull = exceptionNotNull()

        if (errorNotNull::class.java != errorClass)
            throw  AssertionError("Assertion Error! errorClass $errorClass does not match ${errorNotNull::class.java}")
        return this
    }

    suspend fun assertError(predicate: (Throwable) -> Boolean): FlowTestObserver<T> {

        initialize()

        val errorNotNull = exceptionNotNull()

        if (!predicate(errorNotNull))
            throw AssertionError("Assertion Error! Exception for $errorNotNull")
        return this
    }

    suspend fun assertNoError(): FlowTestObserver<T> {

        initialize()

        if (error != null)
            throw AssertionError("Assertion Error! Exception occurred $error")

        return this
    }

    suspend fun assertNull(): FlowTestObserver<T> {

        initialize()

        testValues.forEach {
            if (it != null) throw AssertionError("Assertion Error! There are more than one item that is not null")
        }

        return this
    }

    suspend fun assertComplete(): FlowTestObserver<T> {

        initialize()

        if (!isCompleted) throw AssertionError("Assertion Error! Job is not completed yet!")
        return this
    }

    suspend fun assertNotComplete(): FlowTestObserver<T> {

        initialize()

        if (isCompleted) throw AssertionError("Assertion Error! Job is completed!")
        return this
    }

    suspend fun values(predicate: (List<T>) -> Unit): FlowTestObserver<T> {
        predicate(testValues)
        return this
    }

    suspend fun values(): List<T> {

        initialize()

        return testValues
    }


    private fun exceptionNotNull(): Throwable {

        if (error == null)
            throw  AssertionError("There is no exception")

        return error!!
    }

    fun dispose() {
        job.cancel()
    }
}

/**
 * Creates a RxJava2 style test observer that uses `onStart`, `onEach`, `onCompletion`
 *
 * * Set waitForDelay true for testing delay.
 *
 * ###  Note: waiting for delay with a channel that sends values throw TimeoutCancellationException, don't use timeout with channel
 * TODO Fix channel issue
 */
suspend fun <T> Flow<T>.test(
    scope: CoroutineScope,
    waitForDelay: Boolean = false
): FlowTestObserver<T> {
    return FlowTestObserver(scope, this@test, waitForDelay)
}

/**
 * Test function that awaits with time out until each delay method is run and then since
 * it takes a predicate that runs after a timeout.
 */
suspend fun <T> Flow<T>.testAfterDelay(
    scope: CoroutineScope,
    predicate: suspend FlowTestObserver<T>.() -> Unit

): Job {
    return scope.launch(coroutineContext) {
        FlowTestObserver(this, this@testAfterDelay, true).predicate()
    }
}


```
