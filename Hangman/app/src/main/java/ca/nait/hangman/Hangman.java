package ca.nait.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.renderscript.RenderScript;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Hangman extends Activity implements OnClickListener  {
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        Button Playbutton = findViewById(R.id.playBtn);
        Playbutton.setOnClickListener(this);
        //notification
        notificationManager = NotificationManagerCompat.from(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.playBtn) {
            Intent playIntent = new Intent(this, MainActivity.class);
            this.startActivity(playIntent);
            overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_out);
            Notification notification = new NotificationCompat.Builder(this, App.HangmanRemainder_ID)
                    .setSmallIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                    .setContentTitle("HangMan")
                    .setContentText("Welcome to Hang man, try out the new word category Dragonball Characters")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
                notificationManager.notify(1,notification);
        }
    }
}
