package com.example.ravi.androidclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRecordActivity extends AppCompatActivity {

    Patient patient;
    CreateRecord createRecord;
    private static final int SELECT_PHOTO = 100;
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageRef,imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    ImageView imageView;
    MedRec medRec;
    EditText patientId;
    EditText patientFirstName;
    EditText patientLastName;
    EditText medicineEnter;
    EditText quantityEnter;
    EditText diagnosis;
    List<MedRec> medRecList;
    List<String> medicineList;
    List<Integer> quantityList;

    ListView listView;
    ArrayList<Medicine> medicineArrayList;
    MedicineAdapter medicineAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_record);
        patient = new Patient();
        medRec = new MedRec();
        medicineList = new ArrayList<>();
        quantityList = new ArrayList<>();
        medicineArrayList = new ArrayList<Medicine>();
        medicineAdapter = new MedicineAdapter(this,medicineArrayList);
        createRecord = new CreateRecord();
        createRecord.set$class("org.ehr.hackathon.Patient");

patient.set$class("org.ehr.hackathon.CreateMedicalRecord");
        patientId = findViewById(R.id.patient_id_edittext);
        medicineEnter = findViewById(R.id.medicine_enter_edittext);
        quantityEnter = findViewById(R.id.quantity_enter_edittext);
        listView = findViewById(R.id.medicine_listView);
        diagnosis = findViewById(R.id.diagnosis_edittext);
        listView.setAdapter(medicineAdapter);

        //imageView = (ImageView) findViewById(R.id.imageView2);

        //accessing the firebase storage
     //   storage = FirebaseStorage.getInstance();

        //creates a storage reference
      //  storageRef = storage.getReference();

    }

    public void submitPatient (View view) {
        //patient.setFirstName(patientFirstName.getText().toString());
        patient.setPatientId(patientId.getText().toString());
        //patient.setLastName(patientLastName.getText().toString());
        medRec.setMedicine(medicineList);
        medRec.setQuantity(quantityList);
        medRecList = new ArrayList<MedRec>();
        medRecList.add(medRec);

        patient.setMedRec(medRecList);
        createRecord.setDoctor("org.ehr.hackathon.Doctor#" +"d1");
        createRecord.setMedicine(medicineList);
        createRecord.setQuantity(quantityList);
        createRecord.setDiagnosis(diagnosis.getText().toString());
        createRecord.setPatient("org.ehr.hackathon.Patient#p1);//"+patientId.getText().toString());
        System.out.println(createRecord);
        postData();
    }

    public void medicineClicked(View view) {
        medicineList.add(medicineEnter.getText().toString());
        quantityList.add(Integer.parseInt(quantityEnter.getText().toString()));
        Medicine md = new Medicine();
        md.medicine = medicineEnter.getText().toString();
        md.quantity = quantityEnter.getText().toString();
        medicineArrayList.add(md);
        medicineAdapter.notifyDataSetChanged();
    }

    public void selectImage(View view) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {

                    Toast.makeText(this,"Image selected, click on upload button",Toast.LENGTH_SHORT).show();
                    selectedImage = imageReturnedIntent.getData();
                }
        }
    }



    private void postData() {

        Call<CreateRecord> createRecordCall = BlockChainAPI.getCreateRecordService().savePost(
                
                "org.ehr.hackathon.Patient#"+patientId.getText().toString(),
                "org.ehr.hackathon.Doctor#"+FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                diagnosis.getText().toString(),
                medicineList,
                quantityList
        );



        createRecordCall.enqueue(new Callback<CreateRecord>() {
            @Override
            public void onResponse(Call<CreateRecord> call, Response<CreateRecord> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                //Log.i("responce",response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<CreateRecord> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                Log.i("retrofit error",t.getMessage());
            }
        });
    }
}
