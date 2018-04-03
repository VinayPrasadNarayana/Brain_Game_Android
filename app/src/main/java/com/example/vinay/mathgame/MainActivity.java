package com.example.vinay.mathgame;
//required Imports

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebStorage;

public class MainActivity extends Activity {
    static int difficulty; //static variable for difficulty
    static int hintvalue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //set content
    }

    // Starting the game
    public void Play(View V) {
        openNewGameDialog(); // starts the game

    }

    public void Continue(View v) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Intent intent = new Intent(getBaseContext(), PlayActivity.class);


        intent.putExtra("isContinueButtonClicked", true); // set the value of iscontinuecliked to true
        intent.putExtra("Difficulty", sp.getInt("Difficulty", 0)); // set the difficulty level
        intent.putExtra("TimeLeft", sp.getLong("TimeLeft", 0));
        intent.putExtra("Question", sp.getString("Question", "Question")); // sets the question
        intent.putExtra("Answer", sp.getString("Answer", "Answer")); // sets the answer
        intent.putExtra("Result", sp.getString("Result", "RESULT")); // set the r3esult
        intent.putExtra("QuestionNumber", sp.getInt("QuestionNumber", 1)); // set the number of question remaining

        startActivity(intent);
    }


    public void about(View V) { //when the about button is clicked
        AlertDialog.Builder builder = new AlertDialog.Builder(com.example.vinay.mathgame.MainActivity.this);
        builder.setTitle("About"); //title
        builder.setMessage(" 1. You have to answer a question with in 10 seconds and press # to start and commit the answer." +
                "\n\n 2. If you do not submit any answer by the end of the time the page will take you to the next question and zero points will be given." +
                "\n\n 3. 100 / (10−time remaining ) marks for each correct guess. time remaining is the value of the countdown timer when the user pressed the # button to submit the answer"); //message displayed
        builder.setCancelable(false);
        //setting the ok button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void Yes(View V) {
        hintvalue = 1;
    }


    public void Exit(View V) { //when the exit button is clicked
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Would you like to save the game data?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }


    private static final String TAG = "BrainGame";

    private void openNewGameDialog() {
        //opens new game
        new AlertDialog.Builder(this)
                .setTitle("Select Level")
                .setItems(R.array.difficulty,                        // sets items array difficulty
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface
                                                        dialoginterface, int i) {
                                startGame(i);
                            }
                        })
                .show();
    }

    private void startGame(int i) {                    // method for to start game

        switch (i) {                                // switch statement for I

            case 0:                                                                // case 0 for novice
                Intent intent = new Intent(com.example.vinay.mathgame.MainActivity.this, PlayActivity.class);
                startActivity(intent);                                            //starts activity novice
                difficulty = 0;
                break;

            case 1:                                                                // case 1 for easy
                Intent easy = new Intent(com.example.vinay.mathgame.MainActivity.this, PlayActivity.class);
                startActivity(easy);                                            //starts activity easy
                difficulty = 1;
                break;

            case 2:                                                                // case 2 for medium
                Intent medium = new Intent(com.example.vinay.mathgame.MainActivity.this, PlayActivity.class);
                startActivity(medium);                                            //starts activity medium
                difficulty = 2;
                break;

            case 3:                                                                // case 3 for novice
                Intent guru = new Intent(com.example.vinay.mathgame.MainActivity.this, PlayActivity.class);
                startActivity(guru);                                            // starts activity guru
                difficulty = 3;
                break;
        }
    }
}


