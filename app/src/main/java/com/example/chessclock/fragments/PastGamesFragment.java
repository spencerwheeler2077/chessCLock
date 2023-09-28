package com.example.chessclock.fragments;


//  TODO: This needs to be a recycle view. Scroll view might work if time is tight.

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chessclock.MainViewModel;
import com.example.chessclock.databinding.PastGameFragmentBinding;
import com.example.chessclock.models.game;

public class PastGamesFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PastGameFragmentBinding binding = PastGameFragmentBinding.inflate(inflater, container, false);
        MainViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.setNotClockPage();
        ObservableArrayList<game> gameList = viewModel.getGameList();
        binding.pastGamesView.setAdapter(new pastGameAdapter(gameList));
        binding.pastGamesView.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }
}
