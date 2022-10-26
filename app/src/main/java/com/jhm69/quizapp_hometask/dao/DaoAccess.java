package com.jhm69.quizapp_hometask.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jhm69.quizapp_hometask.model.Quiz;

import java.util.List;


@Dao
public interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResult(Quiz battleModel);

    @Query("SELECT * FROM Quiz WHERE battleId =:taskId")
    LiveData<Quiz> getResult(String taskId);


    @Query("SELECT * FROM Quiz WHERE battleId =:taskId")
    Quiz getBattleOffline(String taskId);


    @Query("SELECT * FROM Quiz ORDER BY timestamp")
    LiveData<List<Quiz>> getAllResult();

    @Query("DELETE FROM Quiz WHERE battleId = :result")
    void deleteResult(String result);

}
