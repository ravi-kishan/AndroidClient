
package com.example.ravi.androidclient;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRecord {

    @SerializedName("$class")
    @Expose
    private String $class;
    @SerializedName("patient")
    @Expose
    private String patient;
    @SerializedName("doctor")
    @Expose
    private String doctor;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("medicine")
    @Expose
    private List<String> medicine = null;
    @SerializedName("quantity")
    @Expose
    private List<Integer> quantity = null;

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<String> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<String> medicine) {
        this.medicine = medicine;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }

}
