package com.kurokiji.gss;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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


    ImageButton armButtom;
    TextView armText;
    ImageButton disarmButton;
    TextView disarmText;

    //TODO aumentar con cada objeto alert
    int totalWarning = 5;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment statusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
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

        retrofit = new Retrofit.Builder() // constructor por fases
                .baseUrl(SuperApi.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // solo para conversiones de tipos primitivos
                .addConverterFactory(ScalarsConverterFactory.create()) // creando el conversor de JSON
                .build();
        api = retrofit.create(SuperApi.class);
        getActualState();
       // changeStatusImage(currentStatus);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        protectionStatusButton = view.findViewById(R.id.protectionStatusButton);
        protectionStatusButton.setOnClickListener(this);
        protectionStatusText = view.findViewById(R.id.protectionStatusText);
        warningTextView = view.findViewById(R.id.warningText);
        warningTextView.setVisibility(View.INVISIBLE);
        armButtom = view.findViewById(R.id.armButtom);
        armButtom.setOnClickListener(this);
        armText = view.findViewById(R.id.armText);
        disarmButton = view.findViewById(R.id.disarmButton);
        disarmButton.setOnClickListener(this);
        disarmText = view.findViewById(R.id.disarmText);
        return view;
    }

    public void getActualState(){
        api.getNewState().enqueue((new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RESPUESTA", "onResponse: " + response.body());
                String status = response.body().replace('"', ' ').trim();
                changeUiByStatus(status);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                changeUiByStatus("error");
            }
        }));
    }

    public void changeUiByStatus(String state){
        Log.d("RESPUESTA", "switch: " + state.toString());
        switch (state){
            case "on":
                protectionStatusButton.setImageResource(R.drawable.shield);
                protectionStatusText.setText("Armed");
                armButtom.setAlpha(20);
                armText.setAlpha(0.2f);
                disarmButton.setAlpha(100);
                disarmText.setAlpha(1);
                break;
            case "off":
                protectionStatusButton.setImageResource(R.drawable.not_protected);
                protectionStatusText.setText("Disarmed");
                disarmButton.setAlpha(20);
                disarmText.setAlpha(0.2f);
                armButtom.setAlpha(100);
                armText.setAlpha(1);
                break;
            case "alert":
                protectionStatusButton.setImageResource(R.drawable.big_warning);
                protectionStatusText.setText("Intrusion");
                warningTextView.setVisibility(View.VISIBLE);
                warningTextView.setText("" + totalWarning + " intrusiones detectadas");
                disarmButton.setAlpha(20);
                disarmText.setAlpha(0.2f);
                armButtom.setAlpha(100);
                armText.setAlpha(1);
                break;
            case "error":
                protectionStatusButton.setImageResource(R.drawable.sync_problem);
                protectionStatusText.setText("Can't sync");
                disarmButton.setAlpha(20);
                disarmText.setAlpha(0.2f);
                armButtom.setAlpha(20);
                armText.setAlpha(0.2f);
                break;
        }

    }

    public void armSystem(){
        api.putState("on").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RESPUESTA", "respueta " + response.raw());
                getActualState();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getActualState();
            }
        });
    }

    public void disarmSystem(){
        api.putState("off").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                getActualState();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
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
        }
    }
}