package com.example.kayor.continuousspeechrecognition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ListeningActivity {
    private final  int GET_AUDIO_PERMISSION = 4;
    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {
            /* do nothing*/
            } else {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, GET_AUDIO_PERMISSION);
            }
        }

        content = (LinearLayout)findViewById(R.id.commands);

        // The following 3 lines are needed in every onCreate method of a ListeningActivity
        context = getApplicationContext(); // Needs to be set
        VoiceRecognitionListener.getInstance().setListener(this); // Here we set the current listener
        startListening(); // starts listening
    }

    // Here is where the magic happens
    @Override
    public void processVoiceCommands(String voiceCommands) {
        content.removeAllViews();

            TextView txt = new TextView(getApplicationContext());
            txt.setText(voiceCommands);
            txt.setTextSize(10);
            txt.setTextColor(Color.BLACK);
            txt.setGravity(Gravity.CENTER);
            content.addView(txt);

        restartListeningService();
    }
}

