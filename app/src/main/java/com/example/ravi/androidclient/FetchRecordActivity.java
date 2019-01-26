package com.example.ravi.androidclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchRecordActivity extends AppCompatActivity {

    private String patientId;
    Patient patient;

    MedRec medRec;
    TextView patientID;
    TextView patientFirstName;
    TextView patientLastName;
    TextView patientDiagnosis;
    List<MedRec> medRecList;
    List<String> medicineList;
    List<Integer> quantityList;
    ArrayList<Images> imagesArrayList;
    ImagesAdapter imagesAdapter;

    ListView listView;
    ListView imagesListView;
    ArrayList<Medicine> medicineArrayList;
    MedicineAdapter medicineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_record);
        Bundle bundle = getIntent().getExtras();
        patientId = bundle.getString("email","akshat@jain.com");

        patient = new Patient();
        medRec = new MedRec();
        medRecList = new ArrayList<MedRec>();
        medicineList = new ArrayList<>();
        quantityList = new ArrayList<>();
        medicineArrayList = new ArrayList<Medicine>();
        medicineAdapter = new MedicineAdapter(this,medicineArrayList);
        imagesArrayList = new ArrayList<Images>();
        imagesAdapter = new ImagesAdapter(this,imagesArrayList);

        patientID = findViewById(R.id.patient_id);
        patientFirstName = findViewById(R.id.patient_firstName);
        patientLastName = findViewById(R.id.patient_lastName);
        listView = findViewById(R.id.medicinelv);
        patientDiagnosis = findViewById(R.id.patient_diagnosis);
        listView.setAdapter(medicineAdapter);
        imagesListView = findViewById(R.id.image_list_view);
        imagesListView.setAdapter(imagesAdapter);

        getData();

    }
    private void getData() {
        Call<Patient> patientCall = BlockChainAPI.getPatientService().getPatient(patientId);
        patientCall.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                Patient patient = response.body();
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                 patientID.setText(patient.getPatientId());
                patientFirstName.setText(patient.getFirstName());
                patientLastName.setText(patient.getLastName());
                medRecList = patient.getMedRec();
                if(medRecList != null) {
                    medicineList = medRecList.get(0).getMedicine();
                    quantityList = medRecList.get(0).getQuantity();
                }
                patientDiagnosis.setText(medRecList.get(0).getDiagnosis());

                for(int i=0;i<medicineList.size();i++)
                {
                    Medicine medicine = new Medicine();
                    medicine.medicine = medicineList.get(i);
                    medicine.quantity = quantityList.get(i).toString();
                    medicineArrayList.add(medicine);
                }
                for(int i=0;i<medRecList.get(0).getFiles().size();i++)
                {
                    Images currentImage = new Images();
                    currentImage.setUrl(medRecList.get(0).getFiles().get(i));
                    imagesArrayList.add(currentImage);
                }
                medicineAdapter.notifyDataSetChanged();
                imagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                Log.i("retrofit error",t.getMessage());
            }
        });
    }
}
