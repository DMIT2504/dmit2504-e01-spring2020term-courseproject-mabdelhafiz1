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
    boolean clicked = false;

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
        wordLayout = findViewById(R.id.word);
        RevealWordsButton = findViewById(R.id.activity_main_reveal_button);
        DragonballZButton = findViewById(R.id.activity_main_DragonballC_button);
        DragonballSuperButton = findViewById(R.id.activity_main_DragonballSuper_Button);

        //animation
        rotateAnimation  = AnimationUtils.loadAnimation(this,R.anim.rotate);
        scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale);
        scaleAndRotateAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_and_rotate);
        
        //body
        bodyParts = new ImageView[numParts];
        bodyParts[0] = findViewById(R.id.head);
        bodyParts[1] = findViewById(R.id.body);
        bodyParts[2] = findViewById(R.id.arm1);
        bodyParts[3] = findViewById(R.id.arm2);
        bodyParts[4] = findViewById(R.id.leg1);
        bodyParts[5] = findViewById(R.id.leg2);

        //letters
        letters = findViewById(R.id.letters);
        playGame();

        RevealWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                if(clicked)
                {
                    clickcount =0;
                }
                clickcount+=1;
                charViews[clickcount].setTextColor(Color.GREEN);
                if(clickcount >= 5){
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
                clicked=true;
                RevealWordsButton.setEnabled(true);
               v.setAnimation(scaleAndRotateAnimation);
            }
        });

        DragonballSuperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked=true;
                playDbs();
                RevealWordsButton.setEnabled(true);
                v.setAnimation(scaleAndRotateAnimation);
            }
        });
        }


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

}
