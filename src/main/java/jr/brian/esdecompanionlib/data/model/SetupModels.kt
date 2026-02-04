package jr.brian.esdecompanionlib.data.model

/**
 * Steps in the ESDE setup wizard flow.
 */
sealed class SetupStep {
    object Welcome : SetupStep()
    object RequestPermissions : SetupStep()
    object PermissionsGranted : SetupStep()
    object SelectScriptsFolder : SetupStep()
    object CreateScripts : SetupStep()
    object SelectMediaFolder : SetupStep()
    object EnableScriptsInESDE : SetupStep()
    object Complete : SetupStep()
    data class Warning(
        val type: WarningType,
        val path: String
    ) : SetupStep()
}

/**
 * Types of warnings that can occur during setup.
 */
enum class WarningType {
    NonStandardScriptsPath,
    NonStandardMediaPath,
    ScriptsMissing
}

/**
 * Result of a setup operation.
 */
data class SetupResult(
    val success: Boolean,
    val message: String,
    val needsPermission: Boolean
)
