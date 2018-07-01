package com.example.melek.getitfree;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class tab_actuality extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_actuality);

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        // Set up ListView
        final ListView listView = (ListView) findViewById(R.id.actuality_list);
        final ArrayList<post> arrayList = new ArrayList<post>();


        // Use Firebase to populate the list.
       mDatabase.child("actuality_components").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String k= messageSnapshot.getKey();
                    mDatabase.child("actuality_components").child(k).child("key").setValue(k.toString());
                    String l = (String) messageSnapshot.child("label").getValue();
                    String d = (String) messageSnapshot.child("description").getValue();
                    String t = (String) messageSnapshot.child("price").getValue();
                    String p = (String) messageSnapshot.child("period").getValue();
                    String u = (String) messageSnapshot.child("url").getValue();
                    arrayList.add(new post(l,d,t,p,u,k,"empty"));
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


                Intent intent = new Intent(getApplicationContext(),show_actuality.class); // passer de menu vers VoirActuality
                intent.putExtra("myKey", (Serializable) arrayList.get(position)); // putExtra(key,l'attribut)
                startActivity(intent);


            }
        });


        //pour actualiser
        ListView lv = (ListView) findViewById(R.id.actuality_list);
        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);

        android.widget.ListAdapter listadapter = new com.example.melek.getitfree.ListAdapter(arrayList, getApplicationContext());

        lv.setAdapter(listadapter);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            private void refreshContent() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        pullToRefresh.setRefreshing(false);
                    }
                }, 5000);

            }

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub

                refreshContent();

            }
        });


    }

}

