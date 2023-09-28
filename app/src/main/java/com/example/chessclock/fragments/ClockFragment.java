package com.example.chessclock.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chessclock.FormatTime;
import com.example.chessclock.MainViewModel;
import com.example.chessclock.R;


public class ClockFragment extends Fragment {
    public ClockFragment(){
        super(R.layout.clock_fragment);
        }
    public FormatTime formater = new FormatTime();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton whiteButton = view.findViewById(R.id.white_button);
        whiteButton.setEnabled(false);
        AppCompatButton blackButton = view.findViewById(R.id.black_button);
        whiteButton.setEnabled(false);

        MainViewModel viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        TextView whiteName = view.findViewById(R.id.white_name);
        TextView blackName = view.findViewById(R.id.black_name);

        TextView whiteMovestext = view.findViewById(R.id.white_moves);
        TextView blackMovestext = view.findViewById(R.id.black_moves);

        blackName.setText(viewModel.bPlayerName);
        whiteName.setText(viewModel.wPlayerName);

        viewModel.setClockPage();

        viewModel.getGameLive().observe(getViewLifecycleOwner(), (gameLive)->{
            if (!gameLive){
                whiteButton.setEnabled(false);
            }
        });

        viewModel.getDone().observe(getViewLifecycleOwner(), (done) ->{

            if(done){
            whiteButton.setEnabled(false);
            blackButton.setEnabled(false);}
            else{
                blackButton.setEnabled(true);
            }
        });


        viewModel.getWhiteTime().observe(getViewLifecycleOwner(), (whiteTime)-> {
            //TODO: Format time
            int wTime= whiteTime;

            whiteButton.setText(formater.format(wTime));
        });

        viewModel.getWhiteMoves().observe(getViewLifecycleOwner(), (whiteMoves)-> {
            whiteMovestext.setText("move: " + String.valueOf(whiteMoves));
        });

        viewModel.getBlackMoves().observe(getViewLifecycleOwner(), (blackMoves)-> {
            blackMovestext.setText("move: " + String.valueOf(blackMoves));
        });

        viewModel.getBlackTime().observe(getViewLifecycleOwner(), (blackTime)-> {
            int btimevalue = blackTime;
            blackButton.setText(formater.format(btimevalue));
        });

        whiteButton.setOnClickListener(Button ->{
            view.findViewById(R.id.white_button).setEnabled(false);
            view.findViewById(R.id.black_button).setEnabled(true);
            viewModel.whitemove();
        });
        blackButton.setOnClickListener(Button ->{
            whiteButton.setEnabled(true);
            blackButton.setEnabled(false);
            viewModel.blackmove();
        });

        whiteButton.setOnLongClickListener(Button ->{
            viewModel.endGame((String)blackName.getText());
            return true;
        });
        blackButton.setOnLongClickListener(Button ->{
            viewModel.endGame((String)whiteName.getText());
            return true;
        });




    }
}
