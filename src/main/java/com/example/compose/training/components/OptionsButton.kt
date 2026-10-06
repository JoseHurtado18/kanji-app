package com.example.compose.training.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme

val radioOptions = listOf("Calls", "Missed", "Friends", "Nada")
@Composable
fun OptionsButton(optios: List<String>, modifier: Modifier = Modifier) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    Row(
        modifier.selectableGroup()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        optios.forEach { text ->
            Row(
                Modifier
                    .weight(1f)          // <- reemplaza fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center // opcional, para centrar el chip
            ) {
                FilterChip(
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = SelectableChipColors(
                        containerColor = colorResource(R.color.grisdestacado),
                        labelColor = Color.White,
                        leadingIconColor = Color.Black,
                        trailingIconColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledLabelColor = Color.Gray,
                        disabledLeadingIconColor = Color.Gray,
                        disabledTrailingIconColor = Color.Gray,
                        selectedContainerColor = Color.White,
                        disabledSelectedContainerColor = Color.Gray,
                        selectedLabelColor = Color.Black,
                        selectedLeadingIconColor = Color.Black,
                        selectedTrailingIconColor = Color.Black
                    ),
                    selected = (text == selectedOption),
                    onClick = {},
                    label = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = text, fontSize = 16.sp)
                        }
                    },
                    leadingIcon = null
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOptionButton() {
    ComposeTheme() {
        OptionsButton(radioOptions)
    }
}