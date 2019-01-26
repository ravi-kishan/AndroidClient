package com.example.ravi.androidclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DoctorFragment extends Fragment {

    TextView getRecord;
    TextView postRecord;
    private String patientId;
    private AlertDialog dialog;

    public DoctorFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_doctor, container, false);

        getRecord = rootView.findViewById(R.id.get_record);
        postRecord = rootView.findViewById(R.id.post_record);

        getRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Enter Patient Id");
                LayoutInflater inflater = (getActivity()).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.enter_patient_id_dialog, null);

                builder.setView(dialogView);
                builder.setPositiveButton("SUBMIT", (dialog, which) -> {
                    EditText editText = (EditText)dialogView.findViewById(R.id.enter_patient_edit);
                    patientId = editText.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("email",patientId);
                    Intent intent = new Intent(getActivity(),FetchRecordActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    dialog.dismiss();
                });
                builder.setNegativeButton("Cancel",(dialog, which) -> {
                    dialog.dismiss();
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        postRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PostRecordActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

   /* public void buttonClicked(View view) {
        if(view == getRecord) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("Enter Patient Id");
            LayoutInflater inflater = (this).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.enter_patient_id_dialog, null);

            builder.setView(dialogView);
            builder.setPositiveButton("SUBMIT", (dialog, which) -> {
                EditText editText = (EditText)dialogView.findViewById(R.id.enter_patient_edit);
                patientId = editText.getText().toString();
                Log.i("patientid",patientId);
               // Intent intent = new Intent(getActivity(),GetRecordActivity.class);
                //intent.putExtra("email","akshat@jain.com");
               // startActivity(intent);
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel",(dialog, which) -> {
                dialog.dismiss();
            });
            dialog = builder.create();
            dialog.show();
        } else {
            Intent intent = new Intent(getActivity(),PostRecordActivity.class);
            startActivity(intent);
        }
    }*/


}
