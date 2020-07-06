package ca.nait.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.GridView;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

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
    //new method
    //before on create
    private String[] words;
    private String[] dragonballzEpisodes;
    private String[] dbsChars;
    private Random rand;
    private String currWord;
    private LinearLayout wordLayout;
    private TextView[] charViews;
    //reveal text
    Button RevealWordsButton;
    Button DragonballZButton;
    Button DragonballSuperButton;
    int clickcount=0;

//fix
    void revealLetterInWord(String letter){
        int indexOfLetter = currWord.indexOf(letter);
        //loop if inde
        while(indexOfLetter >= 0){
            wordDisplayedCharArray[indexOfLetter] = currWord.charAt(indexOfLetter);
            indexOfLetter = currWord.indexOf(letter, indexOfLetter + 1);

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
        //hangman invisible
        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }
        //new method for guess and won/lost










        //old method
        //1.Word
        //shuffle array list and get the first element and then remove it
//        Collections.shuffle(myListOfWords);
//        wordToBeGuessed = myListOfWords.get(0);
//        myListOfWords.remove(0);
//
//        //initialize char array
//        wordDisplayedCharArray = wordToBeGuessed.toCharArray();
//
//        // add underscores
//        for(int i = 1; i < wordDisplayedCharArray.length - 1; i++){
//            wordDisplayedCharArray[i] = '_';
//
//        }
//        //reveal all occurrences of first characters
//        revealLetterInWord(wordDisplayedCharArray[0]);
//        //reveal all occurrences of last characters
//        revealLetterInWord(wordDisplayedCharArray[wordDisplayedCharArray.length - 1]);
//
//        //intialize a string from this char array (for search purposes)
//        wordDisplayedString = String.valueOf(wordDisplayedCharArray);

        //display word
//        displayWordOnScreen();
//        //2. Input
//        //clear input field
//        editInput.setText("");
//        // 3. Letters Tried
//        //Initialize string for letters tried with a space
//        LettersTried = " ";
//
//        //display on screen
//        TextLettersTried.setText(MESSAGE_WITH_LETTERS_TRIED);

        //4.Tries Left
        //initialize the string for tries left
//        triesLeft = " X X X X X";
//        //triesLeft =
//        TextTriesLeft.setText(triesLeft);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new method
        Resources res = getResources();
        words = res.getStringArray(R.array.words);
        dragonballzEpisodes = res.getStringArray(R.array.dragonballzEpisodes);
        dbsChars = res.getStringArray(R.array.dbsChars);
        //new method intiallize
        rand = new Random();
        currWord = "";
        wordLayout = (LinearLayout)findViewById(R.id.word);
        RevealWordsButton = findViewById(R.id.activity_main_reveal_button);
        DragonballZButton = findViewById(R.id.activity_main_DragonballC_button);
        DragonballSuperButton = findViewById(R.id.activity_main_DragonballSuper_Button);
        //intialize variables
//        myListOfWords = new ArrayList<String>();

//        TextWordToBeGuessed = findViewById(R.id.activity_main_word_to_be_guessed_textview);
//        editInput = findViewById(R.id.activity_main_input_editText);
//        TextLettersTried = findViewById(R.id.activity_main_letters_used);
//        TextTriesLeft = findViewById(R.id.activity_main_attemptsLeft);
        //animation
        rotateAnimation  = AnimationUtils.loadAnimation(this,R.anim.rotate);
        scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale);
        scaleAndRotateAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_and_rotate);
        scaleAndRotateAnimation.setFillAfter(true);
//
        //body
        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView)findViewById(R.id.head);
        bodyParts[1] = (ImageView)findViewById(R.id.body);
        bodyParts[2] = (ImageView)findViewById(R.id.arm1);
        bodyParts[3] = (ImageView)findViewById(R.id.arm2);
        bodyParts[4] = (ImageView)findViewById(R.id.leg1);
        bodyParts[5] = (ImageView)findViewById(R.id.leg2);

        //letters
        letters = findViewById(R.id.letters);
        playGame();
        //traverse database file and populate array list
//        InputStream myInputStream = null;
//        Scanner in = null;
//        String  aWord = "";
//        try {
//            myInputStream = getAssets().open("database_file.txt");
//            in = new Scanner(myInputStream);
//            while(in.hasNext()){
//                aWord = in.next();
//                myListOfWords.add(aWord);
//
//            }
//        } catch (IOException e) {
//            Toast.makeText(MainActivity.this, e.getClass().getSimpleName() + ": " + e.getMessage(),
//            Toast.LENGTH_SHORT).show();
//        }
//        finally{
//            //close scanner
//            if(in != null)
//            {
//                in.close();
//            }
            //close InputStream
//            try {
//                if(myInputStream != null) {
//                    myInputStream.close();
//                }
//            } catch (IOException e) {
//                Toast.makeText(MainActivity.this, e.getClass().getSimpleName() + ": " + e.getMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }

        RevealWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                clickcount=clickcount+1;
                charViews[clickcount].setTextColor(Color.BLACK);
                if(clickcount >= 6){
                    RevealWordsButton.setEnabled(false);
                }
                //start animation
                v.startAnimation(rotateAnimation);
            }
        });

        DragonballZButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playDbzEpisdoes();

               v.setAnimation(scaleAndRotateAnimation);
            }
        });

        DragonballSuperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playDbs();
                v.setAnimation(scaleAndRotateAnimation);
            }
        });
        }

         //initializeGame();

        // setup the text changed listner for the edit text
//        editInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    // if there is some letter on the input field
//                if(s.length() != 0){
//                    chechIfLetterIsInWord(s.charAt(0));
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//    }
    private void playGame()
    {
        //play a new game
        //get random word
        String newWord = words[rand.nextInt(words.length)];
        while(newWord.equals(currWord)) newWord = words[rand.nextInt(words.length)];
        currWord = newWord;

        charViews = new TextView[currWord.length()];
        //remove all text views
        wordLayout.removeAllViews();
        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);
        // for to iterlate over each word in awnser
        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText(""+currWord.charAt(c));

            charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);
            //add to layout
            wordLayout.addView(charViews[c]);

        }
        currPart=0;
        numChars=currWord.length();
        numCorr=0;
        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }


    }

    //dbz episdodes method
    private void playDbzEpisdoes()
    {
        //play a new game
        //get random word
        String newWord = dragonballzEpisodes[rand.nextInt(dragonballzEpisodes.length)];
        while(newWord.equals(currWord)) newWord = dragonballzEpisodes[rand.nextInt(dragonballzEpisodes.length)];
        currWord = newWord;

        charViews = new TextView[currWord.length()];
        //remove all text views
        wordLayout.removeAllViews();
        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);
        // for to iterlate over each word in awnser
        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText(""+currWord.charAt(c));

            charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);
            //add to layout
            wordLayout.addView(charViews[c]);

        }
        currPart=0;
        numChars=currWord.length();
        numCorr=0;
        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }


    }
    private void playDbs() {
        //play a new game
        //get random word
        String newWord = dbsChars[rand.nextInt(dbsChars.length)];
        while (newWord.equals(currWord))
            newWord = dbsChars[rand.nextInt(dbsChars.length)];
        currWord = newWord;

        charViews = new TextView[currWord.length()];
        //remove all text views
        wordLayout.removeAllViews();
        ltrAdapt = new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);
        // for to iterlate over each word in awnser
        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText("" + currWord.charAt(c));

            charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);
            //add to layout
            wordLayout.addView(charViews[c]);

        }
        currPart = 0;
        numChars = currWord.length();
        numCorr = 0;
        for (int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }
    }

//    void chechIfLetterIsInWord(char letter){
//         // if the letter is found in the word to be guessed
//        if(wordToBeGuessed.indexOf(letter) >= 0){
//            // if the letter was Not displayed yet
//            if(wordDisplayedString.indexOf(letter) < 0){
//                //animate
//                TextWordToBeGuessed.startAnimation(scaleAnimation);
//                //replace the underscores with that letter
//                revealLetterInWord(letter);
//                //update the changes on screen
//                displayWordOnScreen();
//                //check if the game is won
//                if(!wordDisplayedString.contains("_")){
//                    TextTriesLeft.startAnimation(scaleAndRotateAnimation);
//                    TextTriesLeft.setText(WINNING_MESSAGE);
//                }
//            }
//
//        }
//        //otherwise, if the letter was Not found inside the word to be guessed
//        else {
//            //decrease the number of tries left, and we'll show it on screen
//            //decreasesAndDisplayTriesLeft(); //this crashes
//            //check if the game is lost
//            if(triesLeft.isEmpty()){
//                TextTriesLeft.startAnimation(scaleAndRotateAnimation);
//                TextTriesLeft.setText(LOSING_MESSAGE);
//                TextWordToBeGuessed.setText(wordToBeGuessed);
//            }
//        }
        //display the letter that was tried

//    }
    //if you put 2 it crashes
//    void decreasesAndDisplayTriesLeft() {
//        //if there are still some tries left
//        if(!triesLeft.isEmpty()){
//            // animate scale
//            TextTriesLeft.startAnimation(scaleAnimation);
//            //take out the last 2 characters from this string
//            triesLeft = triesLeft.substring(0, triesLeft.length() - 1);
//            TextLettersTried.setText(triesLeft);
//        }

//    }


//    public void ResetGame(View view) {
//        //start animation
//        view.startAnimation(rotateAnimation);
//        //setup new game
//        //initializeGame();
//        playGame();
//        editInput.setText("");
//
//    }

    public void letterPressed(View view) {
        //when player taps button letterpressed recieves a reference
        view.startAnimation( scaleAndRotateAnimation);
        String ltr=((TextView)view).getText().toString();
        //get char from the string
        char letterChar = ltr.charAt(0);
        //disable button
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);


        //loop
        boolean correct = false;
        for(int k = 0; k < currWord.length(); k++) {
            if(currWord.charAt(k)==letterChar){
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }
        if(correct) {
            if (numCorr == numChars) {
                // Disable Buttons
                disableBtns();

                // Display Alert Dialog
                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
                winBuild.setTitle("YAY");
                winBuild.setMessage("You win!\n\nThe answer was:\n\n"+currWord);
                winBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.playGame();
                            }});

                winBuild.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }});

                winBuild.show();
            }

        }//ifcorrect
        else if (currPart < numParts) {
            //some guesses left
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        }
        else{
            //user has lost
            disableBtns();

            // Display Alert Dialog
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("OOPS");
            loseBuild.setMessage("You lose!\n\nThe answer was:\n\n"+currWord);
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.playGame();
                        }});

            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }});

            loseBuild.show();
        }
    }//public tag

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }

    public void RevealWords(View view) {
        charViews[0].setTextColor(Color.BLACK);
        charViews[1].setTextColor(Color.BLACK);


        //start animation
      view.startAnimation(rotateAnimation);

    }

}
