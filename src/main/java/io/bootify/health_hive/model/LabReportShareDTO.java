package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;


public class LabReportShareDTO {

    private Long id;

    @NotNull
    private String description;

    @LabReportSharePatientUnique
    private Long patient;

    @LabReportShareDoctorUnique
    private Long doctor;

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

    public Long getPatient() {
        return patient;
    }

    public void setPatient(final Long patient) {
        this.patient = patient;
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(final Long doctor) {
        this.doctor = doctor;
    }

}
