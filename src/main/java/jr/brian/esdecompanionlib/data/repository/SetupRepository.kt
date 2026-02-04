package jr.brian.esdecompanionlib.data.repository

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import jr.brian.esdecompanionlib.data.model.SetupResult
import jr.brian.esdecompanionlib.data.model.SetupStep
import java.io.File

/**
 * Repository for ESDE setup preferences.
 */
class SetupPreferencesRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "SetupPrefs", Context.MODE_PRIVATE
    )

    var setupCompleted: Boolean
        get() = prefs.getBoolean("setup_completed", false)
        set(value) = prefs.edit().putBoolean("setup_completed", value).apply()

    var scriptsPath: String
        get() = prefs.getString("scripts_path", DEFAULT_SCRIPTS_PATH) ?: DEFAULT_SCRIPTS_PATH
        set(value) = prefs.edit().putString("scripts_path", value).apply()

    var mediaPath: String
        get() = prefs.getString("media_path", DEFAULT_MEDIA_PATH) ?: DEFAULT_MEDIA_PATH
        set(value) = prefs.edit().putString("media_path", value).apply()

    companion object {
        const val DEFAULT_SCRIPTS_PATH = "/storage/emulated/0/ES-DE/scripts"
        const val DEFAULT_MEDIA_PATH = "/storage/emulated/0/ES-DE/downloaded_media"
    }
}

/**
 * Repository for managing the ESDE setup wizard flow.
 */
class SetupWizardRepository(
    private val context: Context,
    private val preferences: SetupPreferencesRepository,
    private val onStepChanged: (SetupStep) -> Unit,
    private val onWizardComplete: () -> Unit
) {
    private var currentStep = 0
    var isInSetupWizard = false
        private set

    fun cancelWizard() {
        isInSetupWizard = false
        currentStep = 0
    }

    fun continueWizard() {
        currentStep++

        when (currentStep) {
            1 -> {
                if (hasStoragePermission()) {
                    onStepChanged(SetupStep.PermissionsGranted)
                } else {
                    onStepChanged(SetupStep.RequestPermissions)
                }
            }
            2 -> onStepChanged(SetupStep.SelectScriptsFolder)
            3 -> onStepChanged(SetupStep.CreateScripts)
            4 -> onStepChanged(SetupStep.SelectMediaFolder)
            5 -> onStepChanged(SetupStep.EnableScriptsInESDE)
            6 -> {
                isInSetupWizard = false
                preferences.setupCompleted = true
                onWizardComplete()
            }
        }
    }

    fun hasStoragePermission(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                Environment.isExternalStorageManager()
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            }
            else -> true
        }
    }

    fun isValidScriptsPath(path: String): Boolean {
        return path.contains("ES-DE", ignoreCase = true) &&
                path.contains("scripts", ignoreCase = true)
    }

    fun isValidMediaPath(path: String): Boolean {
        return path.contains("downloaded_media", ignoreCase = true)
    }

    fun areScriptsInstalled(scriptsPath: String): Boolean {
        val scriptsDir = File(scriptsPath)
        val scriptFiles = listOf(
            File(scriptsDir, "game-select/esdecompanion-game-select.sh"),
            File(scriptsDir, "system-select/esdecompanion-system-select.sh"),
            File(scriptsDir, "game-start/esdecompanion-game-start.sh"),
            File(scriptsDir, "game-end/esdecompanion-game-end.sh"),
            File(scriptsDir, "screensaver-start/esdecompanion-screensaver-start.sh"),
            File(scriptsDir, "screensaver-end/esdecompanion-screensaver-end.sh"),
            File(scriptsDir, "screensaver-game-select/esdecompanion-screensaver-game-select.sh")
        )
        return scriptFiles.all { it.exists() }
    }
}

/**
 * Helper functions for ESDE setup initialization.
 */
object SetupHelper {
    const val REQUEST_STORAGE_PERMISSION = 1001

    fun hasStoragePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestStoragePermission(activity: Activity) {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        activity.requestPermissions(permissions.toTypedArray(), REQUEST_STORAGE_PERMISSION)
    }

    fun initializeESDEIntegration(context: Context): SetupResult {
        return try {
            if (!hasStoragePermission(context)) {
                return SetupResult(
                    success = false,
                    message = "Storage permission required",
                    needsPermission = true
                )
            }

            val scriptsDir = File("/storage/emulated/0/ES-DE/scripts")
            if (!scriptsDir.exists()) {
                scriptsDir.mkdirs()
            }

            val scriptResult = ScriptRepository.createAllScripts(scriptsDir)

            SetupResult(
                success = scriptResult.success,
                message = scriptResult.message,
                needsPermission = false
            )
        } catch (e: Exception) {
            SetupResult(
                success = false,
                message = "Failed to initialize ES-DE integration: ${e.message}",
                needsPermission = false
            )
        }
    }
}
