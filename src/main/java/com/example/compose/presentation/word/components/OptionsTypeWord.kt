package com.example.compose.presentation.word.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
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
import com.example.compose.domain.model.JlptLevel
import com.example.compose.domain.model.TypeWord
import com.example.compose.presentation.kanji.add.FormLabel
import com.example.compose.ui.theme.ComposeTheme
import kotlin.collections.forEach

@Composable
fun OptionsTypeWord(
    selected: TypeWord?,
    onOptionSelected: (TypeWord) -> Unit,
    options: List<TypeWord> = TypeWord.entries,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        FormLabel("Tipo de palabra ")

        Row(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { type ->
                Row(
                    Modifier
                        .weight(1f)
                        .height(56.dp)
                        .selectable(
                            selected = (type == selected),
                            onClick = { onOptionSelected(type) },
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
                        selected = (type == selected),
                        onClick = { onOptionSelected(type) },
                        label = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = type.name, fontSize = 14.sp)
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
fun PreviewOptionType() {
    var selected by remember { mutableStateOf<TypeWord?>(null) }
    ComposeTheme {
        OptionsTypeWord(
            selected = selected,
            onOptionSelected = { selected = it },
            options = TypeWord.entries
        )
    }
}


