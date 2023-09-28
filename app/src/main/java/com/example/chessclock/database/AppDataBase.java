package com.example.chessclock.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chessclock.models.game;

@Database(entities = {game.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{

    public abstract GameDao getGamesDao();
}
