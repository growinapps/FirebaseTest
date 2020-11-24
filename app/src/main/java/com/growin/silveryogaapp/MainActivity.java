package com.growin.silveryogaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase pDatabase;
    private DatabaseReference pDatabaseRef;
    //private ChildEventListener pChildEventListener;

    private ListView pListView;
    private ArrayAdapter<String> pArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitState();
        InitDatabase();

    }

    private void InitState() {
        pListView = (ListView)findViewById(R.id.lstMenu);

        //array adapter 정의
        pArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());

        //listview와 adapter 연결
        pListView.setAdapter(pArrayAdapter);
    }

    private void InitDatabase(){
        pDatabase = FirebaseDatabase.getInstance();
        pDatabaseRef = pDatabase.getReference("SilverYoga").child("MainMenu");

        pDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pArrayAdapter.clear();

                for (DataSnapshot ss : snapshot.getChildren()) {
                    String strMenu = ss.getKey().toString() +" "+ ss.getValue().toString();
                    pArrayAdapter.add(strMenu);
                }

                pArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}