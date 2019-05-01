package com.devpoint.firebasedb.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.devpoint.firebasedb.AnotherBaseActivity;
import com.devpoint.firebasedb.R;
import com.devpoint.firebasedb.utils.UserDetails;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends AnotherBaseActivity {
    private String displayName = "";
    private LinearLayout layout;
    private EditText messageArea;
    private ScrollView scrollView;
    private Firebase reference1;
    private FirebaseUser currentUser;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserRef = mRootRef.child("users");


    private String getId(String s1, String s2) {
        if (s1.compareTo(s2) > 0) {
            return s1 + "_" + s2;
        } else {
            return s2 + "_" + s1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setUpToolBar(findViewById(R.id.toolbar));
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        layout = findViewById(R.id.layout1);
        TextView sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);

        ;
        reference1 = new Firebase("https://developers-point.firebaseio.com/messages/" + getId(currentUser.getUid(), UserDetails.chatWith));
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("message", messageText);
                    map.put("user", currentUser.getUid());
                    map.put("from", UserDetails.chatWith );
                    map.put("by", displayName);
                    reference1.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });
 
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String by = map.get("by").toString();

                if (userName.equals(currentUser.getUid())) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(by + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserRef.child(currentUser.getUid()).child("isOnline").setValue(true);
        mUserRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                displayName = dataSnapshot.child("profileDisplayName").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.END;
            textView.setBackgroundResource(R.drawable.bubble_in);
        } else {
            lp2.gravity = Gravity.START;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mUserRef.child(currentUser.getUid()).child("isOnline").setValue(false);

    }


}