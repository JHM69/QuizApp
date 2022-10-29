package com.jhm69.quizapp_hometask.activity;


import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.jhm69.quizapp_hometask.R;
import com.jhm69.quizapp_hometask.adapter.ResultEachQuestionAdapter;
import com.jhm69.quizapp_hometask.model.Question;
import com.jhm69.quizapp_hometask.model.Quiz;
import com.jhm69.quizapp_hometask.viewmodel.BattleViewModel;

import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends AppCompatActivity {
    public ProgressDialog mDialog;
    Button playAgain;
    RecyclerView mRecyclerView;
    ResultEachQuestionAdapter resultEachQuestionAdapter;
    @SuppressLint({"SetTextI18n", "InflateParams", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        String themeName = sharedPreferences.getString("ThemeName", "Default");
        if (themeName.equalsIgnoreCase("TealTheme")) {
            setTheme(R.style.TealTheme);
        } else if (themeName.equalsIgnoreCase("VioleteTheme")) {
            setTheme(R.style.VioleteTheme);
        } else if (themeName.equalsIgnoreCase("PinkTheme")) {
            setTheme(R.style.PinkTheme);
        } else if (themeName.equalsIgnoreCase("DelRio")) {
            setTheme(R.style.DelRio);
        } else if (themeName.equalsIgnoreCase("DarkTheme")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.Dark);
        } else if (themeName.equalsIgnoreCase("Lynch")) {
            setTheme(R.style.Lynch);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        String id = getIntent().getStringExtra("id");

        setSupportActionBar(toolbar);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        playAgain = findViewById(R.id.playAgain);



        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please wait..");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        TextView topic = findViewById(R.id.topic);
        //score = findViewById(R.id.textView6);
        BattleViewModel battleViewModel = ViewModelProviders.of(this).get(BattleViewModel.class);
        mRecyclerView = findViewById(R.id.recv);

        battleViewModel.getBattle(id).observe(this, quiz -> {
            toolbar.setSubtitle("Played "+ TimeAgo.using(quiz.getTimestamp()));
            resultEachQuestionAdapter = new ResultEachQuestionAdapter(quiz.getQuestionList(), quiz.answers, quiz.answerList,ResultActivity.this);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(resultEachQuestionAdapter);
            resultEachQuestionAdapter.notifyDataSetChanged();
            //score.setText(quiz.getScore());
            topic.setText("score : "+quiz.getScore());
           playAgain.setOnClickListener(view -> startActivity(new Intent(getApplication(), SelectCategory.class)));
        });

    }

}