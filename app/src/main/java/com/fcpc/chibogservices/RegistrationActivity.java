package com.fcpc.chibogservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegistrationActivity extends AppCompatActivity {

    private Button bSave;
    private EditText tNewUser;
    private EditText tNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
    }

    private void initViews(){
        bSave = findViewById(R.id.btnSaveUser);
        tNewUser = findViewById(R.id.etNewUsername);
        tNewPass = findViewById(R.id.etNewPassword);
    }

    private void initButton(){
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nUser = tNewUser.getText().toString();

            }
        });
    }
}
