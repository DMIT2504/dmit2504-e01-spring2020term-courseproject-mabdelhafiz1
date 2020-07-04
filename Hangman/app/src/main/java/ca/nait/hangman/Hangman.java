package ca.nait.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Hangman extends Activity implements OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        Button Playbutton = findViewById(R.id.playBtn);
        Playbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.playBtn) {
            Intent playIntent = new Intent(this, MainActivity.class);
            this.startActivity(playIntent);
        }
    }
}
