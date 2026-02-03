package com.example.tictactoe



enum class Player { HUMAN, AI }
enum class Difficulty { EASY, HARD }

data class Score(
    val human: Int = 0,
    val ai: Int = 0,
    val draws: Int = 0
)


data class GameState(
    val board: List<String> = List(9) { "" },
    val currentTurn: Player = Player.HUMAN,
    val winner: String? = null,
    val difficulty: Difficulty = Difficulty.EASY,
    val score: Score = Score(),
    val history: List<GameResult> = emptyList()
)
