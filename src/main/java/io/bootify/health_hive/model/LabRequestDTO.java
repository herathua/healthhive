package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;


public class LabRequestDTO {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private Long user;

    @NotNull
    private String customerName;

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

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getLab() {
        return lab;
    }

    public void setLab(final Long lab) {
        this.lab = lab;
    }

}