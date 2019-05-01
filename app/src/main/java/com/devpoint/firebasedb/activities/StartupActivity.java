package com.devpoint.firebasedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.devpoint.firebasedb.PreferenceUtils;
import com.devpoint.firebasedb.R;
import com.devpoint.firebasedb.classes.LoginFragment;
import com.devpoint.firebasedb.classes.RegisterFragment;
import com.devpoint.firebasedb.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StartupActivity extends AppCompatActivity {


    private static final String TAG = StartupActivity.class.getSimpleName();
    private ViewPager viewPager;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, UsersActivity.class));
            finish();
        }
        currentUser = auth.getCurrentUser();
        setupViewPager();
    }

    private void setupViewPager() {


        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setEventsFired(new LoginFragment.EventsFired() {
            @Override
            public void loginSuccess() {


                startActivity(new Intent(StartupActivity.this, UsersActivity.class));
                finish();
            }

            @Override
            public void openSignupPage() {
                viewPager.setCurrentItem(1);
            }
        });
        adapter.addFragment(loginFragment);
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setEventsFired(new RegisterFragment.EventsFired() {
            @Override
            public void registrationSuccess(Bundle data) {
                PreferenceUtils preferenceUtils = new PreferenceUtils();
                String newToken = preferenceUtils.getData("newToken", StartupActivity.this);
                currentUser = auth.getCurrentUser();

                mRootRef.child("users").child(currentUser.getUid()).setValue(new UserModel(false, currentUser.getEmail(), data.getString(RegisterFragment.DISPLAY_NAME), newToken));
                startActivity(new Intent(StartupActivity.this, UsersActivity.class));
                finish();
            }

            @Override
            public void login() {
                viewPager.setCurrentItem(0);
            }
        });
        adapter.addFragment(registerFragment);

        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment) {

            mFragmentList.add(fragment);
        }


    }

}