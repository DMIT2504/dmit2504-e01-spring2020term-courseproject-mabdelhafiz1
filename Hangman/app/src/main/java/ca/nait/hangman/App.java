package ca.nait.hangman;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String HangmanRemainder_ID="hangmanremainder";


    @Override
    public void onCreate(){
        createNotifcationChannel();
        super.onCreate();
    }

    private void createNotifcationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel hangmanremainder = new NotificationChannel(
                    HangmanRemainder_ID,
                    "The Hangman Game is still running come back!",
                    NotificationManager.IMPORTANCE_HIGH
            );
//            hangmanremainder.setVibrationPattern();
//            hangmanremainder.setLightColor(BLUE);
            hangmanremainder.setDescription("Don't give up just yet! Try the Dragonball Charaters!!");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(hangmanremainder);

        }

    }
}
