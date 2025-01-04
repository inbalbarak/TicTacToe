package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.size

class MainActivity : ComponentActivity() {
    var win : Boolean? = null;
    var currentPlayer = "X"
    val board = Array(3) { Array(3) { "" } }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val firstRow: TableRow = findViewById(R.id.row1)
        val secondRow: TableRow = findViewById(R.id.row2)
        val thirdRow: TableRow = findViewById(R.id.row3)

        val rows = arrayOf(firstRow, secondRow,thirdRow)
        val resetBtn : Button = findViewById(R.id.resetBtn)

        resetBtn.setOnClickListener{
            win =null
            currentPlayer = "X"
            for (i in 0..2) {
                for (j in 0..2) {
                    board[i][j] = ""
                }
            }

            for(row in rows){
                for (i in 0 until row.childCount) {
                    val button = row.getChildAt(i) as Button
                    button.text = null
                    button.isClickable = true
                }
            }


            it.visibility = View.INVISIBLE
            var turnsText : TextView = findViewById(R.id.turnTurns)
            turnsText.setText("Player X")
        }

        var rowNum = 0

        for(row in rows){
            for (col in 0 until row.size) {
                val button = row.getChildAt(col) as Button
                val row = rowNum

                button.setOnClickListener {
                    if(win != true){
                        if (button.text.isEmpty()) {
                            board[row][col] = currentPlayer
                            button.text = currentPlayer
                            if(checkWin()){
                                win = true
                                toggleResetVisable(findViewById(R.id.resetBtn))
                                handleText(findViewById(R.id.turnTurns))
                                button.isClickable=false
                            }
                            else if(isBoardFull()) {
                                win= false
                                toggleResetVisable(findViewById(R.id.resetBtn))
                                handleText(findViewById(R.id.turnTurns))
                                button.isClickable=false
                            }else {
                                button.isClickable=false
                                currentPlayer = if (currentPlayer == "X") "O" else "X"

                            }
                        }

                        if(win == null) togglePlayer(findViewById(R.id.turnTurns))
                    }
                }
            }
            rowNum++
        }
    }


    private fun checkWin(): Boolean {
        for (index in 0..2) {
            if (board[index][0] == currentPlayer && board[index][1] == currentPlayer && board[index][2] == currentPlayer) return true
            if (board[0][index] == currentPlayer && board[1][index] == currentPlayer && board[2][index] == currentPlayer) return true
        }

        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell.isEmpty()) return false
            }
        }
        return true
    }


    fun handleText (textField: TextView){
        if(win == false) textField.setText("Tie")
        else textField.setText(currentPlayer + " is the winner")
    }

    fun toggleResetVisable (resetBtn: Button){
        if(resetBtn.visibility == View.VISIBLE) resetBtn.setVisibility(View.VISIBLE) else resetBtn.setVisibility(View.VISIBLE);
    }

    fun togglePlayer (player: TextView){
        if(player.text.contains("X")) player.setText("Player O") else player.setText("Player X")
    }
}