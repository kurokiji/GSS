package com.kurokiji.gss;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePinDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity myActivity;
    public EditText newPasswordEdit, repeatNewPasswordEdit;
    public TextView changeButton;
    public boolean changePinAccepted;
    public int newPin;

    public ChangePinDialog(Activity activity){
        super(activity);
        this.myActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.changepin_dialog);
        newPasswordEdit = findViewById(R.id.newPasswordEdit);
        repeatNewPasswordEdit = findViewById(R.id.repeatNewPasswordEdit);
        changeButton = findViewById(R.id.sendButton);
        changeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sendButton:
                if (passwordValidator()){
                    changePinAccepted = true;
                    dismiss();
                }
                break;
        }
    }

    public boolean passwordValidator(){
        if(newPasswordEdit.getText().toString().equals(repeatNewPasswordEdit.getText().toString())){
            newPin = Integer.parseInt(newPasswordEdit.getText().toString());
            return true;
        } else{
            repeatNewPasswordEdit.setError("Both passwords must match");
            return false;
        }
    }
}
