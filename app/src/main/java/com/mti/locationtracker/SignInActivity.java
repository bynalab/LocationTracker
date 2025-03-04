package com.mti.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    EditText e_email;

    FirebaseAuth mauth;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initFireBase();
        initViews();
    }

    private void initFireBase() {
        mauth = FirebaseAuth.getInstance();
    }

    private void initViews() {
        e_email = findViewById(R.id.signin_email);
        dialog = new Dialog(this);
    }

    public void Proceed(View v)
    {
        if(fieldIsValid(e_email))
        {
            showDialog();
            mauth.fetchSignInMethodsForEmail(e_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    boolean newUser = task.getResult().getSignInMethods().isEmpty();
                    if(newUser)
                    {
                        //send user to create password
                        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("email", e_email.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else
                        {
                            //send user to login
                            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email", e_email.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    dialog.dismiss();
                }
            });
        }
    }

    private void showDialog() {
        dialog.setContentView(R.layout.dialog_progress);
        dialog.show();
    }

    private boolean fieldIsValid(EditText textField) {
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
