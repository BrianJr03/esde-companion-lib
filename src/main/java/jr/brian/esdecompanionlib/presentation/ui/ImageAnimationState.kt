package jr.brian.esdecompanionlib.presentation.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import jr.brian.esdecompanionlib.data.model.AnimationStyle
import kotlinx.coroutines.launch

/**
 * Holds animation state for image transitions (scale and alpha).
 */
class ImageAnimationState(
    val scale: Animatable<Float, *>,
    val alpha: Animatable<Float, *>
) {
    fun Modifier.animatedGraphicsLayer(): Modifier = this.graphicsLayer {
        scaleX = scale.value
        scaleY = scale.value
        alpha = this@ImageAnimationState.alpha.value
    }
}

/**
 * Remembers and manages animation state for image path changes.
 *
 * @param imagePath Current image path (triggers animation on change)
 * @param animate Whether animation is enabled
 * @param animationStyle Style of animation to use
 * @param animationDuration Duration in milliseconds
 * @param animationScale Initial scale for scale animations
 */
@Composable
fun rememberImageAnimationState(
    imagePath: String,
    animate: Boolean = true,
    animationStyle: AnimationStyle = AnimationStyle.Fade,
    animationDuration: Int = 300,
    animationScale: Float = 0.9f
): ImageAnimationState {
    var previousPath by remember { mutableStateOf<String?>(null) }
    val isNewImage = remember(imagePath) { previousPath != imagePath }

    val shouldAnimateScale = animate && animationStyle in listOf(
        AnimationStyle.ScaleFade,
        AnimationStyle.Custom
    )

    val scaleAnimatable = remember(imagePath) {
        Animatable(if (!animate || !isNewImage || !shouldAnimateScale) 1f else animationScale)
    }
    val alphaAnimatable = remember(imagePath) {
        Animatable(if (!animate || !isNewImage || animationStyle == AnimationStyle.None) 1f else 0f)
    }

    LaunchedEffect(imagePath) {
        if (animate && isNewImage && animationStyle != AnimationStyle.None) {
            when (animationStyle) {
                AnimationStyle.Fade -> {
                    alphaAnimatable.animateTo(1f, animationSpec = tween(animationDuration))
                }
                AnimationStyle.ScaleFade,
                AnimationStyle.Custom -> {
                    launch { scaleAnimatable.animateTo(1f, animationSpec = tween(animationDuration)) }
                    launch { alphaAnimatable.animateTo(1f, animationSpec = tween(animationDuration)) }
                }
                else -> { /* No animation */ }
            }
        }
        previousPath = imagePath
    }

    return ImageAnimationState(scaleAnimatable, alphaAnimatable)
}
