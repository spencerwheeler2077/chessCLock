package com.example.chessclock.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class game {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public String whiteName;
    @ColumnInfo
    public int whiteTime;
    @ColumnInfo
    public int whiteMoves;
    @ColumnInfo
    public String blackName;
    @ColumnInfo
    public int blackTime;
    @ColumnInfo
    public int blackMoves;
    @ColumnInfo
    public int startTime;
    @ColumnInfo
    public String winner;

}
