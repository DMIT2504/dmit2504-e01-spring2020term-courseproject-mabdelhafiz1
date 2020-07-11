package ca.nait.hangman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

public class PlaymusicActivity extends AppCompatActivity {
   // Toolbar toolbar = findViewById(R.id.order_management_toolBar);setSupportActionBar(toolbar);
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.my_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.menu_item_hangman:
                Intent HangmanIntent = new Intent(this, MainActivity.class);
                startActivity(HangmanIntent);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);
    }

    public void Play(View v){
        if(player == null){
            player = MediaPlayer.create(this, R.raw.dragonballrockthedragon);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });

        }
        player.start();
    }

    public void Pause(View v){
        if(player !=null){
            player.pause();
        }
    }

    public void Stop(View v){
        stopPlayer();
    }
    private void stopPlayer(){
        if(player !=null){
            player.release();
            player = null;
            Toast.makeText(this,"MediaPlayer released",Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
