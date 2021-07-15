package com.example.ams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StrainListView extends AppCompatActivity {

//    TextView companyName, strainText, strainType, THCStrain, AvailableAmount, StrainPrice;
    int image[] = {R.drawable.marijuana_logo};
    ListView listView1;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> arrayList;
    ArrayList<Strain> strainArrayList;
    Strain strain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_strain_list_view);
        initViews();
        Intent intent= getIntent();
        final String str = intent.getStringExtra("companyName");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Companies").child(str);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dp : snapshot.getChildren()) {
                    String temp = dp.getKey();
                    strain = dp.getValue(Strain.class);
                    arrayList.add(temp);
                    strainArrayList.add(strain);
                }
                MyAdapter1 adapter = new MyAdapter1(StrainListView.this, arrayList, strainArrayList,str);
                listView1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    class MyAdapter1 extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> arrayList;
        ArrayList<Strain> strainArrayList;
       // DatabaseReference myRef;
        String str;

        MyAdapter1(Context c, ArrayList<String> arrayList, ArrayList<Strain> strainArrayList,String str) {
            super(c, R.layout.list_view_strain1,arrayList);
            this.context = c;
            this.arrayList = arrayList;
            this.strainArrayList = strainArrayList;
            this.str = str;

         //   myRef = FirebaseDatabase.getInstance().getReference("Companies");
        }

        public String getCompanyName(){
            return this.str;
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.list_view_strain1, null, true);
            TextView strainText = view.findViewById(R.id.StrainName);
            TextView strainType = view.findViewById(R.id.StrainType);
            TextView THCStrain = view.findViewById(R.id.THCStrain);
            TextView AvailableAmount = view.findViewById(R.id.AvailableAmount);
            TextView StrainPrice = view.findViewById(R.id.StrainPrice);

            ImageView imageView = view.findViewById(R.id.imageView2);
            imageView.setImageResource(R.drawable.marijuana_logo);
            strainText.setText(arrayList.get(position));
            strainType.setText(strainArrayList.get(position).getStrainType());
            THCStrain.setText("THC: "+ strainArrayList.get(position).getThcEditText()+"%");
            AvailableAmount.setText("כמות "+ strainArrayList.get(position).getSupplyAvailability());
            StrainPrice.setText(strainArrayList.get(position).getPriceEditText()+" ₪");
            return view;

        }
    }

    private void initViews() {
        listView1 = findViewById(R.id.StrainListView);
        arrayList = new ArrayList<>();
        strainArrayList = new ArrayList<>();
    }
}