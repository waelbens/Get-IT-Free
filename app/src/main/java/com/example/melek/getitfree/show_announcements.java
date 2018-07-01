package com.example.melek.getitfree;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class show_announcements extends AppCompatActivity {

    TextView vt1, vt2,vt3,vt4,vt8;
    Button remove_btn;
    ImageView vt6;
    String vt5, vt7;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUserId;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_announcements);

        vt1 = (TextView) findViewById(R.id.label);
        vt2 = (TextView) findViewById(R.id.description);
        vt3= (TextView) findViewById(R.id.tel);
        vt4= (TextView) findViewById(R.id.period_name);
        vt8= (TextView) findViewById(R.id.mail_now);
        vt6 = (ImageView) findViewById(R.id.icon);


        Serializable s = getIntent().getSerializableExtra("myKey");
        post u = (post) s;
        vt1.setText(u.getLabel());
        vt2.setText(u.getDescription());
        vt3.setText(u.getTel());
        vt4.setText(u.getPeriod_name());
        vt8.setText(u.getUser());
        vt5=u.getUser();
        vt7=u.getKeyy();
        Picasso.with(getApplicationContext()).load(u.getUrl()).into(vt6);
    }


    public void ClickPhone(View v) {
        try {

            Serializable s = getIntent().getSerializableExtra("myKey");
            post u = (post) s;

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + u.getTel()));
            //auto generated
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }
    }



    public void ClickMail(View v) {
        Serializable s=getIntent().getSerializableExtra("myKey");
        post u= (post) s;

        Log.i("Send email", "");

        String[] TO = {u.user};
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
            Toast.makeText(show_announcements.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }



    //*************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu){


        getMenuInflater().inflate(R.menu.menu_annonce, menu);
        return true ;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("announcement_components");

        if(id==R.id.delete )
        {
            if (mFirebaseUser.getEmail().toString().equals(vt5)) {
                mDatabase.child(vt7).removeValue();

                Toast.makeText(this, "Announcement has been deleted", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(show_announcements.this, menu.class);
                startActivity(intent);
            }
            else {Toast.makeText(this,"Announcement can't be deleted",Toast.LENGTH_LONG).show();}
        }

        if(id==R.id.exit)
        {
            Intent intent = new Intent(show_announcements.this, menu.class);
            startActivity(intent);

        }

        if(id==R.id.share)
        {   Serializable s=getIntent().getSerializableExtra("myKey");
            post u= (post) s;

            Log.i("Send email", "");

            String[] TO = {mFirebaseAuth.getCurrentUser().getEmail().toString()};
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
                Toast.makeText(show_announcements.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item) ;


    }

}
