package com.devpoint.firebasedb.classes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devpoint.firebasedb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {

    public static final String DISPLAY_NAME = "display_Name";
    private EditText inputEmail, inputPassword, registerDisplayName;
    private Button btnSignIn, btnSignUp;

    private FirebaseAuth auth;
    private EventsFired eventsFired;
    private ProgressDialog pd;
    public void setEventsFired(EventsFired eventsFired) {
        this.eventsFired = eventsFired;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = rootView.findViewById(R.id.sign_in_button);
        btnSignUp = rootView.findViewById(R.id.sign_up_button);
        inputEmail = rootView.findViewById(R.id.email);
        inputPassword = rootView.findViewById(R.id.password);


        registerDisplayName = rootView.findViewById(R.id.registerDisplayName);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventsFired.login();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String displayname = registerDisplayName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(displayname)) {
                    Toast.makeText(getContext(), "Enter display name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pd.show();
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.dismiss();
                                Toast.makeText(getContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    if (eventsFired != null) {
                                        Bundle args = new Bundle();
                                        args.putString(DISPLAY_NAME, registerDisplayName.getText().toString());
                                        eventsFired.registrationSuccess(args);
                                    }

                                }
                            }
                        });

            }
        });
        return rootView;
    }

   public interface EventsFired {
        void registrationSuccess(Bundle arg);

        void login();
    }

}

