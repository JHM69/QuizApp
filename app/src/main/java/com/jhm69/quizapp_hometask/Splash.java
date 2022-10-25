package com.jhm69.quizapp_hometask;import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;import android.os.Bundle;import androidx.appcompat.app.AppCompatActivity;import androidx.appcompat.app.AppCompatDelegate;import java.util.Timer;import java.util.TimerTask;public class Splash extends AppCompatActivity {    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);        String themeName = sharedPreferences.getString("ThemeName", "Default");        if (themeName.equalsIgnoreCase("TealTheme")) {            setTheme(R.style.TealTheme);        } else if (themeName.equalsIgnoreCase("VioleteTheme")) {            setTheme(R.style.VioleteTheme);        } else if (themeName.equalsIgnoreCase("PinkTheme")) {            setTheme(R.style.PinkTheme);        } else if (themeName.equalsIgnoreCase("DelRio")) {            setTheme(R.style.DelRio);        } else if (themeName.equalsIgnoreCase("DarkTheme")) {            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);            setTheme(R.style.Dark);        } else if (themeName.equalsIgnoreCase("Lynch")) {            setTheme(R.style.Lynch);        } else {            setTheme(R.style.AppTheme);        }        setContentView(R.layout.activity_splash);        new Timer().schedule(new TimerTask() {            @Override            public void run() {                    startActivity(new Intent(Splash.this, MainActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TASK));            }        }, 2600);    }}