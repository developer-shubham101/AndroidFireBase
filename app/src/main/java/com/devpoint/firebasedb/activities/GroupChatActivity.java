package com.devpoint.firebasedb.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.devpoint.firebasedb.AnotherBaseActivity;
import com.devpoint.firebasedb.utils.LogUtils;
import com.devpoint.firebasedb.R;
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


public class GroupChatActivity extends AnotherBaseActivity {
    private static final String TAG = "GroupChatActivity";
    private LinearLayout layout;
    private RelativeLayout layout_2;
    private TextView sendButton;
    private EditText messageArea;
    private ScrollView scrollView;
    private Firebase reference1/*, reference2*/;
    private FirebaseUser currentUser;

    private String displayName = "";
//    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//    private DatabaseReference groupChatRef = mRootRef.child("gchat");
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserRef = mRootRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        setUpToolBar(findViewById(R.id.toolbar));
       /* if (auth.getCurrentUser() != null) {

        }*/
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://developers-point.firebaseio.com/gchat");
//        reference2 = new Firebase("https://developers-point.firebaseio.com/gchat/" + UserDetails.chatWith + "_" + currentUser.getUid());


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("message", messageText);
                    map.put("user", currentUser.getUid());
                    map.put("by", displayName);
                    reference1.push().setValue(map);
//                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
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
        reference1.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LogUtils.d(TAG, dataSnapshot.toString());


                String message = dataSnapshot.child("message").getValue(String.class);
                String userName = dataSnapshot.child("user").getValue(String.class);
                String by = dataSnapshot.child("by").getValue(String.class);
                if (userName.equals(currentUser.getUid())) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(by + ":-\n" + message, 2);
                }

               /* Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (userName.equals(currentUser.getUid())) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }*/
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                LogUtils.d(TAG, dataSnapshot.toString());
            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {
                LogUtils.d(TAG, dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                LogUtils.d(TAG, dataSnapshot.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(GroupChatActivity.this);
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
}