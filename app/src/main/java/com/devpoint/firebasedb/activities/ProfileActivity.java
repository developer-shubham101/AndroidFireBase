package com.devpoint.firebasedb.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.devpoint.firebasedb.AnotherBaseActivity;
import com.devpoint.firebasedb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AnotherBaseActivity {

    private EditText profileEmail, profileName, profileDisplayName;
    private Button updateProfile;
    //    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserRef = mRootRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpToolBar(findViewById(R.id.toolbar));
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        profileName = findViewById(R.id.profileName);
        profileDisplayName = findViewById(R.id.profileDisplayName);
        profileEmail = findViewById(R.id.profileEmail);
//        inputPassword = findViewById(R.id.password);
//        progressBar = findViewById(R.id.progressBar);
        updateProfile = findViewById(R.id.updateProfile);

        profileEmail.setText(currentUser.getEmail());
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserRef.child(currentUser.getUid()).child("profileName").setValue(profileName.getText().toString());
//                mUserRef.child(currentUser.getUid()).child("profileDisplayName").setValue(profileDisplayName.getText().toString());
//                mUserRef.child(currentUser.getUid()).child("profileName").setValue(profileName.getText().toString());
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
//        progressBar.setVisibility(View.GONE);
        mUserRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                profileDisplayName.setText(dataSnapshot.child(currentUser.getUid()).child("profileDisplayName").getValue(String.class));
                profileName.setText(dataSnapshot.child(currentUser.getUid()).child("profileName").getValue(String.class));
//                profileEmail.setText(dataSnapshot.child(currentUser.getUid() ).child("profileDisplayName").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}