package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class LabReportShareDTO {

    private Long id;

    @NotNull
    private String description;

    @Size(max = 100)
    @LabReportSharePatientUnique
    private String patient;

    @Size(max = 100)
    @LabReportShareDoctorUnique
    private String doctor;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(final String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(final String doctor) {
        this.doctor = doctor;
    }

}
