package com.kurokiji.gss.mainfragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kurokiji.gss.dialogs.ChangePinDialog;
import com.kurokiji.gss.dialogs.Pinpad;
import com.kurokiji.gss.R;
import com.kurokiji.gss.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    TextView ipChangeButton;
    TextView pinChangeButton;
    Switch pinRequestSwitch;
    MainActivity localMainActivity;

    boolean pinResult;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        ipChangeButton = view.findViewById(R.id.ipSystem);
        ipChangeButton.setOnClickListener(this);
        pinChangeButton = view.findViewById(R.id.changePinButton);
        pinChangeButton.setOnClickListener(this);
        pinRequestSwitch = view.findViewById(R.id.requestPinSwitch);
        localMainActivity = (MainActivity) getActivity();
        ipChangeButton.setText(localMainActivity.currentUser.getUserIpData());
        if(localMainActivity.currentUser.isRequestPinData()){
            pinRequestSwitch.setChecked(true);
        } else {
            pinRequestSwitch.setChecked(false);
        }
        switchListener();


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ipSystem:
                launchEditIpDialog();
                break;
            case R.id.changePinButton:
                launchChangePinDialog();
                break;
        }
    }

    // TODO add validacion a IP
    public void launchEditIpDialog(){
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

        String[] parts = localMainActivity.currentUser.getUserIpData().split("\\.");
        ipHolder1.setText(parts[0]);
        ipHolder2.setText(parts[1]);
        ipHolder3.setText(parts[2]);
        ipHolder4.setText(parts[3]);

        builder.setPositiveButton(Html.fromHtml("<font color='#FFF'>Save</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO intentar validar
                String ipData= ipHolder1.getText().toString() + "." + ipHolder2.getText().toString() + "." + ipHolder3.getText().toString() + "." + ipHolder4.getText().toString();
                ipChangeButton.setText(ipData);
               localMainActivity.currentUser.setUserIpData(ipData);
               localMainActivity.saveData();
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
                    localMainActivity.currentUser.setRequestPinData(true);
                    localMainActivity.saveData();

                } else {
                    launchDisablePinDialog();
                }
            }
        });
    }


    public void launchDisablePinDialog(){
        Pinpad myPinPad = new Pinpad(getActivity());
        myPinPad.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.welcome_background));
        myPinPad.setCancelable(false);
        myPinPad.show();

        myPinPad.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (myPinPad.pinOk){
                    localMainActivity.currentUser.setRequestPinData(false);
                    localMainActivity.saveData();
                }
            }
        });
        myPinPad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast cancelToast = Toast.makeText(getActivity(), "You must match your PIN", Toast.LENGTH_LONG);
                cancelToast.show();
                pinRequestSwitch.setChecked(true);
            }
        });
    }

    public void launchChangePinDialog(){
        Pinpad myPinPad = new Pinpad(getActivity());
        myPinPad.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.welcome_background));
        myPinPad.setCancelable(false);
        myPinPad.show();
        myPinPad.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (myPinPad.pinOk){
                    changePinDialog();
                }
            }
        });
        myPinPad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                pinResult = false;
                Toast cancelToast = Toast.makeText(getActivity(), "You must match your PIN", Toast.LENGTH_LONG);
                cancelToast.show();
            }
        });
    }

    public void changePinDialog(){
        ChangePinDialog changePin = new ChangePinDialog(getActivity());
        changePin.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.welcome_background));
        changePin.setCancelable(false);
        changePin.show();

        changePin.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (changePin.changePinAccepted){
                    localMainActivity.currentUser.setPassword(changePin.newPin);
                    localMainActivity.saveData();
                }
            }
        });
        changePin.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }
}