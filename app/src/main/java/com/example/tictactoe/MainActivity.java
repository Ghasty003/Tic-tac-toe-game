package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore;
    private MaterialButton resetBtn;
    private int playerOneScoreCount, playerTwoScoreCount, cellCount;
    private TextView[] textViews = new TextView[9];
    private String currentPlayer;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.playerOneScore);
        playerTwoScore = findViewById(R.id.playerTwoScore);
        resetBtn = findViewById(R.id.resetBtn);

        resetBtn.setOnClickListener( view -> {
            resetGame();
        });

        currentPlayer = "X";
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;

        for (int i = 0; i < textViews.length; i++) {
            String textID = "cell_" + i;
            int resourceID = getResources().getIdentifier(textID, "id", getPackageName());
            textViews[i] = findViewById(resourceID);
            textViews[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if(!((TextView) view).getText().toString().equals("")) {
            return;
        }

        String textID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(textID.substring(textID.length() - 1));

        if (currentPlayer.equals("X")) {
            ((TextView) view).setText("X");
            gameState[gameStatePointer] = 0;
            ((TextView) view).setTextColor(Color.parseColor("#FFc34a"));
        } else if (currentPlayer.equals("O")) {
            ((TextView) view).setText("O");
            gameState[gameStatePointer] = 1;
            ((TextView) view).setTextColor(Color.parseColor("#70ffEA"));
        }

        if (checkWinner()) {
            if (currentPlayer.equals("X")) {
                playerOneScoreCount++;
                Toast.makeText(this, "Player X won", Toast.LENGTH_SHORT).show();
            } else if (currentPlayer.equals("O")) {
                playerTwoScoreCount++;
                Toast.makeText(this, "Player O won", Toast.LENGTH_SHORT).show();
            }
            updateScore();
            resetGame();
        } else {
            if (currentPlayer.equals("X")) {
                currentPlayer = "O";
            } else if (currentPlayer.equals("O")) {
                currentPlayer = "X";
            }
        }
    }


    public boolean checkWinner() {
        boolean winningResult = false;
        for (int[] winnerPosition : winningPositions) {
            if (gameState[winnerPosition[0]] == gameState[winnerPosition[1]] &&
                    gameState[winnerPosition[1]] == gameState[winnerPosition[2]]
                    && gameState[winnerPosition[0]] != 2
            ) {
                winningResult = true;
            }
        }

        return winningResult;
    }

    public void updateScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void resetGame() {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = () -> {
           currentPlayer = "X";
            for (int i = 0; i < textViews.length; i++) {
                textViews[i].setText("");
                gameState[i] = 2;
            }
        };
        handler.postDelayed(runnable, 500);
    }
}