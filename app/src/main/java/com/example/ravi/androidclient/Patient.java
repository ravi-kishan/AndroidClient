
package com.example.ravi.androidclient;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient {

    @SerializedName("$class")
    @Expose
    private String $class;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("medRec")
    @Expose
    private List<MedRec> medRec = null;


    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<MedRec> getMedRec() {
        return medRec;
    }

    public void setMedRec(List<MedRec> medRec) {
        this.medRec = medRec;
    }

}
