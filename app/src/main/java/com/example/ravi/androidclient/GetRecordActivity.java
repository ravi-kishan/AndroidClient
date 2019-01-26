package com.example.ravi.androidclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecordActivity extends AppCompatActivity {

    private String patientId;
    Patient patient;
    private static final int SELECT_PHOTO = 100;
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageRef,imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    ImageView imageView;
    MedRec medRec;
    TextView patientID;
    TextView patientFirstName;
    TextView patientLastName;
    TextView medicineEnter;
    TextView quantityEnter;
    List<MedRec> medRecList;
    List<String> medicineList;
    List<Integer> quantityList;

    ListView listView;
    ArrayList<Medicine> medicineArrayList;
    MedicineAdapter medicineAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_record);
        Bundle bundle = getIntent().getExtras();
        patientId = bundle.getString("email","akshat@jain.com");
        //patientId = "akshat@jain.com";
        //Log.i("email  ",patientId);
        setContentView(R.layout.activity_post_record);
        patient = new Patient();
        medRec = new MedRec();
        medRecList = new ArrayList<MedRec>();
        medicineList = new ArrayList<>();
        quantityList = new ArrayList<>();
        medicineArrayList = new ArrayList<Medicine>();
        medicineAdapter = new MedicineAdapter(this,medicineArrayList);

        patientID = findViewById(R.id.patient_id_text);
        patientFirstName = findViewById(R.id.patient_firstName_text);
        patientLastName = findViewById(R.id.patient_lastName_text);
        listView = findViewById(R.id.medicine_lV);
        listView.setAdapter(medicineAdapter);

        getData();

    }

    private void getData() {
        Call<Patient> patientCall = BlockChainAPI.getPatientService().getPatient(patientId);
        patientCall.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                Patient patient = response.body();
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
               // patientID.setText(patient.getPatientId());
                //patientFirstName.setText(patient.getFirstName());
                //patientLastName.setText(patient.getLastName());
                medRecList = patient.getMedRec();
                if(medRecList != null) {
                    medicineList = medRecList.get(0).getMedicine();
                    quantityList = medRecList.get(0).getQuantity();
                }

                for(int i=0;i<medicineList.size();i++)
                {
                    Medicine medicine = new Medicine();
                    medicine.medicine = medicineList.get(i);
                    medicine.quantity = quantityList.get(i).toString();
                    medicineArrayList.add(medicine);
                }
                medicineAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                Log.i("retrofit error",t.getMessage());
            }
        });
    }
}
