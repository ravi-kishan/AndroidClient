package com.example.ravi.androidclient;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity{


    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private AlertDialog logoutDialog;
    private boolean loggedIn;
    private boolean register;
    public static User currentUser;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = false;


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");

         authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                    login();

                } else {
                    loggedIn = true;
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.welcome_layout);
                    FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fragment_container);
                    linearLayout.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    showFragment();
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            showLogoutDialog();
        } else {
            login();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        if (logoutDialog == null)
            logoutDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.logout_confirmation)
                    .setMessage(R.string.logout_confirmation_message)
                    .setPositiveButton(R.string.ok, (dialog, which) -> logout())
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .create();

        logoutDialog.show();
    }

    private void logout() {
        AuthUI.getInstance().signOut(this);
        Toast.makeText(this, R.string.log_out_success, Toast.LENGTH_SHORT).show();
    }

    private void login() {
        loggedIn = false;
        final Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(R.style.FirebaseUITheme)
                .setLogo(R.drawable.hospital_management)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build()))
                .build();
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode != RESULT_OK) {
                if (response == null) {
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.log_in_success, Toast.LENGTH_SHORT).show();
                loggedIn = true;
            }
        }
    }

    private void showFragment() {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.welcome_layout);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fragment_container);
        linearLayout.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        DatabaseReference userReference;
            userReference = databaseReference.child(firebaseAuth.getCurrentUser().getUid());


        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new CreateAccountFragment())
                            .commit();
                } else {
                     currentUser = dataSnapshot.getValue(User.class);
                    if(currentUser.getIdentity() == "Patient") {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new ShowAccountFragment())
                                .commit();
                    } else {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new DoctorFragment())
                                .commit();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;

    }



    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
