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

patient.set$class("org.ehr.hackathon.Patient");
        patientId = findViewById(R.id.patient_id_edittext);
        patientFirstName = findViewById(R.id.patient_firstName_edittext);
        patientLastName = findViewById(R.id.patient_lastName_edittext);
        medicineEnter = findViewById(R.id.medicine_enter_edittext);
        quantityEnter = findViewById(R.id.quantity_enter_edittext);
        listView = findViewById(R.id.medicine_listView);
        listView.setAdapter(medicineAdapter);

        //imageView = (ImageView) findViewById(R.id.imageView2);

        //accessing the firebase storage
     //   storage = FirebaseStorage.getInstance();

        //creates a storage reference
      //  storageRef = storage.getReference();

    }

    public void submitPatient (View view) {
        patient.setFirstName(patientFirstName.getText().toString());
        patient.setPatientId(patientId.getText().toString());
        patient.setLastName(patientLastName.getText().toString());
        medRec.setMedicine(medicineList);
        medRec.setQuantity(quantityList);
        medRecList = new ArrayList<MedRec>();
        medRecList.add(medRec);

        patient.setMedRec(medRecList);
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

    /*public void uploadImage(View view) {

        //create reference to images folder and assing a name to the file that will be uploaded
        imageRef = storageRef.child("images/"+selectedImage.getLastPathSegment());

        //creating and showing progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        //starting upload
        uploadTask = imageRef.putFile(selectedImage);

        // Observe state change events such as progress, pause, and resume
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                //sets and increments value of progressbar
                progressDialog.incrementProgressBy((int) progress);

            }
        });

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {

                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(),"Error in uploading!",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                Toast.makeText(getApplicationContext(),"Upload successful",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                //showing the uploaded image in ImageView using the download url
                Picasso.with(getApplicationContext()).load(downloadUrl).into(imageView);

            }
        });


    }
}
*/


    private void postData() {
        Call<Patient> patientCall = BlockChainAPI.getPatientService().savePost(patient);
        patientCall.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                Patient patient = response.body();
                if(response.isSuccessful())
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                Log.i("responce",response.message());
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                Log.i("retrofit error",t.getMessage());
            }
        });
    }
}
