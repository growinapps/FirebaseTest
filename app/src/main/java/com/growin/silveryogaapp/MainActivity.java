package com.growin.silveryogaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontsContractCompat;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase pDatabase;
    private DatabaseReference pDatabaseRef;

    private FirebaseStorage pStorage;
    private StorageReference pStorageRef;


    private ListView pListView;
    //private ArrayAdapter<String> pArrayAdapter;
    private ListViewAdapter pListViewAdapter;
    private ArrayList<ListViewItem> pItemList;
    private Uri imgUri;



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
        //pArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());


        //listview와 adapter 연결
        //pListView.setAdapter(pArrayAdapter);
        //pListView.setAdapter(pListViewAdapter);
    }

    private void InitDatabase(){
        pDatabase = FirebaseDatabase.getInstance();
        pDatabaseRef = pDatabase.getReference("SilverYoga").child("Body").child("Shoulder");

        pStorage = FirebaseStorage.getInstance("gs://growinyoga-4f680.appspot.com");

        pDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pItemList = new ArrayList<ListViewItem>();

                for (DataSnapshot ss : snapshot.getChildren()) {
                    //String strMenu = ss.getKey();// +" "+ ss.getValue().toString();
                    String strPoseName = ss.child("name").getValue().toString();
                    String strImgPath = ss.child("img").getValue().toString();

                    pStorageRef = pStorage.getReference(strImgPath);

                    pStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ListViewItem pItem = new ListViewItem();
                            pItem.setTitle(strPoseName);
                            pItem.setUri(uri);
                            pItemList.add(pItem);
                            pListViewAdapter = new ListViewAdapter(pItemList);
                            pListView.setAdapter(pListViewAdapter);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //
                        }
                    });





                }

                pListViewAdapter = new ListViewAdapter(pItemList);
                pListView.setAdapter(pListViewAdapter);

                //pArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}