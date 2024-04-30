package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;


public class LabDataUploadDTO {

    private Long id;

    @NotNull
    private String discription;

    private Long labRequest;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(final String discription) {
        this.discription = discription;
    }

    public Long getLabRequest() {
        return labRequest;
    }

    public void setLabRequest(final Long labRequest) {
        this.labRequest = labRequest;
    }

}
