package com.example.tictactoe



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import org.intellij.lang.annotations.JdkConstants


@Composable
fun TicTacToeScreen(state: GameState, vm: TicTacToeViewModel) {

    //Anadimos el tablero:
    //Para ello utilizamos el componente card con estos parametros
    /*elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)*/
    //Dentro de la card usamos otras estructuras...
    //A dichas estructuras les damos el color de fondo del surface
    //y un padding de 12.dp
    //en cada celda dibujamos el valor del state.board correspondiente
    //A cada celda le damos las siguientes caracteristicas:
    //tamano 90.dp
    //borde 1.dp y Color.Gray
    //Valores centrados y con tamano de 36.dp
    //Cuando se hace click se llama a vm.onCellClicked(index)

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                "3 en Raya",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(Modifier.height(8.dp))
        }

        item {
            ActionSelector(state.difficulty, vm)
        }

        item {
            Spacer(Modifier.height(12.dp))
        }

        item {
            VerPartidas(state.score)
        }

        item {
            Spacer(Modifier.height(20.dp))
        }

        // mostramos el mensaje que necesitemos
        item {
            val texto = when (state.winner) {
                "X" -> "¡Has ganado! 🎉"
                "O" -> "Gana la máquina 🤖"
                "Draw" -> "Empate 😐"
                else -> "Tu turno"
            }

            Text(
                text = texto,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(Modifier.height(12.dp))
        }

        // creamos el tablero
        item {

        }

        item {
            Spacer(Modifier.height(20.dp))
        }

        // mostrar botones de nueva ronda y reset
        item {
            Row {
                Button(
                    onClick = {vm.resetBoard()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Nueva ronda",
                        style = MaterialTheme.typography.labelLarge)
                }

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = {vm.resetAll()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Reset total",
                        style = MaterialTheme.typography.labelLarge)
                }
            }
        }


    }
}

// Botones de selección de dificultad
@Composable
fun ActionSelector(selected: Difficulty, vm: TicTacToeViewModel) {

    Row {
        Button(
            onClick = {vm.setDifficulty(Difficulty.EASY)},
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected == Difficulty.EASY) MaterialTheme.colorScheme.primary else Color.LightGray
            )
        ) {
            Text("Fácil",
                style = MaterialTheme.typography.labelLarge)
        }

        Spacer(Modifier.width(12.dp))

        Button(
            onClick = {vm.setDifficulty(Difficulty.HARD)},
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected == Difficulty.HARD) MaterialTheme.colorScheme.primary else Color.LightGray
            )
        ) {
            Text("Dificil",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}

// Muestra las partidas
@Composable
fun VerPartidas(selected: Score) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
        ){
            Column{
                Text(
                    "${selected.human}",
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Tú",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Column{
                Text(
                    "${selected.draws}",
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Empates",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Column{
                Text(
                    "${selected.ai}",
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "IA",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}