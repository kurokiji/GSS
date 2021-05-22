package com.kurokiji.gss;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener{

    SuperApi api;

    EditText userEditText;
    EditText emailEditText;
    EditText repeatPasswordEditText;
    EditText passwordEditText;

    TextView sendButton;
    TextView logInButton;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        retrofitInit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_up, container, false);
        userEditText = view.findViewById(R.id.userNameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        repeatPasswordEditText = view.findViewById(R.id.repeatPasswordEditText);
        sendButton = view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        logInButton = view.findViewById(R.id.loginButton);
        logInButton.setOnClickListener(this);
        return view;
    }

    public void retrofitInit(){
        Retrofit retrofit = new Retrofit.Builder() // constructor por fases
                .baseUrl(SuperApi.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // solo para conversiones de tipos primitivos
                .addConverterFactory(ScalarsConverterFactory.create()) // creando el conversor de JSON
                .build();
        api = retrofit.create(SuperApi.class);
    }

    public void signUp(String user, String password){
        api.registerUser(new User(user, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("login", "onResponse: " + response.body());
                // TODO
                String email = emailEditText.getText().toString();
               loadMainActivity(user, password, email);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String email = emailEditText.getText().toString();
                loadMainActivity(user, password, email);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton:

               // TODO VALIDAR DATOS
                 loadMainActivity(userEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString());
//                signUp(userEditText.getText().toString(), passwordEditText.getText().toString());
                break;
            case R.id.loginButton:
                    LoginActivity loginActivity = new LoginActivity();
                    loginActivity.openLogInFragment();
                    // TODO hace un raro al abrir desde aqui
                break;
        }
    }

    public boolean fieldValidation() {
        //TODO validar email
        if (userEditText.getText() == null) {
            userEditText.setError("You have to write your user");
            return false;
        }
        if (passwordEditText.getText() == null || passwordEditText.getText().toString().length() > 4 || passwordEditText.getText().toString().length() < 4) {
            passwordEditText.setError("Your password must have 4 for numeric characters");
            return false;
        } else { return true; }
    }

    public void loadMainActivity(String user, String password, String email) {
        Intent mainActivityIntent = new Intent(getContext(), MainActivity.class);
        mainActivityIntent.putExtra(Constants.USERNAME_DATA, user);
        mainActivityIntent.putExtra(Constants.USEREMAIL_DATA, email);
        mainActivityIntent.putExtra(Constants.USERPIN_DATA, password);
        startActivity(mainActivityIntent);
    }

}