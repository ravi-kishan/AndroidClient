package com.example.ravi.androidclient;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class BlockChainAPI {

    private static final String url = "http://192.168.43.94:3000/api/";

    public static PatientService patientService = null;
    public static PatientService getPatientService() {

        if(patientService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            patientService = retrofit.create(PatientService.class);
        }
        return patientService;
    }

    public interface PatientService {
        @GET("patient/{id}")
        Call<Patient> getPatient( @Path("id") String id);

        @POST("CreateMedicalRecord")
        //@FormUrlEncoded
        Call<Patient> savePost(@Body Patient patient);
    }
}
