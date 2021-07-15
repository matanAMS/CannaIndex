package com.example.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//implements View.OnClickListener
public class SignupActivity extends AppCompatActivity  {

    EditText userName, userEmail, userPass, userconfPass, userID, userPhone, userFullName;
    Button btnSingup;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        initViews();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail.getText().toString().length() <= 5 || userName.getText().toString().length() <= 3 || userFullName.getText().toString().length() <= 3 ||
                        userID.getText().toString().length() < 9 || userPhone.getText().toString().length() <= 9 || userPass.getText().toString().length() <= 3 || userconfPass.getText().toString().length() <= 3) {
                    Toast.makeText(SignupActivity.this, "הזן את כל הנתונים", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    singUpUser();
                }
            }
        });

    }

    private void initViews() {
        userEmail = findViewById(R.id.userEmail);
        userName = findViewById(R.id.userName);
        userFullName = findViewById(R.id.userFullName);
        userID = findViewById(R.id.userID);
        userPhone = findViewById(R.id.userPhone);
        userPass = findViewById(R.id.userPass);
        userconfPass = findViewById(R.id.userconfirmpass);
        btnSingup = findViewById(R.id.btnSingup);
        mAuth = FirebaseAuth.getInstance();
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnSingup:
//
//                break;
//        }
//    }

    private void singUpUser() {
        if (!userPass.getText().toString().equals(userconfPass.getText().toString())) {
            Toast.makeText(getApplicationContext(), "PASS DOESNT MATCH!", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(),
                userPass.getText().toString())
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(userName.getText().toString(), userID.getText().toString(), userPhone.getText().toString());
                            myRef.child(mAuth.getUid()).setValue(user);
                            //register complete successfully
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        } else {
                            //Error
                            Toast.makeText(getApplicationContext(), "incorrect details", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}