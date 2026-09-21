package com.example.menutictac.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menutictac.R;
import com.example.menutictac.models.TicTacToeModel;
import com.example.menutictac.services.SignalRService;

// הוספת אימפורט מעל המחלקה

public class Main2Activity extends AppCompatActivity {
    private TicTacToeModel model;

    private final SignalRService signalRService = new SignalRService();
    private static final String TAG = "MainActivity";

    private int idFor(int row, int col) {
        if (row == 0 && col == 0) return R.id.button00;
        if (row == 0 && col == 1) return R.id.button01;
        if (row == 0 && col == 2) return R.id.button02;
        if (row == 1 && col == 0) return R.id.button10;
        if (row == 1 && col == 1) return R.id.button11;
        if (row == 1 && col == 2) return R.id.button12;
        if (row == 2 && col == 0) return R.id.button20;
        if (row == 2 && col == 1) return R.id.button21;
        if (row == 2 && col == 2) return R.id.button22;
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new TicTacToeModel();
    }

    public void onCellClick(View view) {
        Button button = (Button) view;
        String tag = button.getTag().toString();
        String[] position = tag.split(",");
        int row = Integer.parseInt(position[0]);
        int col = Integer.parseInt(position[1]);

        if (model.isLegal(row, col)) {
            model.makeMove(row, col);
            button.setText(model.getCurrentPlayer());

            if (model.checkWin()) {
                model.changePlayer();
                Toast.makeText(this, "Player " + model.getCurrentPlayer() + " wins!", Toast.LENGTH_SHORT).show();
                model.resetGame();
                resetBoard();
            } else if (model.isTie()) {
                Toast.makeText(this, "It's a tie!", Toast.LENGTH_SHORT).show();
                model.resetGame();
                resetBoard();
            } else {
                model.changePlayer();
            }
        }
    }

    private void resetBoard() {
        int[] buttonIds = {
                R.id.button00, R.id.button01, R.id.button02,
                R.id.button10, R.id.button11, R.id.button12,
                R.id.button20, R.id.button21, R.id.button22
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setText("");
        }
    }
}
