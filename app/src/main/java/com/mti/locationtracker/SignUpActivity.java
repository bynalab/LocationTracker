package com.mti.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
import com.mti.locationtracker.Model.User;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextView email_address;

    EditText password;
    EditText confirm_password;
    EditText name;
    EditText phone;

    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initFireBase();
        initViews();
    }

    private void initFireBase() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void initViews() {
        email_address = findViewById(R.id.signup_email);

        password = findViewById(R.id.signup_password);
        confirm_password = findViewById(R.id.signup_confirm_password);
        name = findViewById(R.id.signup_Fullname);
        phone = findViewById(R.id.signup_phone);

        Bundle bundle = getIntent().getExtras();
        email_address.setText(bundle.getString("email"));

        dialog = new Dialog(this);
    }

    public void CreateAccount(View v)
    {
        if (!fieldIsEmpty(password) && !fieldIsEmpty(confirm_password) && passwordsMatch(password, confirm_password))
        {
            showDialog();
            //Code
            mAuth.createUserWithEmailAndPassword(email_address.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String fullname = name.getText().toString();
                            String Phone = phone.getText().toString();

                            String userID = mAuth.getCurrentUser().getUid();
                            User user = new User(fullname, Phone);
                            mFirebaseDatabase.getReference().child("Users").child(userID).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Account created successfully.\nLogin with your newly created account", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                            finish();
                        }else
                            {
                                Toast.makeText(SignUpActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
            return true;
        }else
        {
            return false;
        }
    }

    private boolean passwordsMatch(EditText textField, EditText editText2) {
        if(!textField.getText().toString().equals(textField.getText().toString()))
        {
            textField.setError("Passwords no not match");
            editText2.setError("Passwords do not match");
            return false;
        }else
        {
            return true;
        }
    }
}
