package com.example.vinay.mathgame;

// all the required imports

import android.app.Activity;
//import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
//import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.util.Log;
//import android.view.Gravity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.Toast;
import android.widget.TextView;

import java.util.Random;


public class PlayActivity extends Activity {
    //boolean to store if the continue button clicked
    Boolean isContinueClicked;

    // for saving data
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //remaining time
    long secondsLeft;
    int Total; // Puts total answer in here
    Random random1 = new Random(); //Generates random
    // gets a random number
    int number1 = random1.nextInt(10);
    int number2 = random1.nextInt(10);
    int number3 = random1.nextInt(60);
    int number4 = random1.nextInt(60);
    int number5 = random1.nextInt(90);
    int number6 = random1.nextInt(90);

    int selectnumber = random1.nextInt(4); // 4 signs declared
    CountDownTimer time; // This extends the object
    static Button Hash; // static button declared
    TextView timing; // timing
    static int score; // variable for score
    int counterscore = 0; // variable to count score


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        //checks if countinue button is clicked
        Intent intent = getIntent();
        isContinueClicked = intent.getBooleanExtra("isContinueButtonClicked", false);

        // if continue button is clicked
        if (isContinueClicked) {
            TextView question = (TextView) findViewById(R.id.Question); //puts questions in here
            TextView answer = (TextView) findViewById(R.id.answer); // displays question mark and user puts answer in here
            TextView display = (TextView) findViewById(R.id.answervalue); //displays if its correct or incorrect
            TextView timing = (TextView) findViewById(R.id.timer); //This where the timer shows
            MainActivity.difficulty = intent.getIntExtra("Difficulty", 0); //gets the difficulty level
            question.setText(intent.getStringExtra("Question")); // gets the questiion
            Total = Integer.parseInt(intent.getStringExtra("Answer")); //gets the answer
            //display.setText(intent.getStringExtra("Result"));
            //timing.setText(intent.getLongExtra("TimeLeft",0));
            counterscore = intent.getIntExtra("QuestionNumber", 0); // gets the questions remaining
            counterscore--;

time();
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();

        // when the hash button is clicked
        Hash = (Button) findViewById(R.id.hash); //Hash button
        Hash.setOnClickListener(new OnClickListener() { //Hash onclick listener method

            public void onClick(View A) { //method
                if (counterscore != 0 ) {
                    time.cancel(); //methods for time cancel
                }
                time(); //start timer

                TextView question = (TextView) findViewById(R.id.Question); //puts questions in here
                TextView answer = (TextView) findViewById(R.id.answer); // displays question mark and user puts answer in here
                TextView display = (TextView) findViewById(R.id.answervalue);
                // TextView Score = (TextView) findViewById(R.id.score);//displays if its correct or incorrect
                String compvalue1 = answer.getText().toString();  //compares the value to string
                String compvalue2 = Integer.toString(Total);

                if (counterscore != 0|| isContinueClicked) {
                    if (compvalue1.equals(compvalue2)) { //if statement compares user answer to actual answer
                        display.setText("CORRECT!");
                        timing = (TextView) findViewById(R.id.timer); //Puts the timer in here
                        int scoring_display = Integer.parseInt(timing.getText().toString());
                        score = score + (100 / 10 - scoring_display); // calculate the score

                        answer.setText("?"); // displays a question mark
                        display.setTextColor(Color.GREEN); //sets the text colour to green

                    } else {
                        display.setText("WRONG!"); //displays wrong
                        answer.setText("?"); // displays a question mark
                        display.setTextColor(Color.RED); //puts the text in green

                    }

                }
                counterscore++; // counter for score
                if (counterscore == 10) {
                    time.cancel();
                    question.setText("0 + 0"); // sets the question back to 0 + 0
                    answer.setText("?"); // sets the answer back to ?
                    display.setText("GoodJob"); //sets display to Goodjob
                    display.setTextColor(Color.GREEN); // sets color to green
                    AlertDialog.Builder builder = new AlertDialog.Builder(com.example.vinay.mathgame.PlayActivity.this);
                    builder.setTitle("Score"); //title
                    builder.setMessage("Your Score is " + score); //message displayed
                    builder.setCancelable(false);
                    //setting the ok button
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            finish();
                            startActivity(intent); // get back to main menu
                        }
                    });
                    builder.show();
                }
                Random ran = new Random();

                // gets a random number from 10 - 90 numbers for variables
                number1 = ran.nextInt(10);
                number2 = ran.nextInt(10);
                number3 = ran.nextInt(60);
                number4 = ran.nextInt(60);
                number5 = ran.nextInt(90);
                number6 = ran.nextInt(90);
                char[] operator = {'+', '-', '/', '*'}; // declared 4 operators
                char operationsrandom = operator[new Random().nextInt(4)]; // gets randomly operators
                if (MainActivity.difficulty == 0) { //this is calling difficulty level Guru from MainActivity.java this is Switch case 0

                    operationsrandom = operator[new Random().nextInt(4)]; //selects random operators from 4 expressions
                    switch (operationsrandom)  //switch statement

                    {

                        case '+': // do addition
                            Total = number1 + number2; //does the total
                            question.setText(String.valueOf(number1 + " " + "+" + " " + number2 + " ="));
                            break;  //break

                        case '-': // does minus
                            Total = number1 - number2; //does the total
                            question.setText(String.valueOf(number1 + " " + "-" + " " + number2 + " ="));

                        case '/': //does division
                            if (number1 == 0 || number2 == 0) {
                                number1 = number1 + (1); //add 1 to this random number
                                number2 = number2 + (1);
                            } // add 1 to random number 2
                            Total = (number1 / number2);//does the total
                            if (Total < 1) {
                                Total = 1;
                            }
                            //Score.setText(String.valueOf(Total));
                            question.setText(String.valueOf(number1 + " " + "/" + " " + number2 + " ="));
                            break;

                        case '*': //this does multiplication
                            Total = number1 * number2; //multiply random number 1 by random number 2
                            question.setText(String.valueOf(number1 + " " + "*" + " " + number2 + " ="));
                            break;
                    }
                }

                if (MainActivity.difficulty == 1) { //this is calling difficulty level Guru from MainActivity.java this is Switch case 1
                    switch (operationsrandom) {

                        case '+': // do addition
                            Total = number1 + number2 - number4;//does the total
                            question.setText(String.valueOf(number1 + "+" + number2 + "-" + number4 + "="));
                            break; //break

                        case '-': //do take away
                            Total = number1 - number2 * number3;//does the total
                            question.setText(String.valueOf(number1 + "-" + number2 + "-" + number3 + "="));
                            break; //break

                        case '/': // do revision
                            if (number1 == 0|| number2 == 0) {
                                number1 = number1 + (1);  //adds 1 to number1 if 0
                                number2 = number2 + (1);
                            }    // adds 1 to number2

                            Total = number1 / number2; // two random numbers dividing
                            if (Total < 1) {
                                Total = 1;
                            }
                            question.setText(String.valueOf(number1 + "/" + number2 + "="));
                            break; //break

                        case '*': // does multiplication
                            Total = number1 * number2; //multiply the 2 random numbers
                            question.setText(String.valueOf(number1 + "*" + number2 + "="));
                            break; //break
                    }
                }


                if (MainActivity.difficulty == 2) { //this is calling difficulty level Guru from MainActivity.java this is Switch case 2

                    switch (operationsrandom) {

                        case '+': // do addition
                            Total = number1 + number2 / number3;
                            question.setText(String.valueOf(number1 + "+" + number2 + "/" + number3 + "="));
                            break; //break

                        case '-': // do minus
                            if (number1 == 0|| number2 == 0) {
                                number1 = number1 + (1);    //adds 1 to number1 if 0
                                number2 = number2 + (1);
                            }    //adds 1 to number2 if 0
                            int minitotal;
                            minitotal = number1 / number2;
                            if (minitotal < 1) {
                                minitotal = 1;
                            }
                            Total = minitotal * number3 + number4; //does the total
                            question.setText(String.valueOf(number1 + "/" + number2 + "*" + number3 + "+" + number4 + "="));
                            break; //break

                        case '/': // do divide
                            if (number1 == 0|| number2 == 0) {
                                number1 = number1 + (1); //adds 1 to number1 if 0
                                number2 = number2 + (1);
                            } //adds 1 to number2 if 0
                            Total = number1 / number2;//does the total
                            if (Total < 1) {
                                Total = 1;
                            }
                            question.setText(String.valueOf(number1 + "/" + number2 + " =")); //displays the questions
                            break; //break

                        case '*': // do multiplication
                            Total = number3 * number2 - number1;//does the total
                            question.setText(String.valueOf(number3 + "*" + number2 + "-" + number1 + "=")); //displays the question
                            break;    //break

                    }
                }

                if (MainActivity.difficulty == 3) { //this is calling difficulty level Guru from MainActivity.java this is Switch case 3

                    switch (operationsrandom) { // Switch statement

                        case '+': // do addition
                            Total = number1 + number2 * number5 - number4; // getting number1 number2 number5 number4 randomly doing required expression
                            question.setText(String.valueOf(number1 + "+" + number2 + "*" + number5 + "-" + number4 + "=")); //displays the question
                            break; //break

                        case '-': // do minus
                            if (number1 == 0|| number2 == 0) { //if statement for random number 1 and number 2 if equals to 0 plus 1
                                number1 = number1 + (1);
                                number2 = number2 + (1);//adds 1 to random number 1
                            } //adds 1 to random number2

                            int minitotal;
                            minitotal = number1 / number2;
                            if (minitotal < 1) {
                                minitotal = 1;
                            }

                            Total = minitotal * number6 + number5 * number4; // getting number1 number2 number6 number5 number4 randomly and doing required expression
                            question.setText(String.valueOf(number1 + "/" + number2 + "*" + number6 + "+" + number5 + "*" + number4 + "=")); //displays question
                            break; //break

                        case '/': // do divide
                            if (number1 == 0 || number2 == 0) { //if statement for random number 1 and number 2 if equals to 0 plus 1
                                number1 = number1 + (1); //adds 1 to random number 1
                                number2 = number2 + (1);
                            } //adds 1 to random number 2

                            minitotal = number1 / number2;
                            if (minitotal < 1) {
                                minitotal = 1;
                            }
                            Total = minitotal * number4 * number5 + number3 - number6; // getting number1 number2 number4 number5 number3 number6 randomly and doing required expression
                            question.setText(String.valueOf(number1 + "/" + number2 + "*" + number4 + "*" + number5 + "+" + number3 + "-" + number6 + "=")); //displays the question
                            break; //break

                        case '*': // do multiply
                            Total = number6 * number5 - number4 + number3; // getting number6 number5 number4 number3 randomly and doing required expression
                            question.setText(String.valueOf(number6 + "*" + number5 + "-" + number4 + "+" + number3 + "=")); //displays question
                            break; //break
                    }
                }
            }
        });
    }

    public void keypad1(View A) { //method declared for keypad1

        TextView display = (TextView) findViewById(R.id.answer); //finds text and puts it in answer text field
        String text = (String) display.getText(); // gets the text
        if (text.contains("?")) //if statement if text contains a question mark
            text = text.replace("?", ""); //text will replace
        display.setText(text + "1");
    } //replacing with 1 when clicked

    public void keypad2(View A) {    //method declared for keypad2

        TextView display = (TextView) findViewById(R.id.answer); //finds text and puts it in answer text field
        String text = (String) display.getText(); // gets the text
        if (text.contains("?")) //if statement if text contains a question mark
            text = text.replace("?", ""); //text will replace
        display.setText(text + "2");
    } //replacing with 2 when clicked

    public void keypad3(View A) { //method declared for keypad3

        TextView display = (TextView) findViewById(R.id.answer); //finds text and puts it in answer text field
        String text = (String) display.getText(); // gets the text
        if (text.contains("?")) //if statement if text contains a question mark
            text = text.replace("?", ""); //text will replace
        display.setText(text + "3");
    } //replacing with 3 when clicked

    public void keypad4(View A) { //method declared for keypad4

        TextView display = (TextView) findViewById(R.id.answer); //finds text and puts it in answer text field
        String text = (String) display.getText(); // gets the text
        if (text.contains("?")) //if statement if text contains a question mark
            text = text.replace("?", ""); //text will replace
        display.setText(text + "4");
    } //replacing with 4 when clicked

    public void keypad5(View A) {    //method declared for keypad5

        TextView display = (TextView) findViewById(R.id.answer);    //finds text and puts it in answer text field
        String text = (String) display.getText(); // gets the text
        if (text.contains("?"))    //if statement if text contains a question mark
            text = text.replace("?", "");    //text will replace
        display.setText(text + "5");
    }    //replacing with 5 when clicked

    public void keypad6(View A) {    //method declared for keypad6

        TextView display = (TextView) findViewById(R.id.answer);    //finds text and puts it in answer text field
        String text = (String) display.getText();    // gets the text
        if (text.contains("?"))    //if statement if text contains a question mark
            text = text.replace("?", "");    //text will replace
        display.setText(text + "6");
    }    //replacing with 6 when clicked

    public void keypad7(View A) {    //method declared for keypad7

        TextView display = (TextView) findViewById(R.id.answer);    //finds text and puts it in answer text field
        String text = (String) display.getText();    // gets the text
        if (text.contains("?"))    //if statement if text contains a question mark
            text = text.replace("?", "");    //text will replace
        display.setText(text + "7");
    }    //replacing with 7 when clicked

    public void keypad8(View A) {    //method declared for keypad8

        TextView display = (TextView) findViewById(R.id.answer); //finds text and puts it in answer text field
        String text = (String) display.getText();    // gets the text
        if (text.contains("?"))    //if statement if text contains a question mark
            text = text.replace("?", "");    //text will replace
        display.setText(text + "8");
    }    //replacing with 8 when clicked

    public void keypad9(View A) {    //method declared for keypad9

        TextView display = (TextView) findViewById(R.id.answer);    //finds text and puts it in answer text field
        String text = (String) display.getText(); // gets the text
        if (text.contains("?"))    //if statement if text contains a question mark
            text = text.replace("?", "");    //text will replace
        display.setText(text + "9");
    }    //replacing with 9 when clicked

    public void keypad0(View A) {    //method declared for keypad0

        TextView display = (TextView) findViewById(R.id.answer);    //finds text and puts it in answer text field
        String text = (String) display.getText();    // gets the text
        if (text.contains("?"))    //if statement if text contains a question mark
            text = text.replace("?", "");    //text will replace
        display.setText(text + "0");
    }    //replacing with 0 when clicked

    public void keypadminus(View A) {    //method declared for keypadminus

        TextView display = (TextView) findViewById(R.id.answer);    //finds text and puts it in answer text field
        String text = (String) display.getText();    // gets the text
        if (text.contains("?"))    //if statement if text contains a question mark
            text = text.replace("?", "");    //text will replace
        display.setText(text + "-");    //replacing with - when clicked
    }

    public void keypaddelete(View A) { // method declared for keypaddelete

        TextView display = (TextView) findViewById(R.id.answer);    //finds text and puts it in answer text field
        String keypaddelete = (String) display.getText();    // gets the text
        if (keypaddelete.length() > 0) { //if statement if value greater then 0
            keypaddelete = keypaddelete.substring(0, keypaddelete.length() - 1); //kepaddelete will -1 when clicked delete a charectar
            display.setText(keypaddelete);    // will delete charectar when clicked
        }
    }

    public void keypadback(View A) {
        if (counterscore < 10) {
            time.cancel();
            saveGameData(); // saves the data

            Intent intent = new Intent(this, MainActivity.class);

            finish(); // finish the current activity
            startActivity(intent);

        } else {

        }
    }

    public void time() { //Public timer method

        time = new CountDownTimer(10000, 1000) { //displaying the count down timer
            Long millisUnitilFinished;        //The amount of time until finished.

            public void onTick(long millisUntilFinished) { // method Callback fired on regular interval
                TextView timing = (TextView) findViewById(R.id.timer); //This where the timer shows
                timing.setText(String.valueOf(millisUntilFinished / 1000)); // this makes sure its 10 seconds
                this.millisUnitilFinished = millisUntilFinished / 1000;
                secondsLeft = millisUntilFinished / 1000;
            } //sets 10 seconds

            public void onFinish() {    // Method Callback fired when the time is up.
                TextView timing = (TextView) findViewById(R.id.timer); //this is where the timer shows
                double leftover = millisUnitilFinished;        //left over variable
                timing.setText("10");
                Hash.performClick(); //Performing the onclick for the hash button each time after 10 seconds
            }
        }.start(); //start the countdown timer
    }

    public void saveGameData() {

        //Data to be passed
        TextView question = (TextView) findViewById(R.id.Question); //puts questions in here
        TextView answer = (TextView) findViewById(R.id.answer); // displays question mark and user puts answer in here
        TextView display = (TextView) findViewById(R.id.answervalue); //displays if its correct or incorrect
        editor.putInt("Difficulty", MainActivity.difficulty); // stores dificulty level
        editor.putLong("TimeLeft", secondsLeft);
        editor.putString("Question", question.getText().toString());// stores question
        editor.putString("Answer", String.valueOf(Total));// stores answer
        editor.putString("Result", display.getText().toString());// stores result
        editor.putInt("QuestionNumber", counterscore);// stores
        editor.commit();
    }


}