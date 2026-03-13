# XGithubApi - Final Notes

## Libraries used and why

### Core stack
- **Kotlin + Android Gradle Plugin**: primary language/build tooling for all modules (`app`, `domain`, `data`).
- **Jetpack Compose + Material 3**: declarative UI in `app` (screen composition, theming, preview tooling).
- **Navigation Compose**: in-app navigation between home and repo-details screens.
- **AndroidX Core/Lifecycle/Splashscreen**: app lifecycle integration, startup splash, and Android compatibility helpers.

### Dependency injection
- **Hilt (Dagger Hilt + KSP compiler)**: DI wiring for `HttpClient`, API service, repository, and ViewModel injection with `@AndroidEntryPoint`.

### Networking and serialization
- **Ktor Client (CIO, ContentNegotiation, Logging, Timeout)**: HTTP client layer in `data`.
- **Gson serialization (`ktor-serialization-gson`)**: JSON mapping for GitHub API responses.

### Image loading
- **Coil 3 (`coil-compose`, `coil-network-okhttp`)**: async image loading in Compose UI.

### Testing
- **JUnit4**: base unit test framework.
- **MockK**: mocking/stubbing for Kotlin classes and collaborators.
- **kotlinx-coroutines-test**: deterministic coroutine testing.
- **Ktor Mock client**: network-layer test doubles without real HTTP.
- **AndroidX test + Espresso + Compose UI test libs**: instrumentation and UI test support.

## Tradeoffs / missing features

- **Tight module coupling**: `domain` depends on `data` (`implementation(project(":data"))`), which weakens clean architecture boundaries. Usually `data` should depend on abstractions from `domain`.
- **Limited API flexibility**: endpoint is fixed to one org URL in `data/src/main/java/com/jonathan/data/api/ApiRoutes.kt`; no pagination, sorting, org selection, or search.
- **Error handling is coarse**: repository returns a generic `"api error"` string and status mapping is fixed. Missing richer error body parsing/retry/backoff handling.
- **No local cache/offline mode**: app currently appears network-only (no Room/DataStore persistence).

## How to run the app/tests

### Prerequisites
- Android Studio (recommended) with SDK API 36 installed.
- JDK 21 (project compiles with Java 21 in module Gradle files).
- `local.properties` with `sdk.dir` (already present locally in this workspace).

> Note: from this environment, running `./gradlew -version` failed because no Java runtime was found, so commands below are the intended run/test commands once JDK is installed/configured.

### Run app (terminal)
```bash
cd /Users/U022143/Dev/XGithubApi
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

### Run tests (terminal)
```bash
cd /Users/U022143/Dev/XGithubApi
./gradlew test
./gradlew :app:testDebugUnitTest
./gradlew :data:testDebugUnitTest
./gradlew :domain:testDebugUnitTest
```

### Run instrumentation/UI tests (device or emulator required)
```bash
cd /Users/U022143/Dev/XGithubApi
./gradlew :app:connectedDebugAndroidTest
```

### Android Studio flow
1. Open the project in Android Studio.
2. Select `app` run configuration.
3. Run on emulator/device.
4. For tests, run unit tests from each module or use Gradle tool window tasks above.

