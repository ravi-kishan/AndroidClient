package com.example.ravi.androidclient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class BlockChainAPI {

    private static final String url = "http://192.168.43.94:3000/";

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

    public static CreateRecordService createRecordService = null;
    public static CreateRecordService getCreateRecordService() {

        if(createRecordService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            createRecordService = retrofit.create(CreateRecordService.class);
        }
        return createRecordService;
    }

    public interface PatientService {
        @GET("api/patient/{id}")
        Call<Patient> getPatient( @Path("id") String id);


    }

    public interface CreateRecordService {
        @POST("api/CreateMedicalRecord")
            @FormUrlEncoded
        Call<CreateRecord> savePost(
                @Field("patient") String patient,
                                    @Field("doctor") String doctor,
                                    @Field("diagnosis") String diagnosis,
                                    @Field("medicine") List<String> medicine,
                                    @Field("quantity") List<Integer> quantity);
    }
}
