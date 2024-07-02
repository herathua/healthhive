package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;

public class LabLoginDTO {
    @NotNull
    private String labRegID;

    @NotNull
    private String labPassword;

    public String getLabRegID() {
        return labRegID;
    }

    public void setLabRegID(String labRegID) {
        this.labRegID = labRegID;
    }

    public String getLabPassword() {
        return labPassword;
    }

    public void setLabPassword(String labPassword) {
        this.labPassword = labPassword;
    }
}
