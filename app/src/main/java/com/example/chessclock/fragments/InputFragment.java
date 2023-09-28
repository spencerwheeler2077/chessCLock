package com.example.chessclock.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chessclock.MainViewModel;
import com.example.chessclock.R;

public class InputFragment extends Fragment {
    public InputFragment() {
        super(R.layout.input_fragment);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        EditText whiteName = getView().findViewById(R.id.white_player_name);
        EditText blackName = getView().findViewById(R.id.black_player_name);
        EditText startTime = getView().findViewById(R.id.start_time);
        EditText increment = getView().findViewById(R.id.increment);
        Button startButton = getView().findViewById(R.id.start_button);

        MainViewModel viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        viewModel.setNotClockPage();
        ObservableArrayList pastGames = viewModel.getGameList();

        startButton.setOnClickListener(Button ->{
            viewModel.newGame(whiteName.getText().toString(), blackName.getText().toString(),
                    startTime.getText().toString(), increment.getText().toString());
            viewModel.changeToClock();
        });
    }

}
