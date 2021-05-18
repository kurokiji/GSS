package com.kurokiji.gss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView botttomMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botttomMenuView = findViewById(R.id.bottom_navigation);
        loadFragment(StatusFragment.newInstance("", ""));
        botttomMenuView.setSelectedItemId(R.id.status);

        botttomMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.status:
                        loadFragment(StatusFragment.newInstance("", ""));
                        return true;
                    case R.id.history:
                        loadFragment(HistoryFragment.newInstance("", ""));
                        return true;
                    case R.id.settings:
                        loadFragment(SettingsFragment.newInstance("", ""));
                        return true;
                }

                return false;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}