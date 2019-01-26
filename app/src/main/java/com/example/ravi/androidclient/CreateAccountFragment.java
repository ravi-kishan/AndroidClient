package com.example.ravi.androidclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class CreateAccountFragment extends Fragment implements View.OnClickListener{


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ArrayAdapter<CharSequence> identityAdapter;
    private Button submitButton;
    private Spinner identitySpinner;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");
       // User user = new User(firebaseUser.getDisplayName(),firebaseUser.getDisplayName(),firebaseUser.getEmail(),"as");//identitySpinner.getSelectedItem().toString());
        //databaseReference.child(firebaseUser.getUid()).setValue(user);
        //Toast.makeText(getActivity(),"submitted",Toast.LENGTH_SHORT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_account, container, false);
        TextView nameTextView = (TextView)rootView.findViewById(R.id.user_name);
        nameTextView.setText(firebaseUser.getDisplayName());
        identitySpinner = (Spinner)rootView.findViewById(R.id.identity_spinner);
        identityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.identity_array, android.R.layout.simple_spinner_item);
        identitySpinner.setAdapter(identityAdapter);
        submitButton = (Button)rootView.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this::onClick);
        //User user = new User(firebaseUser.getDisplayName(),firebaseUser.getDisplayName(),firebaseUser.getEmail(),identitySpinner.getSelectedItem().toString());
        //databaseReference.child(firebaseUser.getUid()).setValue(user);
        //Toast.makeText(getActivity(),"submitted",Toast.LENGTH_SHORT);
        return rootView;
    }



    @Override
    public void onClick(View view) {

            User user = new User(firebaseUser.getDisplayName(),firebaseUser.getDisplayName(),firebaseUser.getEmail(),identitySpinner.getSelectedItem().toString());
            databaseReference.child(firebaseUser.getUid()).setValue(user);
            if(user.getIdentity() == "Patient") {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ShowAccountFragment())
                        .commit();
            }
            else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ShowAccountFragment())
                        .commit();
            }



    }


}
