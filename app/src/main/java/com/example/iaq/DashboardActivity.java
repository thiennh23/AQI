package com.example.iaq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class DashboardActivity extends AppCompatActivity {

    APIInterface apiInterface;
    AnimatedBottomBar animatedBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        animatedBottomBar = findViewById(R.id.bottom_bar);
        //Set default fragment
        replace(new HomeFragment());
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {
                if (tab1.getId() == R.id.homeScreen){
                    replace(new HomeFragment());
                } else if (tab1.getId() == R.id.map) {
                    replace(new MapFragment());
                } else if (tab1.getId() == R.id.chart){
                    replace(new ChartsFragment());
                } else if (tab1.getId() == R.id.setting){
                    replace(new SettingsFragment());
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

}