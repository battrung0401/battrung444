package com.example.androidtopic2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;

import com.example.topic2b.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchAddress, switchElectricity, switchUserType, switchPrice, switchMusic;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchAddress = findViewById(R.id.switchAddress);
        switchElectricity = findViewById(R.id.switchElectricity);
        switchUserType = findViewById(R.id.switchUserType);
        switchPrice = findViewById(R.id.switchPrice);
        switchMusic = findViewById(R.id.switchMusic);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);


        boolean isAddressVisible = sharedPreferences.getBoolean("showAddress", true);
        boolean isElectricityVisible = sharedPreferences.getBoolean("showElectricity", true);
        boolean isUserTypeVisible = sharedPreferences.getBoolean("showUserType", true);
        boolean isPriceVisible = sharedPreferences.getBoolean("showPrice", true);
        boolean isMusicOn = sharedPreferences.getBoolean("playMusic", false);


        switchAddress.setChecked(isAddressVisible);
        switchElectricity.setChecked(isElectricityVisible);
        switchUserType.setChecked(isUserTypeVisible);
        switchPrice.setChecked(isPriceVisible);
        switchMusic.setChecked(isMusicOn);


        switchAddress.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("showAddress", isChecked).apply());

        switchElectricity.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("showElectricity", isChecked).apply());

        switchUserType.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("showUserType", isChecked).apply());

        switchPrice.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("showPrice", isChecked).apply());

        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("playMusic", isChecked).apply();
            if (isChecked) {
                playMusic();
            } else {
                stopMusic();
            }
        });
    }

    private MediaPlayer mediaPlayer;

    private void playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music);

            mediaPlayer.setLooping(true); // Phát nhạc liên tục
            mediaPlayer.start();
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
