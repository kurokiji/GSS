package com.kurokiji.gss.mainfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kurokiji.gss.R;
import com.kurokiji.gss.models.State;
import com.kurokiji.gss.interfaces.SuperApi;
import com.kurokiji.gss.activities.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusFragment extends Fragment implements View.OnClickListener{

    Retrofit retrofit;
    SuperApi api;
    ImageButton protectionStatusButton;
    TextView protectionStatusText;
    TextView warningTextView;


    ImageButton armButton;
    TextView armText;
    ImageButton disarmButton;
    TextView disarmText;

    MainActivity localMainActivity;

    public StatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment statusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = new Retrofit.Builder() // constructor por fases
                .baseUrl(SuperApi.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // solo para conversiones de tipos primitivos
                .addConverterFactory(GsonConverterFactory.create()) // creando el conversor de JSON
                .build();
        api = retrofit.create(SuperApi.class);

       // changeStatusImage(currentStatus);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        localMainActivity = (MainActivity) getActivity();
        protectionStatusButton = view.findViewById(R.id.protectionStatusButton);
        protectionStatusButton.setOnClickListener(this);
        protectionStatusText = view.findViewById(R.id.protectionStatusText);
        warningTextView = view.findViewById(R.id.warningText);
        warningTextView.setOnClickListener(this);
        warningTextView.setVisibility(View.INVISIBLE);
        armButton = view.findViewById(R.id.armButtom);
        armButton.setOnClickListener(this);
        armText = view.findViewById(R.id.armText);
        disarmButton = view.findViewById(R.id.disarmButton);
        disarmButton.setOnClickListener(this);
        disarmText = view.findViewById(R.id.disarmText);
        getActualState();
        welcomeDialogLauncher();
        return view;
    }

    public void getActualState(){
        disarmButtonIsEnabled(false);
        armButtonIsEnabled(false);
        protectionStatusButton.setClickable(false);
        protectionStatusButton.setImageResource(R.drawable.sync_in_progress);
        protectionStatusText.setText("Sync in progress");
        api.getNewState().enqueue((new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RESPUESTA", "onResponse: " + response.body());
                String status = response.body()
                        .replace('"', ' ')
                        .trim();
                changeUiByStatus(status);
                protectionStatusButton.setClickable(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                changeUiByStatus("error");
                protectionStatusButton.setClickable(true);
            }
        }));
    }

    public void changeUiByStatus(String state){
        switch (state){
            case "on":
                protectionStatusButton.setImageResource(R.drawable.armed);
                protectionStatusText.setText(R.string.armed_status);
                armButtonIsEnabled(false);
                disarmButtonIsEnabled(true);
                break;
            case "off":
                protectionStatusButton.setImageResource(R.drawable.disarmed);
                protectionStatusText.setText(R.string.disarmed_status);
                armButtonIsEnabled(true);
                disarmButtonIsEnabled(false);
                break;
            case "alert":
                protectionStatusButton.setImageResource(R.drawable.intrusion);
                protectionStatusText.setText(R.string.intrusion_status);
                warningTextView.setVisibility(View.VISIBLE);
                armButtonIsEnabled(false);
                disarmButtonIsEnabled(true);
                break;
            case "error":
                protectionStatusButton.setImageResource(R.drawable.sync_problem);
                protectionStatusText.setText(R.string.cant_sync_status);
                armButtonIsEnabled(false);
                disarmButtonIsEnabled(false);
                break;
        }

    }

    public void disarmButtonIsEnabled(boolean enabled){
        if (enabled) {
            disarmButton.setAlpha(255);
            disarmText.setAlpha(1);
            disarmButton.setClickable(true);
        } else {
            disarmButton.setAlpha(20);
            disarmText.setAlpha(0.2f);
            disarmButton.setClickable(false);
        }
    }

    public void armButtonIsEnabled(boolean enabled){
        if (enabled) {
            armButton.setAlpha(255);
            armText.setAlpha(1);
            armButton.setClickable(true);
        } else {
            armButton.setAlpha(20);
            armText.setAlpha(0.2f);
            armButton.setClickable(false);
        }
    }


    public void armSystem(){
        disarmButtonIsEnabled(false);
        armButtonIsEnabled(false);
        protectionStatusButton.setClickable(false);
        protectionStatusText.setText(R.string.arming_status);
        api.putState(new State("on")).enqueue(new Callback<State>() {
            @Override
            public void onResponse(Call<State> call, Response<State> response) {
                Log.d("RESPUESTA", "respueta " + response.headers() + response.raw());
                getActualState();
            }

            @Override
            public void onFailure(Call<State> call, Throwable t) {
                getActualState();
            }
        });
    }

    public void disarmSystem(){
        disarmButtonIsEnabled(false);
        armButtonIsEnabled(false);
        protectionStatusButton.setClickable(false);
        protectionStatusText.setText(R.string.disarming_status);
        api.putState(new State("off")).enqueue(new Callback<State>() {
            @Override
            public void onResponse(Call<State> call, Response<State> response) {
                getActualState();
            }

            @Override
            public void onFailure(Call<State> call, Throwable t) {
                getActualState();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.armButtom:
                armSystem();
                break;
            case R.id.disarmButton:
                disarmSystem();
                break;
            case R.id.protectionStatusButton:
                getActualState();
                break;
            case R.id.warningText:
                armSystem();
                localMainActivity.botttomMenuView.setSelectedItemId(R.id.history);
                localMainActivity.loadFragment(HistoryFragment.newInstance("",""));
                break;
        }
    }

public void welcomeDialogLauncher(){
        if(localMainActivity.currentUser.getWelcomeDoneData() != true){
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.welcome_dialog);
            dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.welcome_background));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            TextView dismiss = dialog.findViewById(R.id.dismissButton);
            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    localMainActivity.currentUser.setWelcomeDoneData(true);
                    localMainActivity.saveData();
                }
            });
            dialog.show();
        }
}


    public void pinPadDialog(){
        LayoutInflater li = LayoutInflater.from(getContext());
        View welcomeImage = li.inflate(R.layout.welcome_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(welcomeImage);
    }

}