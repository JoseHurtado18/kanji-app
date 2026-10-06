package com.example.compose.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.home.components.HeaderGlobal
import com.example.compose.stats.components.GridStats
import com.example.compose.ui.theme.ComposeTheme


@Composable
fun StatsScreen(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)
            .padding(24.dp)

    ) {
        HeaderGlobal("", "Estadísticas", "")
        GridStats(12, 87)

    }
}

@Preview
@Composable
fun PreviewStatsScreen(){
    ComposeTheme() {
        StatsScreen()
    }
}
