package com.example.chessclock.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chessclock.FormatTime;
import com.example.chessclock.R;
import com.example.chessclock.models.game;

public class pastGameAdapter extends RecyclerView.Adapter<pastGameAdapter.ViewHolder> {
    private ObservableArrayList<game> games;
    public FormatTime formater = new FormatTime();
    public pastGameAdapter(ObservableArrayList<game> information){
        this.games = information;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        game game = games.get(position);

        TextView wName = holder.itemView.findViewById(R.id.white_card_name);
        wName.setText("White: " + game.whiteName);

        TextView bName = holder.itemView.findViewById(R.id.black_card_name);
        bName.setText("Black: "+ game.blackName);

        TextView wTime = holder.itemView.findViewById(R.id.white_card_time);
        wTime.setText("timeleft: " + formater.format(game.whiteTime));

        TextView bTime = holder.itemView.findViewById(R.id.black_card_time);
        bTime.setText("time left: " + formater.format(game.blackTime));

        TextView wMoves = holder.itemView.findViewById(R.id.white_card_move_count);
        wMoves.setText("made " + String.valueOf(game.whiteMoves) + " moves");

        TextView bMoves = holder.itemView.findViewById(R.id.black_card_move_count);
        bMoves.setText("made " + String.valueOf(game.blackMoves) +" moves");

        TextView winner = holder.itemView.findViewById(R.id.winner);
        winner.setText("Winner: " + game.winner);

    }


    @Override
    public int getItemCount() {

        return games.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

