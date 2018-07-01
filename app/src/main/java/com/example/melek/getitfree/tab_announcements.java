package com.example.melek.getitfree;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
;
import java.io.Serializable;
import java.util.ArrayList;

public class tab_announcements extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_announcements);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();

        // Set up ListView
        final ListView listView = (ListView) findViewById(R.id.announcement_list);
        final ArrayList<post> arrayList = new ArrayList<post>();


        // Use Firebase to populate the list.
        mDatabase.child("announcement_components").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String k= messageSnapshot.getKey();
                    mDatabase.child("announcement_components").child(k).child("key").setValue(k);
                    String l = (String) messageSnapshot.child("label").getValue();
                    String d = (String) messageSnapshot.child("description").getValue();
                    String t = (String) messageSnapshot.child("tel").getValue();
                    String m = (String) messageSnapshot.child("name").getValue();
                    String n = (String) messageSnapshot.child("url").getValue();
                    String u = (String) messageSnapshot.child("user").getValue();

                    arrayList.add(new post(l,d,t,m,n,k,u));
                    android.widget.ListAdapter listadapter = new com.example.melek.getitfree.ListAdapter(arrayList, getApplicationContext());
                    listView.setAdapter(listadapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) { }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(),show_announcements.class); // passer de menu vers VoirActuality
                intent.putExtra("myKey", (Serializable) arrayList.get(position)); // putExtra(key,l'attribut)
                startActivity(intent);


            }
        });


    }


    public void add_announcement (View view){

        Intent intent = new Intent(tab_announcements.this, add_announcement.class);
        startActivity(intent);
    }
}
