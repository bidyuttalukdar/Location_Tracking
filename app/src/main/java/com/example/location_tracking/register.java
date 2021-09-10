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

import java.time.Instant;

public class register extends AppCompatActivity {

    EditText mfullName, muserEmail, muserPhoneNumber, muserPassword, muserConfirmPassword;
    Button mregisterBtn;
    TextView mlogin;
    ProgressBar mprogressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mfullName= findViewById(R.id.fullName);
        muserEmail=findViewById(R.id.userEmail);
        muserPhoneNumber=findViewById(R.id.userPhoneNumber);
        muserPassword=findViewById(R.id.userPassword);
        mlogin=findViewById(R.id.createText);
        mregisterBtn=findViewById(R.id.registerbtn);
        fAuth=FirebaseAuth.getInstance();
        mprogressBar=findViewById(R.id.progressBar);
        muserConfirmPassword=findViewById(R.id.userConfirmPassword);


        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        mprogressBar.setVisibility(View.INVISIBLE);
        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email=muserEmail.getText().toString().trim();
                String phoneNumber=muserPhoneNumber.getText().toString().trim();
                String name=mfullName.getText().toString();
                String password=muserPassword.getText().toString().trim();
                String confirmPassword=muserConfirmPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    muserEmail.setError("Email is required");
                    muserEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber)){
                    muserPhoneNumber.setError("Phone Number is required");
                    muserPhoneNumber.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    mfullName.setError("Please Enter Your Name");
                    mfullName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    muserPassword.setError("Please enter password");
                    muserPassword.requestFocus();
                    return;
                }
                if(password.length()<6){
                    muserPassword.setError("Please enter a password with more than 6 character");
                    muserPassword.requestFocus();
                    return;
                }

                if(!password.equals(confirmPassword)){
                    muserPassword.setError("Password Does not match with confirm Password");
                    muserPassword.requestFocus();
                }


                mprogressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this, "User Created", Toast.LENGTH_SHORT).show();
                            mprogressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(register.this, "Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mprogressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }
}