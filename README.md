# KMP Practice: Kotlin Multiplatform Movies App

A modern, scalable Kotlin Multiplatform (KMP) project demonstrating best practices for building cross-platform apps with a shared Compose UI, targeting **Android**, **iOS**, and **Web** (Wasm).

---

## ✨ Features

- **Kotlin Multiplatform**: Shared business logic, UI, and state management.
- **Compose Multiplatform**: Unified UI code for Android, iOS, and Web.
- **TMDB API Integration**: Browse and view movie details.
- **Platform-Adaptive UI**: Responsive layouts for web and mobile.
- **Modern Architecture**: MVVM, sealed state, DI with Koin, error handling, and more.
- **Reusable Components**: Custom buttons, scaffold, dialogs, navigation, and more.
- **Theming**: Consistent look and feel across all platforms.

---

## 🗂️ Project Structure

```
kmp_practice/
  ├── composeApp/                # Shared KMP module (UI, logic, resources)
  │   ├── src/
  │   │   ├── commonMain/        # Shared code for all platforms
  │   │   ├── androidMain/       # Android-specific code
  │   │   ├── iosMain/           # iOS-specific code
  │   │   └── wasmJsMain/        # Web-specific code (Wasm)
  │   └── build.gradle.kts
  ├── iosApp/                    # iOS SwiftUI entry point
  ├── kotlin-js-store/           # Web build artifacts
  ├── gradle/                    # Gradle wrapper and config
  ├── build.gradle.kts
  ├── settings.gradle.kts
  └── README.md
```

---

## 🚀 Getting Started

### 1. **Clone the Repository**

```bash
git clone https://github.com/your-org/kmp_practice.git
cd kmp_practice
```

### 2. **Configure API Keys**

This project uses [The Movie Database (TMDB) API](https://www.themoviedb.org/).  
**You must set your TMDB API token as an environment variable:**

- Create a `.env` file in the project root:
  ```
  TMDB_ACCESS_TOKEN=your_tmdb_api_token_here
  ```

- For more details, see [CONFIGURATION_GUIDE.md](CONFIGURATION_GUIDE.md).

### 3. **Build and Run**

#### **Android**
- Open in Android Studio, select the `androidApp` run configuration, and run on an emulator or device.

#### **iOS**
- Open `iosApp/iosApp.xcodeproj` in Xcode.
- Set the environment variable for the API token.
- Build and run on a simulator or device.

#### **Web**
- Run the following Gradle task:
  ```bash
  ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
  ```
- Open the provided local URL in your browser.

---

## 🧩 Key Modules & Components

### **Shared UI & Logic (`composeApp/src/commonMain`)**

- **Screens:**  
  - `MovieWebScreen`, `MovieListScreen`, `MovieDetailScreen`, `ProfileScreen`
- **ViewModels:**  
  - `MoviesViewModel` (state management, API calls)
- **Navigation:**  
  - Centralized in `core/navigation/`
- **Reusable Components:**  
  - `ScaffoldCustom`, `PrimaryButton`, `TextCustom`, `ImageCard`, `CustomAlertDialog`, `BottomNavigationBar`
- **Theme:**  
  - `core/theme/Theme.kt`, `Color.kt`, `Type.kt`
- **Utilities:**  
  - `PlatformUtils`, `Constants`, `ErrorUtils`, `StateUtils`

### **Platform-Specific Code**

- **Android:**  
  - Entry: `MainActivity.kt`, `MainApplication.kt`
- **iOS:**  
  - Entry: `MainViewController.kt`, SwiftUI: `iOSApp.swift`
- **Web:**  
  - Entry: `main.kt`, `index.html`, `styles.css`

---

## 🏗️ Architecture & Patterns

- **MVVM**: ViewModels in shared code, using `StateFlow` and sealed classes for state.
- **Navigation**: Centralized, with platform-adaptive screen selection.
- **Platform Adaptation**: Use `PlatformUtils` and selector functions to branch UI for web/mobile.
- **Dependency Injection**: Koin for shared and platform-specific dependencies.
- **Error Handling**: Sealed error types, user-friendly messages, and retry logic.
- **Theming**: Single source of truth for colors and typography.

---

## 🛠️ Example: Platform-Adaptive Screen

```kotlin
fun getMovieListScreenForPlatform(navController: Navigator, moviesViewModel: MoviesViewModel): @Composable () -> Unit {
    return when {
        PlatformUtils.isWeb() -> { { MovieWebScreen(navController, moviesViewModel) } }
        else -> { { MovieScreen(navController, moviesViewModel) } }
    }
}
```

---

## 🧪 Testing

- Shared tests in `commonTest`
- Platform-specific tests in respective source sets

---

## ⚙️ Configuration

See [CONFIGURATION_GUIDE.md](CONFIGURATION_GUIDE.md) for:
- API token setup
- Environment variables
- Platform-specific configuration
- Security best practices

---

## 🚦 Optimization & Best Practices

See [OPTIMIZATION_GUIDE.md](OPTIMIZATION_GUIDE.md) for:
- State management
- Error handling
- API layer improvements
- UI and performance optimizations
- Accessibility and security

---

## 📸 Screenshots

> _Add screenshots of Android, iOS, and Web UIs here for visual reference._

---

## 🤝 Contributing

1. Fork the repo and create your branch.
2. Follow the architecture and theming guidelines.
3. Add/modify tests as needed.
4. Open a pull request!

---

## 📚 Resources

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform)
- [Kotlin/Wasm](https://kotl.in/wasm/)
- [TMDB API](https://www.themoviedb.org/documentation/api)

---

## 📝 License

MIT (or your license here)

---

## 🙏 Feedback

- Join the [#compose-web Slack](https://slack-chats.kotlinlang.org/c/compose-web)
- Report issues on [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP)

---