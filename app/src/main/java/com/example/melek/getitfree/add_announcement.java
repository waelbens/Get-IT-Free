package com.example.melek.getitfree;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_announcement extends AppCompatActivity {


    private static final String TAG = add_announcement.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputlabel, inputdescription, inputtel, inputname;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private StorageReference mStorage;
    private String announcement_componentsId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private ImageButton mSelectImage;
    private static final int GALLERY_INTENT =2 ;
    private Uri uri;
    public String url="http://www.novelupdates.com/img/noimagefound.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_announcement);


        txtDetails = (TextView) findViewById(R.id.txt_announcement_components);
        inputlabel = (EditText) findViewById(R.id.label);
        inputdescription = (EditText) findViewById(R.id.description);
        inputtel = (EditText) findViewById(R.id.tel);
        inputname = (EditText) findViewById(R.id.name);
        btnSave = (Button) findViewById(R.id.btn_save);
        mSelectImage = (ImageButton) findViewById(R.id.import_icon);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = mFirebaseInstance.getReference();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

        // get reference to 'announcement_components' node
        mFirebaseDatabase = mFirebaseInstance.getReference("announcement_components");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Get It Free!");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });


        // Save / update the announcement_components
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String label = inputlabel.getText().toString();
                String description = inputdescription.getText().toString();
                String tel = inputtel.getText().toString();
                String name = inputname.getText().toString();
                String user=mFirebaseUser.getEmail().toString();
                if (uri!=null) {url=uri.toString();}


                // Check for already existed announcement_componentsId
                if (TextUtils.isEmpty(announcement_componentsId)) {
                    createannouncement_components(label, description, tel, name,url, user);
                } else {
                    updateannouncement_components(label, description, tel,name);
                }
            }
        });

        toggleButton();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {
            uri = data.getData();
            if (uri!=null){
                mSelectImage.setImageURI(uri);
                StorageReference filepath = mStorage.child("Announcements").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(add_announcement.this, "Upload Done!", Toast.LENGTH_LONG).show();
                         uri = taskSnapshot.getDownloadUrl();
                    }



                });

            }


        }



    }



    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(announcement_componentsId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    private void createannouncement_components(String label, String description, String tel, String name, String url, String user) {
        // TODO
        // In real apps this announcement_componentsId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(announcement_componentsId)) {
            announcement_componentsId = mFirebaseDatabase.push().getKey();
        }
        announcement_components announcement_components = new announcement_components(label, description, tel, name, url, user );
        mFirebaseDatabase.child(announcement_componentsId).setValue(announcement_components);
        addannouncement_componentsChangeListener();
    }


    /**
     * announcement_components data change listener
     */
    private void addannouncement_componentsChangeListener() {
        // announcement_components data change listener
        mFirebaseDatabase.child(announcement_componentsId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                announcement_components announcement_components = dataSnapshot.getValue(announcement_components.class);

                // Check for null
                if (announcement_components == null) {
                    Log.e(TAG, "announcement_components data is null!");
                    return;
                }

                Log.e(TAG, "announcement_components data is changed!" + announcement_components.label + ", " + announcement_components.description + ", " + announcement_components.tel + ", " + announcement_components.name );

                // Display newly updated label and description
                //   txtDetails.setText(announcement_components.label + ", " + announcement_components.description+ ", " + announcement_components.tel + ", " + announcement_components.name + ", " + announcement_components.mail);

                // clear edit text
            //    inputdescription.setText("");
             //   inputlabel.setText("");
              //  inputname.setText("");
              //  inputtel.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read announcement_components", error.toException());
            }
        });
    }

    private void updateannouncement_components(String label, String description, String tel, String name) {
        // updating the announcement_components via child nodes
        if (!TextUtils.isEmpty(label))
            mFirebaseDatabase.child(announcement_componentsId).child("label").setValue(label);

        if (!TextUtils.isEmpty(description))
            mFirebaseDatabase.child(announcement_componentsId).child("description").setValue(description);

        if (!TextUtils.isEmpty(tel))
            mFirebaseDatabase.child(announcement_componentsId).child("tel").setValue(tel);

        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(announcement_componentsId).child("name").setValue(name);


    }
}


