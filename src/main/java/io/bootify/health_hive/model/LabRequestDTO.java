package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;


public class LabRequestDTO {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String invoice;

    @NotNull
    private Long user;

    @NotNull
    private Long lab;

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

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

    public Long getLab() {
        return lab;
    }

    public void setLab(final Long lab) {
        this.lab = lab;
    }

}