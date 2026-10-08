package com.example.compose.kanji.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.core.graphics.PathParser
import kotlinx.coroutines.delay

@Composable
fun AnimatedKanjiCanvas(
    strokePaths: List<String>,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Black,
    highlightColor: Color = Color.Red
) {
    // 1. Convertir los strings SVG a objetos Path de Compose
    val paths = remember(strokePaths) {
        strokePaths.map { svgString ->
            PathParser.createPathFromPathData(svgString).asComposePath()
        }
    }

    // 2. Estados para la animación
    var currentStrokeIndex by remember { mutableStateOf(0) }
    val strokeProgress = remember { Animatable(0f) }
    val pathMeasure = remember { PathMeasure() } // Medidor de trazos

    // 3. Lógica secuencial: animar un trazo tras otro
    LaunchedEffect(paths) {
        currentStrokeIndex = 0
        strokeProgress.snapTo(0f)

        for (i in paths.indices) {
            currentStrokeIndex = i
            strokeProgress.snapTo(0f)

            strokeProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = LinearEasing)
            )
            // Pequeña pausa entre trazos para que se vea natural
            delay(150)
        }
    }

    // 4. Dibujar en el Canvas
    Canvas(modifier = modifier.fillMaxSize()) {
        // A. Dibujar los trazos que ya se completaron
        for (i in 0 until currentStrokeIndex) {
            drawPath(
                path = paths[i],
                color = strokeColor,
                style = Stroke(
                    width = 12f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        // B. Dibujar el trazo actual que se está animando
        if (currentStrokeIndex < paths.size) {
            val currentPath = paths[currentStrokeIndex]

            // Medir la longitud total del trazo actual
            pathMeasure.setPath(currentPath, forceClosed = false)
            val length = pathMeasure.length

            // Crear el efecto de revelado (DashEffect)
            // El 'phase' empuja el espacio en blanco para ocultar la parte no dibujada
            val phase = length - (length * strokeProgress.value)

            drawPath(
                path = currentPath,
                color = highlightColor, // Se dibuja de otro color para resaltar el orden
                style = Stroke(
                    width = 12f,
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