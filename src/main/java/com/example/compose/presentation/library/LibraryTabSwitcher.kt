package com.example.compose.presentation.library

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue



@Composable
fun LibraryTabSwitcher(
    selected: LibraryTab,
    onSelect: (LibraryTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(3.dp)
            .selectableGroup()
    ) {
        LibraryTab.entries.forEach { tab ->
            val isSelected = tab == selected

            val background by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.surface
                else Color.Transparent,
                label = "tabBackground"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "tabText"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(background)
                    .selectable(
                        selected = isSelected,
                        onClick = { onSelect(tab) },
                        role = Role.Tab
                    )
                    .padding(vertical = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(tab.labelRes),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = textColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryTabSwitcherPreview() {
    var selected by remember { mutableStateOf(LibraryTab.WORDS) }
    MaterialTheme {
        LibraryTabSwitcher(
            selected = selected,
            onSelect = { selected = it },
            modifier = Modifier.padding(20.dp)
        )
    }
}