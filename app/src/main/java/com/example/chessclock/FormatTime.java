package com.example.chessclock;

public class FormatTime {
    public String format(int tenthSeconds) {
        int seconds = tenthSeconds/10%60;
        int minutes = tenthSeconds/10/60;
        String base = "%d:%2d";
        String formatted = String.format(base, minutes, seconds);
        return formatted.replace(" ", "0");



    }
}
