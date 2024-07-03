package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class HealthDataDTO {

    private Long healthDataId;

    @NotNull
    private LocalDate date;

    @Size(max = 6)
    private String weight;

    @Size(max = 3)
    private String height;



    private Long user;

    public Long getHealthDataId() {
        return healthDataId;
    }

    public void setHealthDataId(final Long healthDataId) {
        this.healthDataId = healthDataId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(final String height) {
        this.height = height;
    }



    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }
}
