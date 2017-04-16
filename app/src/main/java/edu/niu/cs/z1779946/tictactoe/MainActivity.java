package edu.niu.cs.z1779946.tictactoe;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button [][]buttons;
    private TicTacToe game;
    private TextView gameStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        game = new TicTacToe();
        buildGUI();

    }//end of onCreate

    public void buildGUI(){
        //get the screenwidth
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x / TicTacToe.SIDE;

        //Create a gridlayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE+1); //added an extra row to show a text field

        //Create the button array
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];

        //Create a ButtonHandler object
        ButtonHandler buttonHandler = new ButtonHandler();

        //add the buttons to the grid layout
        for (int row = 0; row < TicTacToe.SIDE; row++){
            for (int col = 0; col < TicTacToe.SIDE; col++){
                buttons[row][col] = new Button(this);

                buttons[row][col].setTextSize((int) (screenWidth * 0.2));
                //buttons[row][col].setTextSize((int) (screenWidth * 0.2));
                buttons[row][col].setOnClickListener(buttonHandler);

                gridLayout.addView(buttons[row][col], screenWidth, screenWidth);
            }
        }

        gameStatus = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(TicTacToe.SIDE, 1),
                colSpec = GridLayout.spec(0, TicTacToe.SIDE);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);

        gameStatus.setLayoutParams(layoutParams);
        gameStatus.setWidth(TicTacToe.SIDE * screenWidth);
        gameStatus.setHeight(screenWidth);
        gameStatus.setGravity(Gravity.CENTER);
        gameStatus.setBackgroundColor(Color.CYAN);
        gameStatus.setTextSize((int)(screenWidth * 0.15));
        gameStatus.setText(game.result());
        gridLayout.addView(gameStatus);

        setContentView(gridLayout);

    }//end of buildGUI

    public void update(int row, int col){
//        Toast.makeText(MainActivity.this, "Update " + row + ", " + col, Toast.LENGTH_SHORT).show();
//        buttons[row][col].setText("X");

        //Determine which player is playing
        int currentPlayer = game.play(row, col);

        if(currentPlayer == 1){
            buttons[row][col].setText("X");
        } else if(currentPlayer == 2){
            buttons[row][col].setText("O");
        }

        if(game.isGameOver()){
            gameStatus.setBackgroundColor(Color.GREEN);
            enableButtons(false);
            gameStatus.setText(game.result());

            showNewDialog();
        }
    }//end of update

    public void enableButtons(boolean enabled){
        for (int row = 0; row < TicTacToe.SIDE; row++){
            for (int col = 0; col < TicTacToe.SIDE; col++) {
                buttons[row][col].setEnabled(enabled);
            }
        }
    }//end of enabledButtons

    private class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            Toast.makeText(MainActivity.this, " ButtonHandler " +v,Toast.LENGTH_SHORT).show();

            for (int row = 0; row < TicTacToe.SIDE; row++){
                for (int col = 0; col < TicTacToe.SIDE; col++){
                    if(v == buttons[row][col]){
                        update(row, col);
                    }
                }//end of col for
            }//end of row for
        }//end of onClick
    }//end of class buttonhandler

    public void resetButtons(){
        for (int row = 0; row < TicTacToe.SIDE; row++){
            for (int col = 0; col < TicTacToe.SIDE; col++){
                buttons[row][col].setText("");
            }
        }
    }

    public void showNewDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Tic Tac Toe");
        alert.setMessage("Play again?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.resetGame();
                enableButtons(true);
                resetButtons();
                gameStatus.setBackgroundColor(Color.CYAN);
                gameStatus.setText(game.result());
            }
        });//end of YES onClick

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });//end of NO onClick

        //make the dialog show up
        alert.show();

    }//end of showNewDialog

}//end o MainActivity