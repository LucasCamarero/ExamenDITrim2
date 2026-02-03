package com.example.tictactoe



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicTacToeViewModel : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state

    fun onCellClicked(index: Int) {
        val current = _state.value
        if (current.board[index].isNotEmpty()) return
        if (current.winner != null) return
        if (current.currentTurn != Player.HUMAN) return

        val newBoard = current.board.toMutableList().also { it[index] = "X" }
        val winner = GameLogic.checkWinner(newBoard)

        _state.value = current.copy(
            board = newBoard,
            currentTurn = Player.AI,
            winner = winner
        )

        if (winner == null) aiTurn()
        else updateScore(winner)
    }

    private fun aiTurn() {
        viewModelScope.launch {
            delay(400)

            val current = _state.value
            val newBoard = GameLogic.aiMove(current.board, current.difficulty)
            val winner = GameLogic.checkWinner(newBoard)

            _state.value = current.copy(
                board = newBoard,
                currentTurn = Player.HUMAN,
                winner = winner
            )

            if (winner != null) updateScore(winner)
        }
    }

    fun setDifficulty(difficulty: Difficulty) {
        _state.value = GameState(
            difficulty = difficulty,
            score = _state.value.score,
            history=_state.value.history
        )
    }

    fun resetBoard() {
        _state.value = _state.value.copy(
            board = List(9) { "" },
            currentTurn = Player.HUMAN,
            winner = null
        )
    }

    fun resetAll() {
        _state.value = GameState(difficulty = _state.value.difficulty)
    }

    fun updateScore(result: String) {
        val score = _state.value.score

        val match = when (result) {
            "X" -> MatchResult.WIN
            "O" -> MatchResult.LOSE
            else -> MatchResult.DRAW
        }

        val newScore = when (result) {
            "X" -> score.copy(human = score.human + 1)
            "O" -> score.copy(ai = score.ai + 1)
            else -> score.copy(draws = score.draws + 1)
        }

        _state.value = _state.value.copy(
            score = newScore,
            history = listOf(
                GameResult(result = match, difficulty = _state.value.difficulty)
            ) + _state.value.history
        )
    }

}

