package ca.nait.hangman;

import androidx.appcompat.app.AppCompatActivity;

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
}
