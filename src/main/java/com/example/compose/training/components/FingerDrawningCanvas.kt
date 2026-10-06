package com.example.compose.training.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.Roboto
import com.example.compose.ui.theme.RobotoMono

@Composable
fun FingerDrawingCanvas() {
    val lines = remember { mutableStateListOf<Pair<Path, Color>>() }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentColor by remember { mutableStateOf(Color.Black) }

    // 1. Envolvemos todo en un Box para superponer el Texto y el Canvas
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = colorResource(R.color.grisdestacado),
                shape = RoundedCornerShape(roundedCornerShapeValue)
            ),
        contentAlignment = Alignment.Center // 2. Centra el contenido (el texto)
    ) {

        // 3. Mostramos el texto SOLO si no hay nada dibujado
        if (lines.isEmpty()) {
            Text(
                text = "Toca para escribir con el dedo",
                color = Color.Gray, // Puedes ajustar el color para que sea sutil
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = RobotoMono
            )
        }

        // 4. El Canvas ahora ocupa el tamaño total del Box padre
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPath = Path().apply {
                                moveTo(offset.x, offset.y)
                            }
                            lines.add(Pair(currentPath, currentColor))
                        },
                        onDrag = { change, _ ->
                            val position = change.position
                            currentPath.lineTo(position.x, position.y)
                            lines[lines.lastIndex] = Pair(currentPath, currentColor)
                        }
                    )
                }
        ) {
            lines.forEach { (path, color) ->
                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(width = 8f)
                )
            }
        }
    }
}