# Mziike Trailers

You can change the api key if daily requests are exceeded. i.e 100 requests for the this free plan

## Table of Contents

- [Prerequisite](#prerequisite)
- [The App](#theapp)
- [Architecture](#architecture)
- [Testing & Automation](#testing)
- [ScreenShots](#screenshots)
- [Sample App and Source Code](#sampleappandsourcecode)

## Prerequisite

This project uses the Gradle build system. To build this project, use the `fastlane debug_build`
`gradlew build` command after cloning or use "Import Project" in Android Studio.

## The App

A small app that loads and presents a list of movies and TV Shows from  [IMDB API](https://imdb-api.com/api).
The app has two Activity namely MainActivity and SearchActivity. MainActivity acts as entry point with a bottom navigation
which hold three fragmnents; HolderFragment, ProfileFragment and TvFragment.
HolderFragment, provides a parent fragment manager to HomeMoviesFragment  which show list of 
movies TV Shows, ShowAllFragmnets that show all movies under a category and DetailsFragment that show more details about a single
movie or tv show item.
SearchActivity is used to search movies or tv shows

The project has been written in Kotlin language. For network requests, it uses Retrofit with RxJava and Coroutines.

Dagger Hilt has been used for Dependency injection.

## Architecture
The project is built using the MVVM architectural pattern and make heavy use of a couple of Android Jetpack components. Mvvm allows for the separation of concern which also makes testing easier.


## MVVM implementation
The first time the app is opened, the data will be fetched from the backend api service and stored locally with 
the help of Room database.
But if there is no internet or the api service is down, the data will be fetched from the local cache.
This is handled in the repository class.
ViewModel is basically responsible for updating the UI (Activity/Fragment) with the data changes.
The ViewModel will initialise an instance of the Repository class and update the UI based with this data.



## Testing
All tests are under the Android Test package. All the tests are run using JUnit.
To run tests using fastlane run `gradlew build` command in your CL.
Test automation have also been achieved using CircleCi.

## ScreenShots


The app is available in both day and night theme.


<img src="https://user-images.githubusercontent.com/47601553/138744835-21534c54-713b-4446-9a06-6bd39b9dc5e7.png" width="200" style="max-width:100%;">    <img src="https://user-images.githubusercontent.com/47601553/138744938-4c8d6c2a-93fc-4d64-86d3-abdbf72b2310.png" width="200" style="max-width:100%;">   <img src="https://user-images.githubusercontent.com/47601553/138745065-84d3eecb-068d-43c0-a711-d11bcfc30130.png" width="200" style="max-width:100%;">    <img src="https://user-images.githubusercontent.com/47601553/138745165-21049cc6-f975-40e9-9fa1-52bf8d31225c.png" width="200" style="max-width:100%;"></br></br><img src="https://user-images.githubusercontent.com/47601553/138745238-5cc36327-e44e-461d-8fcf-a8303114e928.png" width="200" style="max-width:100%;">   <img src="https://user-images.githubusercontent.com/47601553/138745858-16b03eed-53ce-4027-87e1-c7a02e787fdd.png" width="200" style="max-width:100%;">    <img src="https://user-images.githubusercontent.com/47601553/138745763-6e1ba342-ed99-452c-89c7-30234c6dcef1.png" width="200" style="max-width:100%;"></br></br>




Libraries used in the whole application are:

- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way 
- [RxJava](https://github.com/ReactiveX/RxJava) - RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.
- [Kotlin.coroutines](https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQjw1dGJBhD4ARIsANb6Odld-9wkN4Lkm6UJAvWRshusopwstZH5IXkSLzxv_Q5JYjgjozIywfcaAlS9EALw_wcB&gclsrc=aw.ds) - Concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- [Dagger Hilt](https://dagger.dev/hilt/) - Used for Dependency injection
    - To simplify Dagger-related infrastructure for Android apps.
    - To create a standard set of components and scopes to ease setup, readability/understanding, and code sharing between apps.
    - To provide an easy way to provision different bindings to various build types (e.g. testing, debug, or release).

- [Retrofit](https://square.github.io/retrofit/) - Turns your HTTP API into a Java interface.
- [Fastlane](https://docs.fastlane.tools/getting-started/android/setup/) -  Automate beta deployments and releases for Android apps. 🚀
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) - Enables mock creation, verification and stubbing for testing
- [CircleCi](https://circleci.com/continuous-integration/) - Achieving continuous integration
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - A scriptable web server for testing HTTP clients
Kotlin coroutines
## Sample App and Source Code

Clone the project and run `fastlane debug_build` in terminal to generate app

[Source.Code](https://github.com/Hechio/Mziike-Trailers) - Access to the project's github reporitory
