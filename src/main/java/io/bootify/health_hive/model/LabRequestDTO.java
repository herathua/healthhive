package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class LabRequestDTO {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String invoice;

    @Size(max = 100)
    private String userEmail;

    @Size(max = 100)
    private String labEmail;

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

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(final String invoice) {
        this.invoice = invoice;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLabEmail() {
        return labEmail;
    }

    public void setLabEmail(final String labEmail) {
        this.labEmail = labEmail;
    }

}
