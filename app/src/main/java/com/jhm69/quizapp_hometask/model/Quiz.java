package com.jhm69.quizapp_hometask.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import androidx.annotation.NonNull;

import com.jhm69.quizapp_hometask.utils.BooleanLiatConverter;
import com.jhm69.quizapp_hometask.utils.DataTypeConverterQuestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NotNullFieldNotInitialized")
@Entity
public class Quiz implements Serializable {

    public String topic;
    public String difficulty;
    @TypeConverters(BooleanLiatConverter.class)
    public ArrayList<Boolean> answers;
    long timestamp;
    @TypeConverters(DataTypeConverterQuestion.class)
    List<Question> questionList;
    @PrimaryKey
    @NonNull
    String battleId;

    int score;
    int completed;

    @Ignore
    public Quiz() {
    }

    public Quiz(String topic, String difficulty, ArrayList<Boolean> answers, long timestamp, List<Question> questionList, @NonNull String battleId, int score, int completed) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.answers = answers;
        this.timestamp = timestamp;
        this.questionList = questionList;
        this.battleId = battleId;
        this.score = score;
        this.completed = completed;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Boolean> answers) {
        this.answers = answers;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(@NonNull String battleId) {
        this.battleId = battleId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}