package com.example.ams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompaniesListView extends AppCompatActivity {

    ListView listView;

    String title;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_companies_list_view);
        arrayList = new ArrayList<>();
        listView = findViewById(R.id.CompaniesListView);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Companies");

        // reading from DB all the comapny data. insert it into ArrayList.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dp : snapshot.getChildren()) {
                    String temp = dp.getKey();
                    arrayList.add(temp);
                }
                MyAdapter adapter = new MyAdapter(CompaniesListView.this, arrayList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//click - pass the company name from listview to next activity
//                Toast.makeText(CompaniesListView.this,arrayList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),StrainListView.class);
                String Cname = arrayList.get(position);
                intent.putExtra("companyName",Cname);
                startActivity(intent);
            }
        });

    }


    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> arrayList;
        DatabaseReference myRef;
        int image[] = {R.drawable.marijuana_logo, R.drawable.marijuana_logo2, R.drawable.marijuana_logo3, R.drawable.marijuana_logo4, R.drawable.marijuana_logo5, R.drawable.marijuana_logo, R.drawable.marijuana_logo2, R.drawable.marijuana_logo3, R.drawable.marijuana_logo4, R.drawable.marijuana_logo5};

        MyAdapter(Context c, ArrayList<String> arrayList) {
            super(c, R.layout.list_view_companies, arrayList);
            this.context = c;
            this.arrayList = arrayList;
            myRef = FirebaseDatabase.getInstance().getReference("Companies");
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.list_view_companies, null, true);

            ImageView imageView = view.findViewById(R.id.imageCompany);
            TextView textView = view.findViewById(R.id.textView);

            imageView.setImageResource(image[position]);
            textView.setText(arrayList.get(position));
            return view;

        }
    }

}
