# ESDE Companion Library Architecture

## MVVM Structure

The library follows proper MVVM (Model-View-ViewModel) architecture:

### 📦 Data Layer (`data/`)

#### `data/model/`
- **AnimationStyle.kt** - Animation transition types
- **PreferencesState.kt** - Preference models (SystemImageType, GameImageType, LogoAlignment, ESDEPrefsState)
- **WallpaperState.kt** - Wallpaper display state
- **SetupModels.kt** - Setup wizard models (SetupStep, WarningType, SetupResult)

#### `data/repository/`
- **PreferencesRepository.kt** - Manages user preferences with reactive StateFlow
- **EventRepository.kt** - Monitors ESDE events via file watching
- **ScriptRepository.kt** - Creates and validates ESDE companion scripts
- **SetupRepository.kt** - Handles setup wizard flow and permissions

### 🎯 Presentation Layer (`presentation/`)

#### `presentation/viewmodel/`
- **ESDEViewModel.kt** - Main ViewModel managing wallpaper state and media resolution

#### `presentation/ui/`
- **ImageAnimationState.kt** - Animation state management for image transitions
- *(Other UI screens and components remain in `esde/ui/` - see Migration Notes)*

### 🔧 Utilities (`util/`)
- **ESDEPreferencesConstants.kt** - SharedPreferences keys
- **ESDEMediaConstants.kt** - Media paths and extensions
- **ESDEEventConstants.kt** - Event monitoring constants

### 💉 Dependency Injection (`di/`)
- **ESDEModule.kt** - Dagger Hilt module providing dependencies

## Migration Notes

### UI Components
UI components currently remain in `esde/ui/` package for backward compatibility:
- Screens: ESDESettingsScreen, ESDESetupScreen, ESDEWallpaperContainer, SetupWizardDialog
- Components: AnimationStyleSelector, BackgroundColorSelector, GameImageTypeSelector, etc.

**Future Enhancement**: Migrate UI files to `presentation/ui/` package and update imports.

### Key Changes from Original Structure
1. **Models** separated into `data/model/`
2. **Managers** renamed to **Repositories** in `data/repository/`
3. **ViewModel** moved to `presentation/viewmodel/`
4. All files now have concise KDoc documentation
5. Package naming follows clean architecture principles

## Usage Example

```kotlin
@HiltViewModel
class YourViewModel @Inject constructor(
    private val prefsRepo: PreferencesRepository,
    private val eventRepo: EventRepository,
    val esdeViewModel: ESDEViewModel
) : ViewModel() {
    // Access reactive preferences
    val prefsState = prefsRepo.state
    
    // Update preferences
    prefsRepo.setDimmingLevel(50)
    
    // Watch for ESDE events
    eventRepo.startWatching()
}
```

## Dependencies

- **Dagger Hilt** - Dependency injection
- **Kotlin Coroutines** - Reactive state management
- **Jetpack Compose** - UI framework
