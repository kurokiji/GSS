package com.kurokiji.gss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    TextView ipChangeButton;
    Button pinChangeButton;
    Switch pinRequestSwitch;

    String ipData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        ipChangeButton = view.findViewById(R.id.ipSystem);
        ipChangeButton.setOnClickListener(this);
        pinChangeButton = view.findViewById(R.id.changePinButton);
        pinRequestSwitch = view.findViewById(R.id.requestPinSwitch);
        switchListener();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ipSystem:
                launchDeleteDialog();
                break;
            case R.id.changePinButton:
                break;
        }
    }

    public void launchDeleteDialog(){
        LayoutInflater li = LayoutInflater.from(getContext());
        View ipDialog = li.inflate(R.layout.ip_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Change IP");
        builder.setMessage("Do you really want to change system IP?");
        builder.setView(ipDialog);
        final EditText ipHolder1 = (EditText) ipDialog.findViewById(R.id.ipHolder1);
        final EditText ipHolder2 = (EditText) ipDialog.findViewById(R.id.ipHolder2);
        final EditText ipHolder3 = (EditText) ipDialog.findViewById(R.id.ipHolder3);
        final EditText ipHolder4 = (EditText) ipDialog.findViewById(R.id.ipHolder4);
        builder.setPositiveButton(Html.fromHtml("<font color='#FFF'>Save</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ipData= ipHolder1.getText().toString() + "." + ipHolder2.getText().toString() + "." + ipHolder3.getText().toString() + "." + ipHolder4.getText().toString();
                ipChangeButton.setText(ipData);
            }
        });

        builder.setNegativeButton(Html.fromHtml("<font color='#FA3428'>Cancel</font>"), new DialogInterface.OnClickListener() { // se ha usado código HTML para darle formato a los botones
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public void switchListener(){
        pinRequestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // password disable
                    Log.d("switch", "onCheckedChanged: encendido");
                } else {
                    // The toggle is disabled
                    // pedir comprobacion para quitar la contrasenia
                    Log.d("switch", "onCheckedChanged: apagado");
                }
            }
        });
    }
}