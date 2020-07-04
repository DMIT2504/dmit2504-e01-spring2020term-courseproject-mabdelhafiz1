package ca.nait.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    //declare the variables
    TextView TextWordToBeGuessed;
    String wordToBeGuessed;
    String wordDisplayedString;
    char[] wordDisplayedCharArray;
    ArrayList<String> myListOfWords;
    EditText editInput;
    TextView TextLettersTried;
    String LettersTried;
    final String MESSAGE_WITH_LETTERS_TRIED = "Letters tried";
    TextView TextTriesLeft;
    String triesLeft;
    final String WINNING_MESSAGE="You Won!";
    final String LOSING_MESSAGE="You Lost!";
    Animation  rotateAnimation;
    Animation scaleAnimation;
    Animation scaleAndRotateAnimation;
    //gridview buttons
    private GridView letters;
    private LetterAdapter ltrAdapt;
    //body part images
    private ImageView[] bodyParts;
    //number of body parts
    private int numParts=6;
    //current part - will increment when wrong answers are chosen
    //guess left
    private int currPart;
    //number of characters in current word

    private int numChars;
    //number correctly guessed

    private int numCorr;

    void revealLetterInWord(char letter){
        int indexOfLetter = wordToBeGuessed.indexOf(letter);
        //loop if inde
        while(indexOfLetter >= 0){
            wordDisplayedCharArray[indexOfLetter] = wordToBeGuessed.charAt(indexOfLetter);
            indexOfLetter = wordToBeGuessed.indexOf(letter, indexOfLetter + 1);

        }
        //update string
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
    }

    void displayWordOnScreen(){
        String formattedString = "";
        for(char character : wordDisplayedCharArray){
            formattedString += character + " ";
        }
        TextWordToBeGuessed.setText(formattedString);

    }
    void initializeGame (){
        //gridview button
        ltrAdapt= new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

        //1.Word
        //shuffle array list and get the first element and then remove it
        Collections.shuffle(myListOfWords);
        wordToBeGuessed = myListOfWords.get(0);
        myListOfWords.remove(0);

        //initialize char array
        wordDisplayedCharArray = wordToBeGuessed.toCharArray();

        // add underscores
        for(int i = 1; i < wordDisplayedCharArray.length - 1; i++){
            wordDisplayedCharArray[i] = '_';

        }
        //reveal all occurrences of first characters
        revealLetterInWord(wordDisplayedCharArray[0]);
        //reveal all occurrences of last characters
        revealLetterInWord(wordDisplayedCharArray[wordDisplayedCharArray.length - 1]);

        //intialize a string from this char array (for search purposes)
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);

        //display word
        displayWordOnScreen();
        //2. Input
        //clear input field
        editInput.setText("");
        // 3. Letters Tried
        //Initialize string for letters tried with a space
        LettersTried = " ";

        //display on screen
        TextLettersTried.setText(MESSAGE_WITH_LETTERS_TRIED);

        //4.Tries Left
        //initialize the string for tries left
        triesLeft = " X X X X X";
        //triesLeft =
        TextTriesLeft.setText(triesLeft);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intialize variables
        myListOfWords = new ArrayList<String>();

        TextWordToBeGuessed = findViewById(R.id.activity_main_word_to_be_guessed_textview);
        editInput = findViewById(R.id.activity_main_input_editText);
        TextLettersTried = findViewById(R.id.activity_main_letters_used);
        TextTriesLeft = findViewById(R.id.activity_main_attemptsLeft);
        rotateAnimation  = AnimationUtils.loadAnimation(this,R.anim.rotate);
        scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale);
        scaleAndRotateAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_and_rotate);
        scaleAndRotateAnimation.setFillAfter(true);
        //body
        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView)findViewById(R.id.head);
        bodyParts[1] = (ImageView)findViewById(R.id.body);
        bodyParts[2] = (ImageView)findViewById(R.id.arm1);
        bodyParts[3] = (ImageView)findViewById(R.id.arm2);
        bodyParts[4] = (ImageView)findViewById(R.id.leg1);
        bodyParts[5] = (ImageView)findViewById(R.id.leg2);

        //traverse database file and populate array list
        InputStream myInputStream = null;
        Scanner in = null;
        String  aWord = "";
        try {
            myInputStream = getAssets().open("database_file.txt");
            in = new Scanner(myInputStream);
            while(in.hasNext()){
                aWord = in.next();
                myListOfWords.add(aWord);

            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getClass().getSimpleName() + ": " + e.getMessage(),
            Toast.LENGTH_SHORT).show();
        }
        finally{
            //close scanner
            if(in != null)
            {
                in.close();
            }
            //close InputStream
            try {
                if(myInputStream != null) {
                    myInputStream.close();
                }
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getClass().getSimpleName() + ": " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        }
        //letters
        letters = findViewById(R.id.letters);
         initializeGame();

        // setup the text changed listner for the edit text
        editInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // if there is some letter on the input field
                if(s.length() != 0){
                    chechIfLetterIsInWord(s.charAt(0));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    void chechIfLetterIsInWord(char letter){
         // if the letter is found in the word to be guessed
        if(wordToBeGuessed.indexOf(letter) >= 0){
            // if the letter was Not displayed yet
            if(wordDisplayedString.indexOf(letter) < 0){
                //animate
                TextWordToBeGuessed.startAnimation(scaleAnimation);
                //replace the underscores with that letter
                revealLetterInWord(letter);
                //update the changes on screen
                displayWordOnScreen();
                //check if the game is won
                if(!wordDisplayedString.contains("_")){
                    TextTriesLeft.startAnimation(scaleAndRotateAnimation);
                    TextTriesLeft.setText(WINNING_MESSAGE);
                }
            }

        }
        //otherwise, if the letter was Not found inside the word to be guessed
        else {
            //decrease the number of tries left, and we'll show it on screen
            decreasesAndDisplayTriesLeft(); //this crashes
            //check if the game is lost
            if(triesLeft.isEmpty()){
                TextTriesLeft.startAnimation(scaleAndRotateAnimation);
                TextTriesLeft.setText(LOSING_MESSAGE);
                TextWordToBeGuessed.setText(wordToBeGuessed);
            }
        }
        //display the letter that was tried
        if(LettersTried.indexOf(letter) < 0){
            LettersTried += letter + ", ";
            String messageToBeDisplayed = MESSAGE_WITH_LETTERS_TRIED + LettersTried;
            TextLettersTried.setText(messageToBeDisplayed);

        }
    }
    //if you put 2 it crashes
    void decreasesAndDisplayTriesLeft() {
        //if there are still some tries left
        if(!triesLeft.isEmpty()){
            // animate scale
            TextTriesLeft.startAnimation(scaleAnimation);
            //take out the last 2 characters from this string
            triesLeft = triesLeft.substring(0, triesLeft.length() - 1);
            TextLettersTried.setText(triesLeft);

        }

    }


    public void ResetGame(View view) {
        //start animation
        view.startAnimation(rotateAnimation);
        //setup new game
        initializeGame();
        editInput.setText("");

    }

    public void letterPressed(View view) {
        //when player taps button letterpressed recieves a reference
        String ltr=((TextView)view).getText().toString();
        //get char from the string
        char letterChar = ltr.charAt(0);
        //disable button
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);

        //loop
        boolean correct = false;
        for(int k = 0; k < wordToBeGuessed.length(); k++) {
            if (wordToBeGuessed.charAt(k) == letterChar) {
                correct = true;
                numCorr++;
                //charViews[k].setTextColor(Color.BLACK);
            }
        }
        if(!wordDisplayedString.contains("_")) {
            TextTriesLeft.startAnimation(scaleAndRotateAnimation);
            TextTriesLeft.setText(WINNING_MESSAGE);
        }





    }//public tag
}
