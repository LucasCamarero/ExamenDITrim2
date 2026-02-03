package com.example.tictactoe


enum class MatchResult { WIN, LOSE, DRAW }

data class GameResult(
    val id: Long = System.currentTimeMillis(),
    val result: MatchResult,
    val difficulty: Difficulty
)
