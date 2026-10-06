package com.example.compose.library.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    // Definimos la paleta de colores para el campo de entrada
    val customInputFieldColors = TextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.LightGray,
        focusedLeadingIconColor = Color(0xFFFFB703),
        unfocusedLeadingIconColor = Color(0xFFFFB703), // Dorado también en reposo
        focusedPlaceholderColor = Color.Gray,
        unfocusedPlaceholderColor = Color.LightGray,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = if (expanded) 0.dp else 90.dp)
                .padding(horizontal = if (expanded) 0.dp else 16.dp)
                .semantics { traversalIndex = 0f },
            shape = RoundedCornerShape(16.dp), // Cambia a tus esquinas preferidas
            colors = SearchBarDefaults.colors(
                containerColor = colorResource(R.color.grisdestacado),
                dividerColor = colorResource(R.color.kvinegro)
            ),
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = {
                        Text("Buscar kanjis...", fontSize = 16.sp)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = if (expanded) Icons.Default.ArrowBack else Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    trailingIcon = {
                        if (expanded && textFieldState.text.isNotEmpty()) {
                            IconButton(onClick = { textFieldState.edit {  } }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpiar")
                            }
                        }
                    },
                    // 🔑 AQUÍ ES DONDE SE APLICAN LOS COLORES DEL INPUT:
                    colors = customInputFieldColors
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                searchResults.forEach { result ->
                    ListItem(
                        headlineContent = { Text(result, color = Color.White) },
                        leadingContent = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                        modifier = Modifier
                            .clickable {
                                textFieldState.edit { replace(0, length, result) }
                                expanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleSearchBarExample(modifier: Modifier = Modifier) {
    val textFieldState = rememberTextFieldState()
    val items = listOf(
        "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb",
        "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop"
    )

    val filteredItems by remember {
        derivedStateOf {
            val searchText = textFieldState.text.toString()
            if (searchText.isEmpty()) {
                emptyList()
            } else {
                items.filter { it.contains(searchText, ignoreCase = true) }
            }
        }
    }

    SimpleSearchBar(
        textFieldState = textFieldState,
        onSearch = { /* Manejar búsqueda */ },
        searchResults = filteredItems,
        modifier = modifier
    )
}




@Preview
@Composable
fun PreSearch(){
    ComposeTheme() {
        SimpleSearchBarExample()
    }
}