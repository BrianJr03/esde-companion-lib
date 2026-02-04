# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep ESDE public API
-keep public class jr.brian.esdecompanionlib.** { public *; }

# Keep Hilt generated code
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel
