package com.my.puzzle;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    IPuzzle puzzle=new Puzzle(Constants.PUZZLE_ROW);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PuzzleView puzzleView=findViewById(R.id.puzzleView);
        puzzleView.setData(puzzle);

        Button btn_new=findViewById(R.id.btn_new);
        btn_new.setOnClickListener(this);

        puzzle.newGame();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_new:
                puzzle.newGame();
                break;
        }
    }
}
