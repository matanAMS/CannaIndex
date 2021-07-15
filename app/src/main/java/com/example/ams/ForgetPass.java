package com.example.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass extends AppCompatActivity {
    Button btnRePass;
    EditText userMail;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_pass);
        userMail = findViewById(R.id.userMailForRePass);
        btnRePass = findViewById(R.id.btnRePass);
        mAuth = FirebaseAuth.getInstance();

        btnRePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMail == null) {
                    return;
                }
                mAuth.sendPasswordResetEmail(userMail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPass.this, "Password send to your mail", Toast.LENGTH_LONG);
                                }
                            }
                        });
            }
        });

    }
}