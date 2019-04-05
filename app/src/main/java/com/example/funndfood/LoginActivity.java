package com.example.funndfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {



    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog processDialog;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;
    private TextView userProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = ( EditText) findViewById(R.id.editTextEmail);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        forgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        processDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();



        if (firebaseAuth.getCurrentUser() != null) {
            //start the profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), com.example.funndfood.userProfile.class));

        }


        buttonSignin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, com.example.funndfood.forgotPassword.class));
            }
        });

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, selection_activity.class));
            }

        });



    };

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if validations are ok
        //we will first show a progressbar
        processDialog.setMessage("Registering User");
        processDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        processDialog.dismiss();
                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), com.example.funndfood.userProfile.class));
                        }
                    }
                });



    }


    @Override
    public void onClick(View v) {
        if (v == buttonSignin) {
            userLogin();
        }
        if (v == textViewSignUp) {
            finish();
            startActivity(new Intent(this, com.example.funndfood.MainActivity.class));
        }


    }

}
