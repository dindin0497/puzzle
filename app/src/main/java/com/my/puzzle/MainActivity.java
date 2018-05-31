package com.my.puzzle;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    IPuzzle puzzle=new Puzzle(Constants.PUZZLE_ROW, new Puzzle.onWinListener() {
        @Override
        public void onWin() {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.win_title)
                    .setMessage(R.string.win_text)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PuzzleView puzzleView=findViewById(R.id.puzzleView);
        puzzleView.setData(puzzle);

        puzzle.shuffle();
    }
}
