
package com.example.ravi.androidclient;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedRec {

    @SerializedName("$class")
    @Expose
    private String $class;
    @SerializedName("recordId")
    @Expose
    private String recordId;
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

    @SerializedName("files")
    @Expose
    private List<String> files = null;

    public List<String> getFiles() { return files;}
    public void setFiles(List<String> files) {this.files = files;}

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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
