package com.example.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryScreen(state: GameState, vm: TicTacToeViewModel) {

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                "Historial de partidas",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(18.dp))

            if(state.history.isEmpty()) {
                Text(
                    "Aún no hay partidas",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        item {
            LazyColumn() {
                state.history.forEach { hist ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ){
                            Row {
                                val color = when (hist.result) {
                                    MatchResult.WIN -> Color(0xFF4CAF50)
                                    MatchResult.LOSE -> Color(0xFFF44336)
                                    MatchResult.DRAW -> Color(0xFFFFC107)
                                }

                                Text(
                                    "${hist.result}",
                                    color = color,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                //containerColor = if (selected == ActionMode.MOVE) Color(0xFF4CAF50) else Color.LightGray
                                Spacer(Modifier.width(18.dp))

                                Text(
                                    "${hist.difficulty}",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}