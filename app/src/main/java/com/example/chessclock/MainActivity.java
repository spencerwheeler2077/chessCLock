package com.example.chessclock;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.chessclock.fragments.ClockFragment;
import com.example.chessclock.fragments.InputFragment;
import com.example.chessclock.fragments.PastGamesFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.FragmentContainer, ClockFragment.class, null).commit();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        viewModel.getPage().observe(this, (page) -> {
            if (page) {
                toolbar.setVisibility(View.GONE);
                toolbar.setEnabled(false);
                closeKeyboard();
            } else {
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setEnabled(true);
            }
        });

        viewModel.getToClock().observe(this, (toClock) -> {
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.FragmentContainer, ClockFragment.class, null).commit();
            navigationView.setCheckedItem(R.id.timer_item);

        });

        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.open();
        });

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            if (viewModel.getPage().getValue()) {
                viewModel.endGame("Draw");
            }
            if (menuItem.getItemId() == R.id.timer_item) {
                toolbar.setTitle("Timer");

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.FragmentContainer, ClockFragment.class, null).commit();
            }
            if (menuItem.getItemId() == R.id.input_item) {
                toolbar.setTitle("Input");
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.FragmentContainer, InputFragment.class, null).commit();
            }
            if (menuItem.getItemId() == R.id.history_item) {
                toolbar.setTitle("Past Games");
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.FragmentContainer, PastGamesFragment.class, null).commit();
            }
            drawerLayout.close();
            return true;
        });
    }
        private void closeKeyboard(){
        //This method was taken from
            // https://www.geeksforgeeks.org/how-to-programmatically-hide-android-soft-keyboard/



            View view = this.getCurrentFocus();

            if (view != null) {

                InputMethodManager manager
                        = (InputMethodManager)
                        getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                manager
                        .hideSoftInputFromWindow(
                                view.getWindowToken(), 0);
            }
        }

}