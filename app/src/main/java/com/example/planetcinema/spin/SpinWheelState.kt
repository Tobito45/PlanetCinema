package com.lyh.spintest

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import com.example.planetcinema.spin.getDegreeFromSection
import com.example.planetcinema.spin.getDegreeFromSectionWithRandom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.time.Duration

@Stable
data class SpinWheelState(
    internal val items: List<SpinWheelItem>,
    @DrawableRes internal val backgroundImage: Int,
    @DrawableRes internal val centerImage: Int,
    @DrawableRes internal val indicatorImage: Int,
    private val initSpinWheelSection: Int?,
    private val onSpinningFinished: (() -> Unit)?,
    private val stopDuration: Duration,
    private val stopNbTurn: Float,
    private val rotationPerSecond: Float,
    private val scope: CoroutineScope,
    internal val rotation : Animatable<Float, AnimationVector1D>
) {
  //  internal val rotation = Animatable(0f)

    init {
        Log.i("Koleso", "regenerate")
        initSpinWheelSection?.let {
            goto(it)
        } ?: launchInfinite()
    }

    fun stoppingWheel(sectionToStop: Int) {
        if (sectionToStop !in items.indices) {
            Log.e("spin-wheel", "cannot stop wheel, section $sectionToStop not exists in items")
            return
        }


        scope.launch {
            val destinationDegree = getDegreeFromSectionWithRandom(items, sectionToStop)

            rotation.animateTo(
                targetValue = rotation.value + (stopNbTurn * 360f) + destinationDegree + (360f - (rotation.value % 360f)),
                animationSpec = tween(
                    durationMillis = stopDuration.inWholeMilliseconds.toInt(),
                    easing = EaseOutQuad
                )
            )
        }

    }

    fun goto(section: Int) {
        scope.launch {
            if (section !in items.indices) {
                Log.e(
                    "spin-wheel",
                    "cannot goto specific section of wheel, section $section not exists in items"
                )
                return@launch
            }
            val positionDegree = getDegreeFromSection(items, section)
            rotation.snapTo(positionDegree)
        }
    }

    fun launchInfinite() {
        scope.launch {
            // Infinite repeatable rotation when is playing
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (rotationPerSecond * 1000f).toInt(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }
}
/*
@Composable
fun rememberSpinWheelState(
    items: PersistentList<SpinWheelItem>,
    @DrawableRes backgroundImage: Int,
    @DrawableRes centerImage: Int,
    @DrawableRes indicatorImage: Int,
    onSpinningFinished: (() -> Unit)?,
    initSpinWheelSection: Int? = 0, //if null then infinite
    stopDuration: Duration = 8.seconds,
    stopNbTurn: Float = 3f,
    rotationPerSecond: Float = 0.8f,
    scope: CoroutineScope = rememberCoroutineScope(),
): SpinWheelState {
    return remember {
        SpinWheelState(
            items = items,
            backgroundImage = backgroundImage,
            centerImage = centerImage,
            indicatorImage = indicatorImage,
            initSpinWheelSection = initSpinWheelSection,
            stopDuration = stopDuration,
            stopNbTurn = stopNbTurn,
            rotationPerSecond = rotationPerSecond,
            scope = scope,
            onSpinningFinished = onSpinningFinished,
        )
    }
}*/
