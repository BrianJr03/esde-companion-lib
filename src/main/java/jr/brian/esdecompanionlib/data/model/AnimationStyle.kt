package jr.brian.esdecompanionlib.data.model

/**
 * Animation styles for image transitions.
 */
enum class AnimationStyle {
    /** No animation */
    None,
    
    /** Simple fade transition */
    Fade,
    
    /** Combined scale and fade transition */
    ScaleFade,
    
    /** Custom animation parameters */
    Custom
}
