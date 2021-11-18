package com.kurokiji.gss.loginFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kurokiji.gss.Constants;
import com.kurokiji.gss.R;
import com.kurokiji.gss.interfaces.SuperApi;
import com.kurokiji.gss.models.User;
import com.kurokiji.gss.activities.LoginActivity;
import com.kurokiji.gss.activities.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    SuperApi api;

    EditText emailEditText;

    EditText passwordEditText;

    TextView sendButton;
    TextView signUpButton;


    // TODO: Rename and change types of parameters
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitInit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        sendButton = view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        signUpButton = view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton:
                    if (fieldValidation()){
                      logIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
                    }
                break;
            case R.id.signUpButton:
                LoginActivity loginActivity = new LoginActivity();
                loginActivity.openSignUpFragment();
                break;
        }
    }

    public void logIn(String user, String password){
        api.loginUser(new User(user, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("login", "onResponse: " + response);
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(Constants.USEREMAIL_DATA, emailEditText.getText().toString());
                intent.putExtra(Constants.USERPIN_DATA, password);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                emailEditText.setError("Incorrect user or password");
                passwordEditText.setError("Incorrect user or password");
            }
        });
    }

    public void retrofitInit(){
        Retrofit retrofit = new Retrofit.Builder() // constructor por fases
                .baseUrl(SuperApi.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // solo para conversiones de tipos primitivos
                .addConverterFactory(GsonConverterFactory.create()) // creando el conversor de JSON
                .build();
        api = retrofit.create(SuperApi.class);
    }

    public boolean fieldValidation() {
        if (emailEditText.getText() == null) {
            emailEditText.setError("You have to write your user");
            return false;
        }
        if (passwordEditText.getText() == null || passwordEditText.getText().toString().length() > 4 || passwordEditText.getText().toString().length() < 4) {
            passwordEditText.setError("Your password must have 4 for numeric characters");
            return false;
        } else { return true; }
    }


}