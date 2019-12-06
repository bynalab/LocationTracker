package com.mti.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    TextView email_address;

    EditText password;

    FirebaseAuth mAuth;
    String UserID;

    Dialog dialog;

    String email = "";
    String pswd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFireBase();
        initViews();

    }

    private void initFireBase() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void initViews() {
        email_address = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        Bundle bundle = getIntent().getExtras();
        email_address.setText(bundle.getString("email"));

        dialog = new Dialog(this);
    }

    public void Login(View v) {
        if(fieldIsEmpty(password))
        {
            showDialog();
            //Code
            String email = email_address.getText().toString();
            String pass = password.getText().toString();

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();    //Remove before proceeding.
                    }else
                        {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }
            });

            dialog.dismiss();
        }
    }

    private void showDialog() {
        dialog.setContentView(R.layout.dialog_progress);
        dialog.show();
    }

    private boolean fieldIsEmpty(EditText textField) {
        if(textField.getText().toString().isEmpty())
        {
            textField.setError("Field cannot be left blank");
            return false;
        }else
        {
            return true;
        }
    }
}
