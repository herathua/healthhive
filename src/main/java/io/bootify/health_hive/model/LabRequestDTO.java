package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class LabRequestDTO {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String customerName;

    @NotNull
    private Long user;

    @NotNull
    private Long lab;

    public void setId(final Long id) {
        this.id = id;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setCustomerName(final String customerName) { this.customerName = customerName; }

    public void setUser(final Long user) {
        this.user = user;
    }

    public void setLab(final Long lab) {
        this.lab = lab;
    }

}