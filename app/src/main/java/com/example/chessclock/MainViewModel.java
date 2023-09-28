package com.example.chessclock;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.chessclock.database.AppDataBase;
import com.example.chessclock.models.game;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {

    private AppDataBase dataBase;
    private Boolean whitesTurn;
    public String wPlayerName = "White";
    public String bPlayerName = "Black";
    private int increment;
    private Boolean loaded = false;
    private Handler mainHandler;

    private ObservableArrayList<game> gameList = new ObservableArrayList<>();
    private MutableLiveData<Boolean> page = new MutableLiveData<>(true);
    private MutableLiveData<Integer> whiteMoves = new MutableLiveData<>(0);
    private MutableLiveData<Integer> blackMoves = new MutableLiveData<>(0);
    private MutableLiveData<Boolean> done = new MutableLiveData<>(false);
    private MutableLiveData<Integer> whiteTime = new MutableLiveData<>(100);
    private MutableLiveData<Integer> blackTime = new MutableLiveData<>(100);
    public MutableLiveData<Boolean> toClock = new MutableLiveData<>(true);
    private MutableLiveData<Boolean> gameLive = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getGameLive(){ return gameLive;}
    public MutableLiveData<Boolean> getToClock() {return toClock;}
    public MutableLiveData<Integer> getWhiteMoves(){
        return whiteMoves;
    }
    public MutableLiveData<Integer> getBlackMoves(){
        return blackMoves;
    }
    public MutableLiveData<Integer> getWhiteTime(){
        return whiteTime;
    }
    public MutableLiveData<Integer> getBlackTime(){
        return blackTime;
    }
    public MutableLiveData<Boolean> getDone() { return done; }
    public ObservableArrayList<game> getGameList() { return gameList;}
    public ObservableArrayList<game> getGames(){
        return gameList;
    }

    public void changeToClock(){
        toClock.setValue(!toClock.getValue());
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataBase = Room.databaseBuilder(application, AppDataBase.class, "pastGames").build();
        mainHandler = new Handler();
        runTimer();
        loadGames();
        loaded = false;

    }



    public void loadGames(){
        if (!loaded){
            loaded = true;
            new Thread(() ->{
                ArrayList<game> pastGames = (ArrayList<game>) dataBase.getGamesDao().getAll();
                mainHandler.post(() ->{
                    gameList.addAll(pastGames);
                        });
            }).start();
        }
    }



    public void setClockPage(){
        page.setValue(true);
    }
    public void setNotClockPage(){
        page.setValue(false);
    }
    public MutableLiveData<Boolean> getPage(){
        return page;
    }

    public void newGame(String whiteName, String blackName, String startTime, String incr){
        if (whiteName.equals("")){
            whiteName = "Black";
        }
            wPlayerName = whiteName;

        if(blackName.equals("")){
            blackName = "Black";
        }
        bPlayerName = blackName;

        if (startTime.equals("")){
            whiteTime.setValue(5*60*10);
            blackTime.setValue(5*60*10);
        }
        else{
            whiteTime.setValue(Integer.valueOf(startTime)*10*60);
            blackTime.setValue(Integer.valueOf(startTime)*10*60);
        }
        if (incr.equals("")){
            increment = 0;
        }
        else {
            increment = Integer.parseInt(incr)* 10;
        }
        gameLive.setValue(false);
        done.setValue(false);
        whiteMoves.setValue(0);
        blackMoves.setValue(0);
    }

    public void whitemove(){
        if(gameLive.getValue()){
            whiteTime.setValue(whiteTime.getValue()+increment);
            whiteMoves.setValue(whiteMoves.getValue() + 1);
        }
        whitesTurn = false;
    }
    public void blackmove(){
        if(gameLive.getValue()) {
            blackMoves.setValue(blackMoves.getValue() + 1);
            blackTime.setValue(blackTime.getValue()+increment);
        }
        if(!gameLive.getValue()){
            gameLive.setValue(true);
        }

        whitesTurn = true;

    }

    public void endGame(String winner){
        if (gameLive.getValue()) {
            if (!done.getValue()) {
                gameLive.setValue(false);
                done.setValue(true);
                com.example.chessclock.models.game newgame = new game();
                newgame.whiteMoves = whiteMoves.getValue();
                newgame.blackMoves = blackMoves.getValue();
                newgame.whiteName = wPlayerName;
                newgame.blackName = bPlayerName;
                newgame.whiteTime = whiteTime.getValue();
                newgame.blackTime = blackTime.getValue();
                newgame.winner = winner;
                new Thread(()->{
                    dataBase.getGamesDao().insert(newgame);
                    mainHandler.post(()->{
                        gameList.add(newgame);
                    });
                }).start();
            }
        }
    }

    private void runTimer()
    {
        // I got a lot of this timer code from
        //https://www.geeksforgeeks.org/how-to-create-a-stopwatch-app-using-android-studio/

        // Creates a new Handler
        final Handler handler = new Handler();


        handler.post(new Runnable() {
            @Override

            public void run()
            {
                // If running is true, increment the
                // seconds variable.
                if (gameLive.getValue()) {
                    if (whitesTurn) {
                        whiteTime.setValue(whiteTime.getValue() - 1);
                        if(whiteTime.getValue() <= 0){
                            whiteTime.setValue(0);
                            endGame(bPlayerName);
                        }
                    } else {
                        blackTime.setValue(blackTime.getValue() - 1);
                        if(blackTime.getValue() <= 0){
                            blackTime.setValue(0);
                            endGame(wPlayerName);

                        }
                    }
                }
                // Post the code again
                // with a delay of 1 milisecond.
                handler.postDelayed(this, 100);
            }
        });
    }
}

