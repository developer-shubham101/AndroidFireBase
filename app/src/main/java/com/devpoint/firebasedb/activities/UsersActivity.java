package com.devpoint.firebasedb.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devpoint.firebasedb.PreferenceUtils;
import com.devpoint.firebasedb.R;
import com.devpoint.firebasedb.adapter.UsersListAdapter;
import com.devpoint.firebasedb.model.UsersListModel;
import com.devpoint.firebasedb.utils.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersActivity extends AppCompatActivity {
    //    private static final String TAG = "UsersActivity";
    private static boolean BACK_PRESSED = false;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private TextView noUsersText;

    //    -----------------------
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserRef = mRootRef.child("users");
    private UsersListAdapter adapter;
    private ArrayList<UsersListModel> usersList = new ArrayList<>();
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        recyclerView = findViewById(R.id.usersList);
        noUsersText = findViewById(R.id.noUsersText);
        toolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.main_drawer_layout);
        nvDrawer = findViewById(R.id.main_nvView);
        FloatingActionButton openGroupChat = findViewById(R.id.openGroupChat);

        openGroupChat.setOnClickListener(v -> startActivity(new Intent(UsersActivity.this, GroupChatActivity.class)));


        setUpToolBar();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    PreferenceUtils preferenceUtils = new PreferenceUtils();
                    String newToken = preferenceUtils.getData("newToken", UsersActivity.this);
                    auth = FirebaseAuth.getInstance();
                    HashMap<String,Object> update = new HashMap<>();
                    update.put("FCMToken",newToken );
                    update.put("isOnline",true );
                    mRootRef.child("users").child(currentUser.getUid()).updateChildren(update);

                    // user auth state is changed - user is null
                    // launch openSignupPage activity
                    startActivity(new Intent(UsersActivity.this, StartupActivity.class));
                    finish();
                }
            }
        };

        initRecycler();

    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);

        toolbar.setTitle("UsersActivity");

        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);

    }


    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_Settings:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.nav_GroupChat:
                startActivity(new Intent(this, GroupChatActivity.class));
                break;
            case R.id.nav_Logout:
                auth.signOut();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(nvDrawer)) {
            mDrawer.closeDrawer(nvDrawer);
        } else {
            if (BACK_PRESSED) {
                super.onBackPressed();
                return;
            }

            BACK_PRESSED = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    BACK_PRESSED = false;
                }
            }, 2000);

        }
    }

    private void initRecycler() {
        usersList.clear();
        adapter = new UsersListAdapter(R.layout.row_user_list, usersList, new UsersListAdapter.CallBackForSinglePost() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onClick(UsersListModel item) {
                UserDetails.chatWith = item.getId();
                startActivity(new Intent(UsersActivity.this, ChatActivity.class));
            }

        });


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserRef.child(currentUser.getUid()).child("isOnline").setValue(false);
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mUserRef.child(currentUser.getUid()).child("isOnline").setValue(false);
//        if (authListener != null) {
//            auth.removeAuthStateListener(authListener);
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
        mUserRef.child(currentUser.getUid()).child("isOnline").setValue(true);
        Query query = mUserRef.orderByChild("profileDisplayName");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<UsersListModel> tmpUsersList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(currentUser.getUid())) continue;
                    tmpUsersList.add(new UsersListModel(ds));
                }

                if (tmpUsersList.isEmpty()) {
                    noUsersText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    noUsersText.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.updateNewList(tmpUsersList);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}