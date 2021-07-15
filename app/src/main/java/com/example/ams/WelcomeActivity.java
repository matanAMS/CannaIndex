package com.example.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView WelcomeTxt;
    FirebaseAuth mAuth;
    Button btnAddStrain, btnCompanies, btnSignOut, btnWeedInfo, cannabMethod, btnLogOut;
    Space space;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);
        initView();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(mAuth.getUid());
        if (!(myRef.getKey().equals("34HShv5bSzPiv2eLhM1e3LxMFb42"))) {//check if admin is login
            btnAddStrain.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                WelcomeTxt.setText("שלום " + user.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddStrain:
                startActivity(new Intent(WelcomeActivity.this, StrainFormActivity.class));
                break;
            case R.id.btnCompanies:
                startActivity(new Intent(WelcomeActivity.this, CompaniesListView.class));
                break;
            case R.id.btnSignOut:
                createDialogSignOut();
                break;
            case R.id.btnWeedInfo:
                startActivity(new Intent(WelcomeActivity.this, WeedInfo.class));
                break;
            case R.id.CannabMethod:
                startActivity(new Intent(WelcomeActivity.this, CannabisMethods.class));
                break;
        }
    }

    // create dialog for log out
    private void createDialogSignOut() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setMessage("אתה בטוח שברצונך להתנתק?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    }

                })
                .setNegativeButton("לא", null)
                .show();
    }

    private void initView() {
        WelcomeTxt = findViewById(R.id.txtWelcome);
        mAuth = FirebaseAuth.getInstance();
        btnAddStrain = findViewById(R.id.btnAddStrain);
        btnCompanies = findViewById(R.id.btnCompanies);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnWeedInfo = findViewById(R.id.btnWeedInfo);
        cannabMethod = findViewById(R.id.CannabMethod);
        btnLogOut = findViewById(R.id.btnSignOut);
        space = findViewById(R.id.space);

    }
}