package io.bootify.health_hivw.model;


public class LabDataUploadDTO {

    private Long id;
    private String description;
    private Long labRequest;

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

    public Long getLabRequest() {
        return labRequest;
    }

    public void setLabRequest(final Long labRequest) {
        this.labRequest = labRequest;
    }

}
