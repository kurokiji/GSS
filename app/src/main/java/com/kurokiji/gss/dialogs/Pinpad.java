package com.kurokiji.gss.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.kurokiji.gss.R;
import com.kurokiji.gss.activities.MainActivity;

public class Pinpad extends Dialog implements android.view.View.OnClickListener{
    public Activity myActivity;
    public TextView button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonErase, buttonExit;
    public TextView holder1, holder2, holder3, holder4;
    public String pin;
    public int pinToStore;
    public TextView pinPadInfo;
    public boolean pinOk = false;

    public Pinpad(Activity activity){
        super(activity);
        this.myActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pin_pad);
        pin = null;
        button0 = findViewById(R.id.number0);
        button0.setOnClickListener(this);
        button1 = findViewById(R.id.number1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.number2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.number3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.number4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.number5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.number6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.number7);
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.number8);
        button8.setOnClickListener(this);
        button9 = findViewById(R.id.number9);
        button9.setOnClickListener(this);
        buttonExit = findViewById(R.id.exit);
        buttonExit.setOnClickListener(this);
        buttonErase = findViewById(R.id.deleteKey);
        buttonErase.setOnClickListener(this);
        holder1 = findViewById(R.id.holder1);
        holder2 = findViewById(R.id.holder2);
        holder3 = findViewById(R.id.holder3);
        holder4 = findViewById(R.id.holder4);
        pinPadInfo = findViewById(R.id.pinPadInfo);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.number0:
                fillHolder(0);
                break;
            case R.id.number1:
                fillHolder(1);
                break;
            case R.id.number2:
                fillHolder(2);
                break;
            case R.id.number3:
                fillHolder(3);
                break;
            case R.id.number4:
                fillHolder(4);
                break;
            case R.id.number5:
                fillHolder(5);
                break;
            case R.id.number6:
                fillHolder(6);
                break;
            case R.id.number7:
                fillHolder(7);
                break;
            case R.id.number8:
                fillHolder(8);
                break;
            case R.id.number9:
                fillHolder(9);
                break;
            case R.id.deleteKey:
                deletePin();
                break;
            case R.id.exit:
                this.cancel();
                break;
        }
    }

    public void fillHolder(int number){
       if (holder1.getText().toString().equals("")){
           holder1.setText("•");
           pin = "" + number;
       } else if (holder2.getText().toString().equals("")){
           holder2.setText("•");
           pin += number;
       } else if (holder3.getText().toString().equals("")){
           holder3.setText("•");
           pin += number;
       } else if (holder4.getText().toString().equals("")){
           holder4.setText("•");
           pin += number;
           pinToStore = Integer.parseInt(pin);
           checkPin();
       }

    }

    public void deletePin(){
        holder1.setText("");
        holder2.setText("");
        holder3.setText("");
        holder4.setText("");
        pin = null;
    }

    public void checkPin(){
        MainActivity localMainActivity = (MainActivity) myActivity;
        if (pinToStore == localMainActivity.currentUser.getPassword()){
            pinOk = true;
            dismiss();
        } else{
            deletePin();
            pinPadInfo.setText(R.string.incorrect_pin);
            pinPadInfo.setTextColor(myActivity.getResources().getColor(R.color.my_red));
        }
    }
}
