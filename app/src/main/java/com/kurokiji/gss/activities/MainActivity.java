package com.kurokiji.gss.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kurokiji.gss.Constants;
import com.kurokiji.gss.mainfragments.HistoryFragment;
import com.kurokiji.gss.mainfragments.ProfileFragment;
import com.kurokiji.gss.R;
import com.kurokiji.gss.mainfragments.SettingsFragment;
import com.kurokiji.gss.mainfragments.StatusFragment;
import com.kurokiji.gss.interfaces.SuperApi;
import com.kurokiji.gss.models.UserProfile;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView botttomMenuView;
    public Retrofit retrofit;
    public SuperApi api;

    public UserProfile currentUser;
    public HashMap<String, UserProfile> userProfiles = new HashMap<String, UserProfile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent loginIntent = getIntent();
        if(loginIntent.getExtras() != null){
            String userEmail = loginIntent.getStringExtra(Constants.USEREMAIL_DATA);
            int userPin = Integer.parseInt(loginIntent.getStringExtra((Constants.USERPIN_DATA)));
            String userName = loginIntent.getStringExtra(Constants.USERNAME_DATA);
            if(userProfiles.get(userEmail) == null){
                userProfiles.put(userEmail, new UserProfile(userName, userPin, userEmail, null, "000.000.000.000", true, false));
                currentUser = userProfiles.get(userEmail);
                saveData();
            } else{
                currentUser = userProfiles.get(userEmail);
            }
        }

        @SuppressLint("SdCardPath") File f = new File(
                "/data/data/com.kurokiji.gss/shared_prefs/preferencesData.xml"); // COMPROBAR PATH
        if (f.exists()) {
            loadData();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }

        botttomMenuView = findViewById(R.id.bottom_navigation);
        loadFragment(StatusFragment.newInstance());
        botttomMenuView.setSelectedItemId(R.id.status);
        retrofitInit();
        botttomMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.status:
                        loadFragment(StatusFragment.newInstance());
                        return true;
                    case R.id.history:
                        loadFragment(HistoryFragment.newInstance("", ""));
                        return true;
                    case R.id.profile:
                        loadFragment(ProfileFragment.newInstance());
                        return true;
                    case R.id.settings:
                        loadFragment(SettingsFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void loadData(){
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES_DATA, Context.MODE_PRIVATE);
        String userBdData = preferences.getString(Constants.USERSBD_DATA, null);
        Gson gson = new Gson();
        Type listType = new TypeToken<HashMap<String, UserProfile>>(){}.getType();
        userProfiles = gson.fromJson(userBdData, listType);
        currentUser = userProfiles.get(preferences.getString(Constants.CURRENT_USER, null));
    }

    public void saveData(){
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String userBdData = gson.toJson(userProfiles);
        editor.putString(Constants.USERSBD_DATA, userBdData);
        editor.putString(Constants.CURRENT_USER, currentUser.getEmail());
        editor.commit();
    }

    public void retrofitInit(){
        retrofit = new Retrofit.Builder() // constructor por fases
                .baseUrl(SuperApi.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // solo para conversiones de tipos primitivos
                .addConverterFactory(ScalarsConverterFactory.create()) // creando el conversor de JSON
                .build();
        api = retrofit.create(SuperApi.class);
    }
}