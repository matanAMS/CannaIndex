package com.example.ams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StrainFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText StrainName, manufacturerName, ThcEditText, PriceEditText, SupplyAvailability;
    Spinner spinnerTypes;
    String strainType;
    Button btnAddStrain;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_strain_form);
        initViews();

        spinnerTypes.setOnItemSelectedListener(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Companies");
        
        String[] items = new String[]{"Sativa", "Hybrid", "Indica"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerTypes.setAdapter(adapter);


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

        }
        btnAddStrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "זן חדש בשם "+ StrainName.getText().toString() + System.getProperty("line.separator") +" של חברת "
                        + manufacturerName.getText().toString() + System.getProperty("line.separator")+
                        "THC:"+ThcEditText.getText().toString()+"% "  +" מלאי: "+ SupplyAvailability.getText().toString() +
                        " מחיר: "+ PriceEditText.getText().toString();
                //create push notification
                NotificationCompat.Builder builder =  new NotificationCompat.Builder(StrainFormActivity.this, "MyNotifications");
                builder.setContentTitle("נרשם זן חדש");
                builder.setContentText(message);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
                builder.setAutoCancel(true);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(StrainFormActivity.this);
                managerCompat.notify(1, builder.build());
                addStrain();
                startActivity(new Intent(StrainFormActivity.this, WelcomeActivity.class));
            }
        });


//        myRef.getre("Companys").child(company name, taken from the strain form).child(strain name).setvalue(strain obj);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        strainType = spinnerTypes.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    private void addStrain() {
        Strain strain = new Strain(PriceEditText.getText().toString(),strainType,SupplyAvailability.getText().toString(), ThcEditText.getText().toString());

        myRef.child(manufacturerName.getText().toString()).child(StrainName.getText().toString()).setValue(strain);
        Toast.makeText(getApplicationContext(), "נוסף בהצלחה", Toast.LENGTH_SHORT).show();

    }

    private void initViews() {
        StrainName = findViewById(R.id.StrainName);
        manufacturerName = findViewById(R.id.manufacturerName);
        spinnerTypes = findViewById(R.id.StrainType);
        ThcEditText = findViewById(R.id.ThcEditText);
        PriceEditText = findViewById(R.id.PriceEditText);
        SupplyAvailability = findViewById(R.id.SupplyAvailability);
        btnAddStrain = findViewById(R.id.btnAddStrain);
    }
}
