package com.example.melek.getitfree;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class show_actuality extends AppCompatActivity {

    TextView vt1,vt2,vt3,vt4,vt5;
    ImageView vt6;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_actuality);

        vt1= (TextView) findViewById(R.id.label);
        vt2= (TextView) findViewById(R.id.description);
        vt3= (TextView) findViewById(R.id.tel);
        vt4= (TextView) findViewById(R.id.period_name);
        vt6 = (ImageView) findViewById(R.id.icon);

        Serializable s=getIntent().getSerializableExtra("myKey");
        post u= (post) s;
        vt1.setText(u.getLabel());
        vt2.setText(u.getDescription());
        vt3.setPaintFlags( Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        vt3.setText(u.getTel());
        vt4.setText(u.getPeriod_name());
        Picasso.with(getApplicationContext()).load(u.getUrl()).into(vt6);

    }


    //*************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu){


        getMenuInflater().inflate(R.menu.menu_actuality, menu);
        return true ;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        // Initialize Firebase Auth and Database Reference
        
        if(id==R.id.share)
        {   Serializable s=getIntent().getSerializableExtra("myKey");
            post u= (post) s;

            Log.i("Send email", "");

            String[] TO = {""};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");


            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, u.getLabel());
            emailIntent.putExtra(Intent.EXTRA_TEXT, u.getDescription());

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(show_actuality.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }

        if(id==R.id.exit)
        {
            Intent intent = new Intent(show_actuality.this, menu.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item) ;

    }

    public void ClickGet(View v) {
        Serializable s=getIntent().getSerializableExtra("myKey");
        post u= (post) s;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        Log.i("Send email", "");

        String[] TO = {mFirebaseAuth.getCurrentUser().getEmail()};//mFirebaseUser.getEmail()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, u.getLabel());
        emailIntent.putExtra(Intent.EXTRA_TEXT, u.getDescription());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(show_actuality.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}