package com.example.chessclock.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chessclock.models.game;

import java.util.List;

@Dao
public interface GameDao {
    @Insert
    public long insert(game entry);

    @Query("SELECT * FROM game")
    public List<game> getAll();

    @Query("SELECT * FROM game WHERE id= :id LIMIT 1")
    public game findById(long id);

    @Update
    public void update(game entry);

    @Delete
    public void delete(game entry);


}
