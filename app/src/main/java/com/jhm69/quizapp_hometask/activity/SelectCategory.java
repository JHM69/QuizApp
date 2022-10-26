package com.jhm69.quizapp_hometask.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jhm69.quizapp_hometask.R;

import java.util.HashMap;

public class SelectCategory extends AppCompatActivity {
    StringBuilder category = new StringBuilder();
    String difficulty="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        ChipGroup categories = findViewById(R.id.chips_categories);
        ChipGroup difficulties = findViewById(R.id.chips_difficulty);

        findViewById(R.id.button2).setOnClickListener(view -> {
            for (int i=0; i<categories.getChildCount();i++){
                Chip chip = (Chip) categories.getChildAt(i);
                if (chip.isChecked()){
                    category.append(getResources().getResourceName(chip.getId()));
                    if(i!=(categories.getChildCount()-1)) category.append(",");
                }
            }
            for (int i=0; i<difficulties.getChildCount();i++){
                Chip chip = (Chip) difficulties.getChildAt(i);
                if (chip.isChecked()){
                    difficulty = getResources().getResourceName(chip.getId());
                }
            }
            startActivity(
                    new Intent(getApplicationContext(),
                            QuizBattle.class)
                            .putExtra("category", category.substring(0, category.length() - 1))
                            .putExtra("difficulty", difficulty));
        });

//        categories.put("arts_and_literature", "Arts & Literature");
//        categories.put("film_and_tv", "Film & TV");
//        categories.put("food_and_drink", "Food & Drink");
//        categories.put("general_knowledge", "General Knowledge");
//        categories.put("geography", "Geography");
//        categories.put("history", "History");
//        categories.put("science", "Science");
//        categories.put("music", "Music");
//        categories.put("society_and_culture", "Society & Culture");
//        categories.put("sport_and_leisure", "Sport & Leisure");



    }
}