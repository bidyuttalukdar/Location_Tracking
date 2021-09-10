package com.example.location_tracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    EditText muserEmail, muserPassword;
    Button mloginBtn;
    FirebaseAuth fAuth;
    TextView mcreateRegister;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        muserEmail=findViewById(R.id.userEmail);
        muserPassword=findViewById(R.id.userPassword);
        mloginBtn=findViewById(R.id.loginBtn);
        mcreateRegister=findViewById(R.id.createRegister);
        progressBar=findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();

        mcreateRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });

        progressBar.setVisibility(View.INVISIBLE);

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=muserEmail.getText().toString().trim();
                String password=muserPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    muserEmail.setError("Please enter mail id");
                    muserEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    muserPassword.setError("Please Enter password");
                    muserPassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this,"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });


    }
}