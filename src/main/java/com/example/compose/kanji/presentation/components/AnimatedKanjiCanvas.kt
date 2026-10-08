package com.example.compose.kanji.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.core.graphics.PathParser
import kotlinx.coroutines.delay

/** Tamaño del lienzo SVG de KanjiVG (viewBox 0 0 109 109). */
private const val SVG_VIEWPORT = 109f

/**
 * Dibuja un kanji trazo por trazo.
 *
 * @param strokePaths trazos en formato SVG path data, en orden de escritura.
 * @param replayKey cambia este valor para reiniciar la animación (botón "Reproducir").
 * @param strokeWidth grosor en unidades del SVG (KanjiVG usa ~3), se escala con el canvas.
 */
@Composable
fun AnimatedKanjiCanvas(
    strokePaths: List<String>,
    modifier: Modifier = Modifier,
    replayKey: Int = 0,
    strokeColor: Color = Color.White,
    highlightColor: Color = Color.Red,
    strokeWidth: Float = 4f
) {
    // 1. Strings SVG -> Path de Compose (los inválidos se ignoran en vez de crashear)
    val paths = remember(strokePaths) {
        strokePaths.mapNotNull { svgString ->
            runCatching {
                PathParser.createPathFromPathData(svgString).asComposePath()
            }.getOrNull()
        }
    }

    // 2. Estado de la animación
    var currentStrokeIndex by remember { mutableIntStateOf(0) }
    val strokeProgress = remember { Animatable(0f) }
    val pathMeasure = remember { PathMeasure() }

    // 3. Secuencia: un trazo tras otro. Se reinicia si cambian los trazos o replayKey.
    LaunchedEffect(paths, replayKey) {
        currentStrokeIndex = 0
        strokeProgress.snapTo(0f)

        for (i in paths.indices) {
            currentStrokeIndex = i
            strokeProgress.snapTo(0f)
            strokeProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = LinearEasing)
            )
            delay(150)
        }

        // Al terminar, todos los trazos quedan como "completados" (ninguno en rojo)
        currentStrokeIndex = paths.size
    }

    // 4. Dibujo
    Canvas(modifier = modifier.fillMaxSize()) {
        // Escala el lienzo SVG (109x109) al espacio disponible, centrado
        val scaleFactor = size.minDimension / SVG_VIEWPORT
        val offsetX = (size.width - SVG_VIEWPORT * scaleFactor) / 2f
        val offsetY = (size.height - SVG_VIEWPORT * scaleFactor) / 2f

        withTransform({
            translate(left = offsetX, top = offsetY)
            scale(scaleX = scaleFactor, scaleY = scaleFactor, pivot = Offset.Zero)
        }) {
            val completed = minOf(currentStrokeIndex, paths.size)

            // A. Trazos ya completados
            for (i in 0 until completed) {
                drawPath(
                    path = paths[i],
                    color = strokeColor,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // B. Trazo en animación
            if (currentStrokeIndex < paths.size) {
                val currentPath = paths[currentStrokeIndex]

                pathMeasure.setPath(currentPath, forceClosed = false)
                val length = pathMeasure.length

                if (length > 0f) {
                    // Revela el trazo desde el inicio: phase va de length (oculto) a 0 (completo)
                    val phase = length - (length * strokeProgress.value)

                    drawPath(
                        path = currentPath,
                        color = highlightColor,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round,
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(length, length),
                                phase = phase
                            )
                        )
                    )
                }
            }
        }
    }
}
