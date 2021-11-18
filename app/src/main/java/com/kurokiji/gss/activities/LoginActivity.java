package com.kurokiji.gss.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.kurokiji.gss.loginFragments.LoginFragment;
import com.kurokiji.gss.R;
import com.kurokiji.gss.loginFragments.SignUpFragment;
import com.kurokiji.gss.interfaces.SuperApi;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public SuperApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        retrofitInit();
        openSignUpFragment();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.background, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void retrofitInit(){
        Retrofit retrofit = new Retrofit.Builder() // constructor por fases
                .baseUrl(SuperApi.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // solo para conversiones de tipos primitivos
                .addConverterFactory(ScalarsConverterFactory.create()) // creando el conversor de JSON
                .build();
        api = retrofit.create(SuperApi.class);
    }

    public void openLogInFragment() {
        loadFragment(LoginFragment.newInstance("", ""));
    }

    public void openSignUpFragment() {
        loadFragment(SignUpFragment.newInstance("", ""));
    }

}