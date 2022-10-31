package com.jhm69.quizapp_hometask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhm69.quizapp_hometask.activity.SelectCategory;
import com.jhm69.quizapp_hometask.adapter.ResultAdapter;
import com.jhm69.quizapp_hometask.viewmodel.BattleViewModel;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ResultAdapter resultAdapter;
    BattleViewModel battleViewModel;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        battleViewModel = ViewModelProviders.of(this)
                .get(BattleViewModel.class);
        mRecyclerView = findViewById(R.id.rcgv);

        findViewById(R.id.button).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SelectCategory.class));
        });
        findViewById(R.id.imageView2).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SelectCategory.class));
        });
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        battleViewModel.battles.observe(this, quizList -> {
            resultAdapter = new ResultAdapter(quizList, this);
            mRecyclerView.setAdapter(resultAdapter);
            resultAdapter.notifyDataSetChanged();
        });
    }
}