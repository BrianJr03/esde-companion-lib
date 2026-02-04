# ES-DE Companion Library

An Android library for integrating ES-DE Companion with Android applications, providing dynamic wallpapers, event listening, and UI components.

## Features

- **Dynamic Wallpapers**: Display game artwork (screenshots, fanart, covers, etc.) as wallpapers
- **ES-DE Event System**: Listen to ES-DE events (game selection, system browsing, screensaver)
- **Setup Wizard**: Guided setup for configuring ES-DE integration
- **Settings UI**: Complete Compose-based settings screens for customization
- **Script Management**: Automatic creation and management of ES-DE integration scripts
- **Asset Management**: Built-in ES-DE system logos (200+ SVG files)

## Installation

### As a Local Module

In your `settings.gradle.kts`:
```kotlin
include(":esde-companion-lib")
```

In your app's `build.gradle.kts`:
```kotlin
dependencies {
    implementation(project(":esde-companion-lib"))
}
```

## Requirements

- **Min SDK**: 33
- **Compile SDK**: 36
- **Jetpack Compose**: Required
- **Hilt**: Dependency injection
- **Kotlin**: 2.0.21+

## Quick Start

### 1. Add Hilt Module

The library provides `ESDEModule` for dependency injection. Make sure your Application class is annotated with `@HiltAndroidApp`.

### 2. Integrate ESDE Preferences Manager

```kotlin
import jr.brian.esdecompanionlib.esde.preferences.ESDEPreferencesManager
import jr.brian.esdecompanionlib.esde.preferences.LocalESDEPreferencesManager

// In your DI setup
@Provides
@Singleton
fun provideESDEPreferencesManager(
    @ApplicationContext context: Context
): ESDEPreferencesManager = ESDEPreferencesManager(context)

// In your Composable
CompositionLocalProvider(
    LocalESDEPreferencesManager provides esdePreferencesManager
) {
    // Your content
}
```

### 3. Setup ES-DE Event Listening

```kotlin
import jr.brian.esdecompanionlib.esde.events.ESDEEventManager
import jr.brian.esdecompanionlib.esde.events.ESDEEventListenerImpl

@Inject lateinit var esdeEventManager: ESDEEventManager
@Inject lateinit var esdeEventListener: ESDEEventListenerImpl

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Start watching for ES-DE events
    esdeEventManager.startWatching()
    esdeEventManager.startPolling()
    
    // Set up event callbacks
    esdeEventListener.onSystemSelected = { systemName ->
        // Handle system selection
    }
    esdeEventListener.onGameSelected = { gameFilename, _, systemName ->
        // Handle game selection
    }
}

override fun onDestroy() {
    super.onDestroy()
    esdeEventManager.stopWatching()
}
```

### 4. Use ESDE Wallpaper Container

```kotlin
import jr.brian.esdecompanionlib.esde.ui.ESDEWallpaperContainer
import jr.brian.esdecompanionlib.esde.viewmodel.ESDEViewModel

val esdeViewModel: ESDEViewModel = hiltViewModel()
val wallpaperState by esdeViewModel.wallpaperState

ESDEWallpaperContainer(state = wallpaperState) {
    // Your app content here
}
```

### 5. Add ESDE Settings Screen

```kotlin
import jr.brian.esdecompanionlib.esde.ui.ESDESettingsScreen

ESDESettingsScreen(
    onNavigateBack = { /* handle back */ },
    onRunSetupWizard = { /* handle setup wizard */ }
)
```

## Main Components

### Preferences
- `ESDEPreferencesManager`: Manages all ES-DE related preferences
- `SetupPreferences`: Tracks setup wizard completion status

### Events
- `ESDEEventManager`: Monitors ES-DE event files
- `ESDEEventListener`: Interface for handling ES-DE events
- `ESDEEventListenerImpl`: Default implementation

### UI Components
- `ESDEWallpaperContainer`: Displays dynamic game artwork
- `ESDESettingsScreen`: Complete settings UI
- `ESDESetupScreen`: Setup wizard dialog

### Scripts
- `ScriptManager`: Creates and validates ES-DE integration scripts
- `ESDESetupHelper`: Helper functions for setup process

### ViewModel
- `ESDEViewModel`: Manages wallpaper state and updates

## String Resources

All UI strings are included in the library's `strings.xml`. Key string prefixes:
- `esde_setup_*`: Setup wizard strings
- `esde_settings_*`: Settings screen strings

## Assets

The library includes 200+ ES-DE system logos in SVG format located at:
```
esde-companion-lib/src/main/assets/system_logos/
```

## Permissions

The library requires the following permissions (automatically merged):
- `READ_EXTERNAL_STORAGE` (maxSdkVersion 32)
- `WRITE_EXTERNAL_STORAGE` (maxSdkVersion 32)
- `MANAGE_EXTERNAL_STORAGE`

## Credits

- Inspired by **ES-DE Companion** by @RobZombie9043
- Built for integration with **ES-DE** for Android
