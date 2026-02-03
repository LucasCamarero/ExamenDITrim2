package com.example.tictactoe


import kotlin.math.max
import kotlin.math.min

object GameLogic {

    private val winLines = listOf(
        listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
        listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
        listOf(0,4,8), listOf(2,4,6)
    )

    fun checkWinner(board: List<String>): String? {
        for (line in winLines) {
            val (a,b,c) = line
            if (board[a].isNotEmpty() && board[a] == board[b] && board[a] == board[c]) {
                return board[a]
            }
        }
        return if (board.none { it.isEmpty() }) "Draw" else null
    }

    fun aiMove(board: List<String>, difficulty: Difficulty): List<String> {
        return when (difficulty) {
            Difficulty.EASY -> easyMove(board)
            Difficulty.HARD -> minimaxMove(board)
        }
    }

    // ---------- EASY MODE ----------
    private fun easyMove(board: List<String>): List<String> {
        val empty = board.indices.filter { board[it].isEmpty() }

        // ganar
        for (i in empty) {
            val test = board.toMutableList()
            test[i] = "O"
            if (checkWinner(test) == "O") return test
        }

        // bloquear
        for (i in empty) {
            val test = board.toMutableList()
            test[i] = "X"
            if (checkWinner(test) == "X") {
                return board.toMutableList().also { it[i] = "O" }
            }
        }

        // random
        val move = empty.random()
        return board.toMutableList().also { it[move] = "O" }
    }

    // ---------- HARD MODE (MINIMAX) ----------
    private fun minimaxMove(board: List<String>): List<String> {
        var bestScore = Int.MIN_VALUE
        var bestMove = -1

        for (i in board.indices) {
            if (board[i].isEmpty()) {
                val newBoard = board.toMutableList()
                newBoard[i] = "O"
                val score = minimax(newBoard, false)
                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }

        return board.toMutableList().also { it[bestMove] = "O" }
    }

    private fun minimax(board: List<String>, isMaximizing: Boolean): Int {
        when (checkWinner(board)) {
            "O" -> return 10
            "X" -> return -10
            "Draw" -> return 0
        }

        if (isMaximizing) {
            var best = Int.MIN_VALUE
            for (i in board.indices) {
                if (board[i].isEmpty()) {
                    val newBoard = board.toMutableList()
                    newBoard[i] = "O"
                    best = max(best, minimax(newBoard, false))
                }
            }
            return best
        } else {
            var best = Int.MAX_VALUE
            for (i in board.indices) {
                if (board[i].isEmpty()) {
                    val newBoard = board.toMutableList()
                    newBoard[i] = "X"
                    best = min(best, minimax(newBoard, true))
                }
            }
            return best
        }
    }
}
