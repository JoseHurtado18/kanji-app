package com.example.compose.kanji.presentation.add.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.kanji.domain.model.JlptLevel
import com.example.compose.kanji.presentation.add.FormLabel
import com.example.compose.ui.theme.ComposeTheme

@Composable
fun OptionsJplt(
    selected: JlptLevel?,
    onOptionSelected: (JlptLevel) -> Unit,
    options: List<JlptLevel> = JlptLevel.entries,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        FormLabel("Nivel JLPT")

        Row(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { level ->
                Row(
                    Modifier
                        .weight(1f)
                        .height(56.dp)
                        .selectable(
                            selected = (level == selected),
                            onClick = { onOptionSelected(level) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    FilterChip(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = SelectableChipColors(
                            containerColor = colorResource(R.color.card_secundaria),
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
                        selected = (level == selected),
                        onClick = { onOptionSelected(level) },
                        label = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = level.name, fontSize = 16.sp)
                            }
                        },
                        leadingIcon = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOptionJlpt() {
    var selected by remember { mutableStateOf<JlptLevel?>(null) }
    ComposeTheme {
        OptionsJplt(
            selected = selected,
            onOptionSelected = { selected = it },
            options = JlptLevel.entries
        )
    }
}